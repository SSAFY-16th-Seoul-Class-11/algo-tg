import java.util.*;

class UserSolution {

    static int movieIdx;

    static class Movie {
        int id, genre, total, idx;
        boolean isRemoved;

        public Movie(int id, int genre, int total) {
            this.id = id;
            this.genre = genre;
            this.total = total;
            this.idx = ++movieIdx;
            this.isRemoved = false;
        }

        public void addRating(int rate) {
            total += rate;
        }
    }

    // PQ에 들어갈 스냅샷
    static class MovieNode implements Comparable<MovieNode> {
        int id;
        int genre;
        int total;
        int idx;

        public MovieNode(int id, int genre, int total, int idx) {
            this.id = id;
            this.genre = genre;
            this.total = total;
            this.idx = idx;
        }

        @Override
        public int compareTo(MovieNode o) {
            if (this.total != o.total) {
                return Integer.compare(o.total, this.total); // 총점 높은 순
            }
            return Integer.compare(o.idx, this.idx); // 최신 인덱스 순
        }
    }

    static class User{
        int id;
        Map<Integer, Integer> viewMap; // <movieId, rating>
        Deque<Integer> history; // <movieId>

        public User(int id) {
            this.id = id;
            viewMap = new HashMap<>();
            history = new ArrayDeque<>();
        }

        public void reset() {
            this.viewMap.clear();
            this.history.clear();
        }

        public boolean watch(Movie movie, int rating) {
            if(viewMap.containsKey(movie.id)) return false;

            movie.addRating(rating);
            viewMap.put(movie.id, rating);
            history.push(movie.id);
            return true;
        }
    }

    int N;
    User[] users;
    Map<Integer, Movie> movieMap ; // <movieId, movie>
    PriorityQueue<MovieNode> pq; // 무비 평점 높은 순(같으면 최신순)

    public UserSolution() {
        this.users = new User[1001];
        for (int i = 1; i <= 1000; i++) {
            this.users[i] = new User(i);
        }
        this.movieMap = new HashMap<>(); // <movieId, movie>
        this.pq = new PriorityQueue<>(); // 무비 평점 높은 순(같으면 최신순)
    }

    void init(int N) {
        movieIdx = 0;
        this.N = N;
        for (int i = 1; i <= N; i++) {
            users[i].reset();
        }
        movieMap.clear();
        pq.clear();
    }

    int add(int mID, int mGenre, int mTotal) {
        if(movieMap.containsKey(mID)) return 0;

        Movie movie = new Movie(mID, mGenre, mTotal);
        movieMap.put(mID, movie);
        pq.offer(new MovieNode(movie.id, movie.genre, movie.total, movie.idx));
        return 1;
    }

    int erase(int mID) {
        if(!movieMap.containsKey(mID)) return 0;

        Movie movie = movieMap.remove(mID);
        movie.isRemoved = true;
        return 1;
    }

    int watch(int uID, int mID, int mRating) {
        if(!movieMap.containsKey(mID)) return 0;

        User user = users[uID];
        Movie movie = movieMap.get(mID);

        if (user.watch(movie, mRating)) {
            pq.offer(new MovieNode(movie.id, movie.genre, movie.total, movie.idx));
            return 1;
        }
        return 0;
    }

    Solution.RESULT suggest(int uID) {
        Solution.RESULT res = new Solution.RESULT();
        res.cnt = 0;

        User user = users[uID];
        int favoriteGenre = -1;
        int maxRating = 0;
        int checkCnt = 0;
        Deque<Integer> tempStack = new ArrayDeque<>();
        while(!user.history.isEmpty() && checkCnt < 5) {
            Integer movieId = user.history.pop();
            if(!movieMap.containsKey(movieId)) continue;
            Movie movie = movieMap.get(movieId);
            if(movie.isRemoved) continue;

            Integer rating = user.viewMap.get(movieId);
            if (rating > maxRating) {
                maxRating = rating;
                favoriteGenre = movie.genre;
            }

            tempStack.push(movieId);
            checkCnt++;
        }

        while(!tempStack.isEmpty()){
            user.history.push(tempStack.pop());
        }

        Queue<MovieNode> tempQueue = new ArrayDeque<>();
        while(!pq.isEmpty() && res.cnt < 5) {
            MovieNode node = pq.poll();

            if (!movieMap.containsKey(node.id)) continue;
            Movie movie = movieMap.get(node.id);
            if (movie.isRemoved) continue;

            // 최신 total 값과 일치하지 않는 이전 버전 노드는 버림
            if (node.total != movie.total) continue;

            tempQueue.offer(node);

            if (favoriteGenre != -1 && movie.genre != favoriteGenre) continue;
            if (user.viewMap.containsKey(movie.id)) continue;

            res.IDs[res.cnt++] = movie.id;
        }

        while (!tempQueue.isEmpty()) {
            pq.offer(tempQueue.poll());
        }

        return res;
    }
}
//소요 시간 2:10:03
