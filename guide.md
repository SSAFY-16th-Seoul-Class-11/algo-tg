# 알고특강 자동화 가이드

README.md의 문제 링크를 수정해 push하면 GitHub Actions가 주차/출제자를 갱신하고 `problem_solve/` 폴더를 만든다.

## 사용법
1. `README.md`의 `### 📝💯 N주차 문제` 아래 문제 링크만 수정한다.
   - **별다른 상황이 아니면 문제 링크 부분만 고치고, 그 외(주차 제목, 출제자 구간, 다른 파일)는 건드리지 않는다.** 주차와 출제자는 봇이 갱신한다.
2. `main`에 push한다. (테스트는 `CI-test` 브랜치)
3. 봇이 `chore: update week and generate daily problem folders [skip ci]` 커밋을 추가한다. 이 커밋을 pull 받은 뒤 작업한다.

## 새 주차 / 수정 판정
push 이전 커밋의 README와 링크 집합을 비교한다.

| 순서 | 조건 | 결과 |
|---|---|---|
| 1 | 링크 집합이 같음 | 변화 없음 |
| 2 | 커밋 메시지에 `[new-week]` | 새 주차 |
| 3 | 커밋 메시지에 `[fix]` | 수정 |
| 4 | 이전 링크와 하나라도 겹침 | 수정 |
| 5 | 마지막 새 주차 갱신 후 4일 이내 | 수정 |
| 6 | 그 외 | 새 주차 |

- 새 주차면 제목 주차 +1, 출제자 한 칸 이동, `.github/study_state.json`의 `last_bump_date` 갱신.
- 주차를 직접 올려서 push하면 중복으로 올리지 않는다. 출제자는 그래도 한 칸 이동한다.

### 자주 하는 상황
- 링크 3개를 전부 새 문제로 교체: 4일이 지났으면 새 주차, 4일 이내면 수정.
- 링크 일부만 교체: 항상 수정. 새 주차로 만들려면 커밋 메시지에 `[new-week]`.
- 오타 수정처럼 링크를 전부 바꿔도 주차를 올리기 싫을 때: 커밋 메시지에 `[fix]`.

## 출제자 순서 건너뛰기
Actions 탭의 `Generate daily problem folders`를 수동 실행하고 `skip`에 건너뛸 인원 수를 입력한다.
순서/현재 위치는 `.github/study_state.json`에서 관리한다.

## 로그 확인
Actions 로그의 `Update week and publisher` 단계에 `[debug]` 줄과 `action=` 이 찍힌다.
`base 존재 여부=False`면 이전 커밋을 못 찾은 것이라 변화 없음으로 처리된다.

## 파일
- `.github/workflows/generate_daily.yml`: 워크플로우 (주차 갱신 → 폴더 생성 → 커밋)
- `.github/scripts/bump_week.py`: 주차/출제자 갱신
- `.github/scripts/generate_daily.py`: 폴더 생성
- `.github/study_state.json`: 출제자 순서 상태
- README의 `<!-- PUBLISHER:START -->` ~ `<!-- PUBLISHER:END -->`는 자동 갱신 구간이므로 직접 수정하지 않는다.
