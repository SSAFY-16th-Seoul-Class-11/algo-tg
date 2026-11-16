from __future__ import annotations

import argparse
import json
import re
import subprocess
from datetime import date, datetime
from pathlib import Path
from zoneinfo import ZoneInfo


README = Path("README.md")
STATE_FILE = Path(".github/study_state.json")

# 마지막 주차 갱신 후 이 일수 이내의 문제 변경은 '수정'으로 본다.
CORRECTION_WINDOW_DAYS = 4

DEFAULT_MEMBERS = ["김동준", "김세헌", "고민균", "서동균", "안서현", "김강민"]

PUBLISHER_START = "<!-- PUBLISHER:START -->"
PUBLISHER_END = "<!-- PUBLISHER:END -->"

DAILY_BLOCK_PATTERN = re.compile(
    r"^###\s*📝💯\s*(?P<title>.+?)\s*문제\s*$"
    r"(?P<body>[\s\S]*?)"
    r"(?=^###\s|^##\s|\Z)",
    re.MULTILINE,
)
LINK_URL_PATTERN = re.compile(r"\]\((https?://[^)\s]+)\)")
WEEK_PATTERN = re.compile(r"(\d+)(?=주차)")


def git(*args: str) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        ["git", *args], capture_output=True, text=True, encoding="utf-8"
    )


def is_valid_ref(ref: str | None) -> bool:
    return bool(ref) and set(ref) != {"0"}


def read_base_readme(base: str | None) -> str | None:
    if not is_valid_ref(base):
        return None

    result = git("show", f"{base}:{README.as_posix()}")
    return result.stdout if result.returncode == 0 else None


def read_commit_messages(base: str | None) -> str:
    if is_valid_ref(base):
        result = git("log", f"{base}..HEAD", "--format=%B")
    else:
        result = git("log", "-1", "--format=%B")

    return result.stdout if result.returncode == 0 else ""


def parse_problem_block(text: str) -> tuple[str | None, set[str]]:
    match = DAILY_BLOCK_PATTERN.search(text)

    if not match:
        return None, set()

    return match.group("title"), set(LINK_URL_PATTERN.findall(match.group("body")))


def week_of(title: str | None) -> int | None:
    match = WEEK_PATTERN.search(title or "")
    return int(match.group(1)) if match else None


def set_week(text: str, week: int) -> str:
    match = DAILY_BLOCK_PATTERN.search(text)

    if not match:
        return text

    new_title = WEEK_PATTERN.sub(str(week), match.group("title"), count=1)
    return text[: match.start("title")] + new_title + text[match.end("title") :]


def decide(
    old_urls: set[str],
    new_urls: set[str],
    messages: str,
    last_bump: date | None,
    today: date,
) -> str:
    """'none' | 'fix' | 'bump' 중 하나를 반환한다."""
    if old_urls == new_urls:
        return "none"

    lowered = messages.lower()

    if "[new-week]" in lowered:
        return "bump"

    if "[fix]" in lowered:
        return "fix"

    if old_urls & new_urls:
        return "fix"

    if last_bump and (today - last_bump).days <= CORRECTION_WINDOW_DAYS:
        return "fix"

    return "bump"


def load_state() -> dict:
    if STATE_FILE.exists():
        return json.loads(STATE_FILE.read_text(encoding="utf-8"))

    return {
        "members": DEFAULT_MEMBERS.copy(),
        "current_index": 0,
        "last_bump_date": None,
    }


def save_state(state: dict) -> None:
    STATE_FILE.parent.mkdir(parents=True, exist_ok=True)
    STATE_FILE.write_text(
        json.dumps(state, ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
    )


def render_publishers(members: list[str], index: int) -> str:
    size = len(members)
    return (
        f"- 이번주 출제자: **{members[index % size]}**\n"
        f"- 다음주 출제자: {members[(index + 1) % size]}\n"
        f"- 순서: {' → '.join(members)}"
    )


def replace_publisher_section(text: str, body: str) -> tuple[str, bool]:
    pattern = re.compile(
        re.escape(PUBLISHER_START) + r"[\s\S]*?" + re.escape(PUBLISHER_END)
    )

    if not pattern.search(text):
        return text, False

    replacement = f"{PUBLISHER_START}\n{body}\n{PUBLISHER_END}"
    return pattern.sub(lambda _: replacement, text, count=1), True


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--base", default=None, help="비교 기준 커밋 (push 이전 커밋)")
    parser.add_argument("--skip", type=int, default=0, help="출제자 순서를 건너뛸 인원 수")
    args = parser.parse_args()

    today = datetime.now(ZoneInfo("Asia/Seoul")).date()
    state = load_state()
    original_state = json.dumps(state, sort_keys=True)

    text = README.read_text(encoding="utf-8")
    original_text = text
    base_text = read_base_readme(args.base)

    action = "none"

    print(f"[debug] base={args.base!r} base 존재 여부={base_text is not None}")
    print(f"[debug] 오늘(KST)={today} last_bump_date={state.get('last_bump_date')}")

    if base_text is not None:
        old_title, old_urls = parse_problem_block(base_text)
        new_title, new_urls = parse_problem_block(text)

        last_bump = (
            date.fromisoformat(state["last_bump_date"])
            if state.get("last_bump_date")
            else None
        )

        messages = read_commit_messages(args.base)
        action = decide(old_urls, new_urls, messages, last_bump, today)

        print(f"[debug] 주차 old={week_of(old_title)} new={week_of(new_title)}")
        print(
            f"[debug] 링크 수 old={len(old_urls)} new={len(new_urls)} "
            f"겹침={len(old_urls & new_urls)} 동일={old_urls == new_urls}"
        )
        print(
            f"[debug] 커밋 태그 new-week={'[new-week]' in messages.lower()} "
            f"fix={'[fix]' in messages.lower()}"
        )

        if action == "bump":
            old_week = week_of(old_title)
            new_week = week_of(new_title)

            # 출제자가 주차를 직접 올렸다면 중복으로 올리지 않는다.
            if old_week is not None and new_week == old_week:
                text = set_week(text, new_week + 1)

            state["current_index"] = state["current_index"] + 1
            state["last_bump_date"] = today.isoformat()

    if args.skip:
        state["current_index"] = state["current_index"] + args.skip

    state["current_index"] %= len(state["members"])

    text, replaced = replace_publisher_section(
        text,
        render_publishers(state["members"], state["current_index"]),
    )

    if not replaced:
        print("경고: README에 PUBLISHER 마커가 없어 출제자 영역을 건너뜁니다.")

    if text != original_text:
        README.write_text(text, encoding="utf-8")

    if json.dumps(state, sort_keys=True) != original_state or not STATE_FILE.exists():
        save_state(state)

    print(f"action={action}")


if __name__ == "__main__":
    main()
