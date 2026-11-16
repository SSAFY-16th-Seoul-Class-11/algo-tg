from __future__ import annotations

from pathlib import Path
import re
import sys


ROOT_README = Path("README.md")
TARGET_ROOT = Path("problem_solve")
DEFAULT_LANGUAGES = ["java"]

LANG_EXTENSIONS = {
    "java": "java",
    "swift": "swift",
    "python": "py",
    "c++": "cpp",
    "c": "c",
    "javascript": "js",
    "typescript": "ts",
    "kotlin": "kt",
}

LANG_ALIASES = {
    "java": "java",
    "swift": "swift",
    "python": "python",
    "py": "python",
    "c++": "c++",
    "c%2b%2b": "c++",
    "cpp": "c++",
    "kotlin": "kotlin",
    "javascript": "javascript",
    "js": "javascript",
    "typescript": "typescript",
    "ts": "typescript",
    "c": "c",
}

PLATFORM_TAGS = {
    "acmicpc.net": "BOJ",
    "programmers.co.kr": "PGS",
    "swexpertacademy.com": "SWEA",
    "leetcode.com": "LTC",
    "codeforces.com": "CF",
}

# 입력 README에 남아있을 수 있는 난이도 태그 ([Easy], [Pro], [Level 1] 등)
LEVEL_TAG = r"\[(?:Easy|Normal|Hard|Extreme|Pro|Level\s*\d+)\]\s*"

DAILY_BLOCK_PATTERN = re.compile(
    r"^###\s*📝💯\s*(?P<title>.+?)\s*문제\s*$"
    r"(?P<body>[\s\S]*?)"
    r"(?=^###\s|^##\s|\Z)",
    re.MULTILINE,
)

MARKDOWN_LINK_PATTERN = re.compile(
    rf"^(?:[-*]\s*)?(?:{LEVEL_TAG})?"
    r"\[(?P<title>.+?)\]\((?P<url>.+?)\)\s*$",
    re.IGNORECASE,
)


def sanitize(name: str) -> str:
    name = re.sub(r'[<>:"/\\|?*]', "-", name)
    name = re.sub(r"\s+", " ", name).strip()
    return name.rstrip(".")


def extract_daily_blocks(text: str) -> list[tuple[str, str]]:
    blocks = [
        (match.group("title").strip(), match.group("body"))
        for match in DAILY_BLOCK_PATTERN.finditer(text)
    ]

    if not blocks:
        raise ValueError(
            "README에서 "
            "'### 📝💯 {주차} 문제' 형식의 블록을 찾지 못했습니다."
        )

    return blocks


def extract_links(body: str) -> list[dict[str, str]]:
    links: list[dict[str, str]] = []

    for line in body.splitlines():
        match = MARKDOWN_LINK_PATTERN.match(line.strip())

        if not match:
            continue

        title = re.sub(
            rf"^{LEVEL_TAG}",
            "",
            match.group("title").strip(),
            flags=re.IGNORECASE,
        ).strip()

        links.append({"title": title, "url": match.group("url").strip()})

    if not links:
        raise ValueError("문제 블록에서 문제 링크를 찾지 못했습니다.")

    return links


def detect_platform_tag(url: str) -> str:
    lower_url = url.lower()

    for domain, tag in PLATFORM_TAGS.items():
        if domain in lower_url:
            return tag

    return "ETC"


def build_daily_readme(title: str, links: list[dict[str, str]]) -> str:
    lines = [f"# {title} 문제", "", "## 문제 목록", ""]
    lines.extend(f"- [{item['title']}]({item['url']})" for item in links)
    lines.append("")
    return "\n".join(lines)


def build_problem_folder_name(item: dict[str, str]) -> str:
    # 폴더명 형식: [태그] {문제이름}
    return sanitize(f"[{detect_platform_tag(item['url'])}] {item['title']}")


def build_problem_readme(folder_name: str, url: str) -> str:
    return f"# {folder_name}\n\n- 문제 링크: {url}\n"


def extract_member_section(text: str) -> str:
    for table in re.findall(r"<table>([\s\S]*?)</table>", text, re.IGNORECASE):
        if "github.com/" in table.lower():
            return table

    raise ValueError(
        "README에서 GitHub 링크가 포함된 "
        "스터디 멤버 테이블을 찾지 못했습니다."
    )


def find_cells(html: str) -> list[str]:
    return re.findall(r"<td\b[^>]*>[\s\S]*?</td>", html, re.IGNORECASE)


def normalize_language_name(raw: str) -> str | None:
    return LANG_ALIASES.get(raw.strip().lower())


