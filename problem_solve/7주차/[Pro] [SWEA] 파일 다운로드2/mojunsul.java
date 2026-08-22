import java.util.*;

class UserSolution {

    static final int MAX_TASKS = 50005;
    static final int INF = 1_000_000_000;
    static final int DIST_LIMIT = 5;
    static final int SPEED_PER_PATH = 9;

    static class Link {

        int to;
        int dist;
        int linkId;

        Link(int to, int dist, int linkId) {
            this.to = to;
            this.dist = dist;
            this.linkId = linkId;
        }
    }

    static class Path {

        int sourceCom;
        List<Integer> links = new ArrayList<>();

        Path(int sourceCom, List<Integer> links) {
            this.sourceCom = sourceCom;
            this.links.addAll(links);
        }
    }

    static class Task {

        int taskId;
        int targetCom;
        int fileId;
        int originalSize;
        int downloadedSize;
        int speed;
        int lastUpdateTime; // 마지막으로 다운로드 진행량이 갱신된 시각
        int version;    // 링크 단절 등으로 속도가 변경될 때마다 증가하는 버전값
        boolean isFinished;
        boolean isCanceled;
        List<Path> activePaths = new ArrayList<>(); // 현재 활성화되어 유지 중인 경로 목록

        void init(int taskId, int targetCom, int fileId, int size, int time, List<Path> paths) {
            this.taskId = taskId;
            this.targetCom = targetCom;
            this.fileId = fileId;
            this.originalSize = size;
            this.downloadedSize = 0;
            this.lastUpdateTime = time;
            this.version = 0;
            this.isFinished = false;
            this.isCanceled = false;
            this.activePaths.clear();
            if (paths != null) {
                this.activePaths.addAll(paths);
            }
            this.speed = this.activePaths.size() * SPEED_PER_PATH;
        }

        // curTime 시점까지 경과한 시간만큼 다운로드 진행량을 누적 갱신
        void update(int curTime) {
            if (isFinished || isCanceled || speed == 0) return;
            int dt = curTime - lastUpdateTime;
            if (dt > 0) {
                downloadedSize += (speed * dt);

                if (downloadedSize >= originalSize) {
                    downloadedSize = originalSize;
                    isFinished = true;
                }
                lastUpdateTime = curTime;
            }
        }

        // 현재 속도 기준 다운로드 예상 완료 시각
        int getExpectedEndTime() {
            if (speed == 0 || isFinished) return INF;
            int remain = originalSize - downloadedSize;
            return lastUpdateTime + (remain + speed - 1) / speed; // 올림연산
        }
    }

    static class Event implements Comparable<Event> {

        int endTime;
        int taskId;
        int version;

        Event(int endTime, int taskId, int version) {
            this.endTime = endTime;
            this.taskId = taskId;
            this.version = version;
        }

        @Override
        public int compareTo(Event o) {
            return Integer.compare(this.endTime, o.endTime);
        }
    }

    PriorityQueue<Event> eventQueue;
    Task[] tasks;
    int taskCnt;

    Map<Integer, Integer> fileSizeMap;  // <fileId, size>
    Map<Integer, Set<Integer>> sourceComMap; // <fileId, 파일 보유한 컴퓨터 집합>

    Map<String, Integer> activeTaskMap; // <"comId_fileId", taskIdx>

    List<Link>[] adj;
    Map<Integer, Boolean> linkActiveMap; // <linkId, 활성화 여부>
    Map<Integer, Set<Integer>> tasksUsingLink;  // <linkId, 링크를 사용 중인 taskId 집합>

    public void init(int N, int mFileCnt[], int mFileID[][], int mFileSize[][]) {
        eventQueue = new PriorityQueue<>();
        tasks = new Task[MAX_TASKS];
        for (int i = 0; i < MAX_TASKS; i++) {
            tasks[i] = new Task();
        }
        taskCnt = 0;

        fileSizeMap = new HashMap<>();
        sourceComMap = new HashMap<>();
        activeTaskMap = new HashMap<>();

        adj = new List[N + 1];
        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
        }

        linkActiveMap = new HashMap<>();
        tasksUsingLink = new HashMap<>();

