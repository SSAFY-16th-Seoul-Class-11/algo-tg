#include <bits/stdc++.h>

using namespace std;

int dx[] = {0, 1, 1, 1, 0, -1, -1, -1};
int dy[] = {-1, -1, 0, 1, 1, 1, 0, -1};

int solution(vector<int> arrows) {
    int answer = 0;
    
    set<pair<int, int>> visited_nodes; // 이미 방문한 점
    set<pair<pair<int, int>, pair<int, int>>> visited_edges; // 이미 지나온 간선
    
    int x = 0;
    int y = 0;
    visited_nodes.insert({x, y});
    
    for(int command : arrows) {
        for(int i=0; i<2; i++) { // 2배 스케일 했기 때문에 한번에 두칸씩 이동 for 대각선 교차점 처리!
            int nx = x + dx[command];
            int ny = y + dy[command];
            
            if(visited_edges.count({{x, y}, {nx, ny}}) == 0) { // 처음 지나는 경로일 경우
                if(visited_nodes.count({nx, ny})!=0) {
                    answer++;
                }
                
                // 양방향 경로 모두 등록
                visited_edges.insert({{x, y}, {nx, ny}});
                visited_edges.insert({{nx, ny}, {x, y}});
                // 정점 등록
                visited_nodes.insert({nx, ny});
            }
            
            x = nx;
            y = ny;
        }
    }
    
    return answer;
}

/*
 * 알고리즘: visited가 중복으로 체크되었을때가 방의 갯수! 단, 연속으로 visited가 중복될 경우는 제외하자. 왜냐하면 선을 따라서 이동하는 것일 수도 있기 때문이다.
 * --> 겹치는 정점(node) 체크 전 간선(edge)가 겹쳤는지 확인하고 해당사항 없으면 정점 체크하자.
 * p.s. 이것도 2배 스케일업해서 인접한 정점끼리의 대각선 교차점을 체크해주자.
 * 시간복잡도: O(N)
 */
