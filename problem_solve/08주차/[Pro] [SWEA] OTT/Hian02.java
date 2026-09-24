/*
문제 정의

N명의 사용자가 가입한 OTT 시스템이 있습니다.
영화는 영화 ID, 장르, 총점
이렇게 3개를 가지고 있습니다.

다음 4가지 기능을 구현해야 함

1. add(mID, mGenre, mTotal)
새로운 영화를 등록합니다.
같은 ID의 영화가 이미 등록되어 있다면 등록에 실패하고 0,
정상적으로 등록하면 1을 반환합니다.

2. erase(mID)
등록된 영화를 삭제합니다.
영화가 존재하지 않거나 이미 삭제된 경우 0,
정상적으로 삭제하면 1을 반환합니다.

삭제된 영화는 더 이상 시청하거나 추천할 수 없습니다.

3. watch(uID, mID, mRating)
사용자가 영화를 시청하고 평점을 줍니다.
영화를 시청하면 영화의 총점 += 사용자가 준 평점이 됩니다.

영화가 없거나 삭제되었거나 사용자가 이미 시청한 영화라면 0,
정상적으로 시청하면 1을 반환합니다.


4. suggest(uID)

사용자에게 최대 5개의 영화를 추천합니다.

추천 규칙
1. 사용자가 이미 본 영화와 삭제된 영화는 제외합니다.
2. 사용자의 시청 기록 중 삭제되지 않은 가장 최근 영화 최대 5개를 확인합니다.
3. 그 5개 중 사용자가 가장 높은 평점을 준 영화의 장르를 추천 장르로 선택합니다.
4. 최고 평점 영화가 여러 개라면 그 중 가장 최근에 시청한 영화의 장르를 선택합니다.
5. 시청 목록에 유효한 영화가 없다면 장르에 상관없이 모든 영화를 추천 대상으로 합니다.
6. 추천 대상 영화는 총점이 높은 순, 총점이 같다면 더 최근에 등록된 영화 순으로 최대 5개를 추천합니다.
*/


/*
접근 방법

이 문제에서 가장 중요한 연산은 suggest입니다.
영화는 최대 10,000개까지 등록될 수 있고 suggest도 최대 5,000번 호출됩니다.
suggest가 호출될 때마다 모든 영화를 가져와서 정렬하면 비효율적입니다.
따라서 영화들을 항상 추천 순서대로 정렬된 상태로 관리합니다.

사용할 자료구조

1. HashMap<Integer, Movie> movies
영화 ID를 이용해서 영화를 빠르게 찾기 위해 사용합니다.
add, erase, watch에서 movies.get(mID)로 영화를 바로 찾을 수 있습니다.


2. TreeSet<Movie> allMovies
현재 삭제되지 않은 모든 영화를 저장합니다.
TreeSet의 정렬 기준을 1. 총점 높은 순 2. 등록 순서 최신 순으로 설정합니다.
따라서 suggest가 호출되었을 때별도로 정렬할 필요가 없습니다.


3. TreeSet<Movie>[] genreMovies
장르별로 TreeSet을 따로 관리합니다.
genreMovies[1], genreMovies[2], ... ,genreMovies[5]형태입니다.

사용자의 추천 장르가 결정되면 해당 장르의 영화만 바로 확인할 수 있습니다.

4. ArrayList<Watch>[] history
각 사용자의 시청 기록을 저장합니다.
시청 기록은 시간 순서대로 추가되므로 리스트의 뒤에서부터 확인하면 최근에 시청한 영화부터 볼 수 있습니다.


5. HashSet<Integer>[] watched
각 사용자가 이미 시청한 영화 ID를 저장합니다.
watch에서 이미 본 영화인지 확인하고, suggest에서 이미 본 영화를 추천 대상에서 제외하기 위해 사용합니다.


영화 삭제 처리
영화가 삭제되었다고 해서 모든 사용자의 history를 찾아다니며 직접 삭제하면 비효율적입니다.
따라서 Movie에 active 변수를 두고 삭제하면 active = false 로 표시합니다.
그리고 suggest에서 시청 기록을 확인할 때 active == false인 영화는 건너뜁니다.


총점 변경 처리
TreeSet은 total을 기준으로 정렬되어 있습니다.
그런데 영화 시청으로 total이 변경됩니다.
TreeSet에 들어 있는 상태에서 total을 바로 변경하면 정렬 상태가 깨질 수 있습니다.
따라서 반드시 TreeSet에서 제거 -> total 변경 -> TreeSet에 다시 삽입 순서로 처리합니다.
*/


/*
문제 풀이
*/

import java.util.*;

class UserSolution {
    static class Movie {
        int id;
        int genre;
        int total;

        // 등록 순서
        // 값이 클수록 최근에 등록됨
        int order;

        // 삭제 여부
        boolean active;
        Movie(int id, int genre, int total, int order) {
            this.id = id;
            this.genre = genre;
            this.total = total;
            this.order = order;
            active = true;
        }
    }

    static class Watch {
        Movie movie;
        // 해당 사용자가 준 평점
        int rating;

        Watch(Movie movie, int rating) {
            this.movie = movie;
            this.rating = rating;
        }
    }