        for (int i = 0; i < N; i++) {
            int comId = i + 1;
            for (int j = 0; j < mFileCnt[i]; j++) {
                int fileId = mFileID[i][j];
                int fileSize = mFileSize[i][j];

                fileSizeMap.put(fileId, fileSize);
                // fileId 보유한 목록에 comId 추가 (목록 없었으면 새로 생성 후 추가)
                sourceComMap.computeIfAbsent(fileId, k -> new HashSet<>()).add(comId);
            }
        }
    }

    public void makeNet(int K, int mID[], int mComA[], int mComB[], int mDis[]) {
        for (int i = 0; i < K; i++) {
            int linkId = mID[i];
            int a = mComA[i];
            int b = mComB[i];
            int dist = mDis[i];

            linkActiveMap.put(linkId, true);
            tasksUsingLink.computeIfAbsent(linkId, k -> new HashSet<>());

            adj[a].add(new Link(b, dist, linkId));
            adj[b].add(new Link(a, dist, linkId));
        }
    }

    public void removeLink(int mTime, int mID) {
        advanceTo(mTime);

        // 링크 비활성화 플래그 설정
        linkActiveMap.put(mID, false);

        Set<Integer> using = tasksUsingLink.get(mID);
        if (using == null || using.isEmpty()) return;

        // 끊어진 링크를 사용하던 태스크들 경로 및 속도 재계산
        List<Integer> affectedTasks = new ArrayList<>(using);
        for (int taskIdx : affectedTasks) {
            Task task = this.tasks[taskIdx];
            if (task.isFinished || task.isCanceled) continue;

            task.update(mTime);

            // 유효 경로들만 유지
            List<Path> remainingPaths = new ArrayList<>();
            for (Path path : task.activePaths) {
                boolean containsDeletedLink = false;
                for (int linkId : path.links) {
                    if (linkId == mID) {
                        containsDeletedLink = true;
                        break;
                    }
                }
                if (!containsDeletedLink) {
                    remainingPaths.add(path);
                }
            }

            // 남은 경로개수로 속도 갱신
            task.activePaths = remainingPaths;
            task.speed = remainingPaths.size() * SPEED_PER_PATH;

            if (task.speed == 0) {
                task.isCanceled = true;
                String key = task.targetCom + "_" + task.fileId;
                activeTaskMap.remove(key);
            }
            else {
                task.version++;
                int newEndTime = task.getExpectedEndTime();
                eventQueue.offer(new Event(newEndTime, task.taskId, task.version));
            }
        }

        // 끊어진 링크의 사용 태스크 제거
        tasksUsingLink.get(mID).clear();
    }

    public int downloadFile(int mTime, int mComA, int mFileID) {
        advanceTo(mTime);

        List<Path> validPaths = findPaths(mComA, mFileID);
        int pathCount = validPaths.size();
        if (pathCount == 0) {
            return 0;
        }

        // 태스크 생성 및 등록
        int taskIdx = taskCnt++;
        int totalSize = fileSizeMap.getOrDefault(mFileID, 0);
        tasks[taskIdx].init(taskIdx, mComA, mFileID, totalSize, mTime, validPaths);

        for (Path path : validPaths) {
            for (int linkId : path.links) {
                tasksUsingLink.computeIfAbsent(linkId, k -> new HashSet<>()).add(taskIdx);
            }
        }

        String key = mComA + "_" + mFileID;
        activeTaskMap.put(key, taskIdx);

        int endTime = tasks[taskIdx].getExpectedEndTime();
        eventQueue.offer(new Event(endTime, taskIdx, tasks[taskIdx].version));

        return pathCount;
    }

    public int getFileSize(int mTime, int mComA, int mFileID) {
        advanceTo(mTime);

        String key = mComA + "_" + mFileID;
        // 다운로드 중인 태스크가 존재하는 경우
        if (activeTaskMap.containsKey(key)) {
            Integer taskIdx = activeTaskMap.get(key);
            Task task = tasks[taskIdx];
            task.update(mTime);
            if (task.isFinished) {
                return fileSizeMap.get(mFileID);
            }
            return task.downloadedSize;
        }

        // 초기에 보유하고 있던 경우 원본 크기 반환
        if (sourceComMap.containsKey(mFileID) && sourceComMap.get(mFileID).contains(mComA)) {
            return fileSizeMap.get(mFileID);
        }

        return 0;
    }

    // targetTime 까지 예약된 완료 이벤트들을 처리
    private void advanceTo(int targetTime) {
        while (!eventQueue.isEmpty() && eventQueue.peek().endTime <= targetTime) {
            Event event = eventQueue.poll();
            Task task = tasks[event.taskId];

            if (task.isFinished || task.isCanceled || task.version != event.version) {
                continue;
            }

            task.update(event.endTime);

            if (task.isFinished) {
                sourceComMap.computeIfAbsent(task.fileId, k -> new HashSet<>()).add(task.targetCom);

                // 활성 태스크 목록에서 제거
                String key = task.targetCom + "_" + task.fileId;
                activeTaskMap.remove(key);

                // 사용하던 모든 링크의 점유 목록에서 해당 태스크 해제
                for (Path path : task.activePaths) {
                    for (int linkId : path.links) {
                        Set<Integer> set = tasksUsingLink.get(linkId);
                        if (set != null) {
                            set.remove(task.taskId);
                        }
                    }
                }
            }
        }
    }

    // startCom에서 출발하여 거리 5 이내에 있는 모든 파일 보유 컴퓨터까지의 경로를 수집
    private List<Path> findPaths(int startCom, int fileId) {
        List<Path> results = new ArrayList<>();
        Set<Integer> sources = sourceComMap.get(fileId);

        if (sources == null || sources.isEmpty()) {
            return results;
        }

        List<Integer> pathLinks = new ArrayList<>();
        dfs(startCom, 0, 0, sources, pathLinks, results);

        return results;
    }

    private void dfs(int cur, int parent, int dist, Set<Integer> sources, List<Integer> pathLinks, List<Path> results) {
        if (dist > 0 && sources.contains(cur)) {
            results.add(new Path(cur, pathLinks));
        }

        for (Link link : adj[cur]) {
            // 단절된 링크 제외
            if (!linkActiveMap.getOrDefault(link.linkId, false)) continue;
            // 부모로 돌아가는 링크 제외
            if (link.to == parent) continue;

            int nextDist = dist + link.dist;

            if (nextDist <= DIST_LIMIT) {
                pathLinks.add(link.linkId);
                dfs(link.to, cur, nextDist, sources, pathLinks, results);
                pathLinks.remove(pathLinks.size() - 1);
            }
        }
    }
}
// 소요 시간: 6:40:13