def extract_languages_from_cell(cell_html: str) -> list[str]:
    candidates: list[str] = []

    data_match = re.search(
        r"data-languages?=[\"']([^\"']+)[\"']", cell_html, re.IGNORECASE
    )
    if data_match:
        candidates += re.split(r"[,\s]+", data_match.group(1))

    candidates += re.findall(r"badge/([^?\-\s]+)-", cell_html, re.IGNORECASE)

    plain_text = re.sub(r"<[^>]+>", " ", cell_html)
    candidates += re.split(r"[,/\s]+", plain_text)

    languages: list[str] = []

    for candidate in candidates:
        language = normalize_language_name(candidate)

        if language and language not in languages:
            languages.append(language)

    return languages


def find_language_cells(member_section: str) -> list[str]:
    for row in re.findall(r"<tr\b[^>]*>([\s\S]*?)</tr>", member_section, re.IGNORECASE):
        cells = find_cells(row)

        if any(extract_languages_from_cell(cell) for cell in cells):
            return cells

    return []


def extract_members(member_section: str) -> dict[str, list[str]]:
    """{GitHub 사용자명: [언어, ...]} 형태로 반환한다."""
    usernames: list[str] = []

    for cell in find_cells(member_section):
        match = re.search(r"github\.com/([^\"'/<>?#]+)", cell, re.IGNORECASE)

        if match and match.group(1) not in usernames:
            usernames.append(match.group(1))

    if not usernames:
        raise ValueError(
            "스터디 멤버 테이블에서 GitHub 사용자명을 찾지 못했습니다."
        )

    language_cells = find_language_cells(member_section)
    members: dict[str, list[str]] = {}

    for index, username in enumerate(usernames):
        languages = (
            extract_languages_from_cell(language_cells[index])
            if index < len(language_cells)
            else []
        )
        members[username] = languages or DEFAULT_LANGUAGES.copy()

    return members


def write_if_changed(path: Path, content: str) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)

    if path.exists() and path.read_text(encoding="utf-8") == content:
        return

    path.write_text(content, encoding="utf-8")


def ensure_file(path: Path, content: str = "") -> None:
    path.parent.mkdir(parents=True, exist_ok=True)

    if not path.exists():
        path.write_text(content, encoding="utf-8")


def build_code_template(username: str, language: str) -> str:
    if language == "swift":
        return f"import Foundation\n\n// {username}\n"

    if language == "python":
        return f"# {username}\n"

    if language == "c++":
        return (
            "#include <bits/stdc++.h>\n"
            "using namespace std;\n\n"
            "int main() {\n"
            "    ios::sync_with_stdio(false);\n"
            "    cin.tie(nullptr);\n"
            "    return 0;\n"
            "}\n"
        )

    if language == "c":
        return (
            "#include <stdio.h>\n\n"
            "int main(void) {\n"
            "    return 0;\n"
            "}\n"
        )

    if language == "kotlin":
        return "fun main() {\n}\n"

    if language in {"javascript", "typescript"}:
        return f"// {username}\n"

    return ""  # java


def generate_daily_folder(
    daily_title: str,
    links: list[dict[str, str]],
    members: dict[str, list[str]],
) -> None:
    safe_title = sanitize(daily_title)

    if not safe_title:
        raise ValueError("데일리 문제 제목이 비어 있습니다.")

    daily_directory = TARGET_ROOT / safe_title

    write_if_changed(
        daily_directory / "README.md",
        build_daily_readme(daily_title, links),
    )

    for item in links:
        folder_name = build_problem_folder_name(item)
        problem_directory = daily_directory / folder_name

        write_if_changed(
            problem_directory / "README.md",
            build_problem_readme(folder_name, item["url"]),
        )

        for username, languages in members.items():
            for language in languages:
                extension = LANG_EXTENSIONS.get(language)

                if not extension:
                    continue

                ensure_file(
                    problem_directory / f"{username}.{extension}",
                    build_code_template(username, language),
                )


def main() -> None:
    if not ROOT_README.exists():
        print("루트 README.md가 없습니다.")
        sys.exit(1)

    try:
        readme_text = ROOT_README.read_text(encoding="utf-8")

        members = extract_members(extract_member_section(readme_text))

        for daily_title, daily_body in extract_daily_blocks(readme_text):
            generate_daily_folder(
                daily_title,
                extract_links(daily_body),
                members,
            )

    except ValueError as error:
        print(f"생성 실패: {error}")
        sys.exit(1)


if __name__ == "__main__":
    main()