    // 영화 ID -> 영화
    HashMap<Integer, Movie> movies;

    // 전체 영화
    TreeSet<Movie> allMovies;

    // 장르별 영화
    TreeSet<Movie>[] genreMovies;

    // 사용자별 시청 기록
    ArrayList<Watch>[] history;

    // 사용자별 이미 시청한 영화
    HashSet<Integer>[] watched;

    // 영화 등록 순서
    int order;

    /*
    영화 추천 우선순위
    1. 총점이 높은 영화
    2. 총점이 같으면 최근에 등록된 영화
    */
    Comparator<Movie> comparator = (a, b) -> {
        if (a.total != b.total) {
            return Integer.compare(b.total, a.total);
        }

        if (a.order != b.order) {
            return Integer.compare( b.order, a.order);
        }

        return Integer.compare(b.id,a.id);
    };

    /*
    초기화
    */
    @SuppressWarnings("unchecked")
    void init(int N){
        order = 0;
        movies = new HashMap<>();
        allMovies = new TreeSet<>(comparator);
        genreMovies = new TreeSet[6];

        for (int i = 1; i <= 5; i++) {
            genreMovies[i] = new TreeSet<>(comparator);
        }
        history = new ArrayList[N + 1];
        watched = new HashSet[N + 1];

        for (int i = 1; i <= N; i++) {
            history[i] =  new ArrayList<>();
            watched[i] =  new HashSet<>();
        }
    }

    /*
    영화 등록
    */
    int add(int mID, int mGenre, int mTotal){
        // 같은 ID의 영화가 이미 등록되어 있음
        if (movies.containsKey(mID)) {
            return 0;
        }

        Movie movie = new Movie( mID, mGenre, mTotal, ++order);
        // ID로 영화 찾기
        movies.put(mID,movie);

        // 전체 영화 목록
        allMovies.add(movie);

        // 해당 장르 영화 목록
        genreMovies[mGenre].add(movie);
        return 1;
    }


    /*
    영화 삭제
    */
    int erase(int mID){
        Movie movie = movies.get(mID);


        // 등록되지 않은 영화 또는 이미 삭제된 영화
        if (movie == null || !movie.active) {
            return 0;
        }
        // 추천 목록에서 제거
        allMovies.remove(movie);
        genreMovies[movie.genre].remove(movie);

        // 삭제 표시
        movie.active = false;
        return 1;
    }

    /*
    영화 시청
    */
    int watch(int uID, int mID, int mRating){
        Movie movie =  movies.get(mID);
        // 영화가 존재하지 않거나
        // 이미 삭제된 경우
        if (movie == null || !movie.active) {
            return 0;
        }

        // 이미 시청한 영화
        if (watched[uID].contains(mID)) {
            return 0;
        }

        /*
        total은 TreeSet의 정렬 기준이므로  total을 변경하기 전에 TreeSet에서 먼저 제거
        */

        allMovies.remove(movie);
        genreMovies[movie.genre].remove(movie);

        // 영화 총점 증가
        movie.total += mRating;
        // 변경된 총점 기준으로 다시 삽입
        allMovies.add(movie);
        genreMovies[movie.genre].add(movie);

        // 이미 본 영화로 기록
        watched[uID].add(mID);

        // 시청 기록 추가
        history[uID].add(new Watch(movie,mRating ));
        return 1;
    }


    /*
    영화 추천
    */
    Solution.RESULT suggest(int uID){
        Solution.RESULT res = new Solution.RESULT();
      
        res.cnt = 0;

        ArrayList<Watch> list = history[uID];

        int recentCount = 0;
        int bestRating = -1;
        int targetGenre = 0;

        /*
        최근 시청 영화부터 확인, 삭제되지 않은 영화만 기준으로 최대 5개까지 확인
        */
        for (int i = list.size() - 1; i >= 0 && recentCount < 5; i--) {
            Watch watch = list.get(i);

            // 삭제된 영화는 무시
            if (!watch.movie.active) {
                continue;
            }
            recentCount++;
            /*
            뒤에서부터 확인하므로 가장 최근 영화부터 확인합니다.
            따라서 평점이 같다면 먼저 발견한 영화가 더 최근 영화입니다.
            그래서 평점이 더 클 때만 targetGenre를 변경합니다.
            */

            if (watch.rating > bestRating) {
                bestRating = watch.rating;
                targetGenre = watch.movie.genre;
            }
        }
        TreeSet<Movie> candidates;

        /*
        유효한 시청 기록이 없다면 장르 상관없이 전체 영화에서 추천
        */
        if (recentCount == 0) {
            candidates = allMovies;
        }

        /*
        시청 기록이 있다면 선택된 장르에서만 추천
        */
        else {
            candidates = genreMovies[targetGenre];
        }


        /*
        TreeSet은 이미 총점 높은 순, 등록 최신 순으로 정렬되어 있습니다.
        */
        for (Movie movie : candidates) {
            // 이미 시청한 영화는 추천 제외
            if (watched[uID].contains(movie.id)) {
                continue;
            }

            res.IDs[res.cnt] = movie.id;
            res.cnt++;

            // 최대 5개 추천
            if (res.cnt == 5) {
                 break;
            }
        }
        return res;
    }
}
