#include <bits/stdc++.h>

using namespace std;

vector<vector<int>> answer;

void hanoi(int n, int from, int mid, int to) {
    if(n==1) {
        answer.push_back({from, to});
        return;
    }
    hanoi(n-1, from, to, mid);
    answer.push_back({from, to});
    hanoi(n-1, mid, from, to);
}

vector<vector<int>> solution(int n) {
    ios::sync_with_stdio(false);
    hanoi(n, 1, 2, 3);
    return answer;
}

/*
 * 알고리즘 : DFS
 * 시간복잡도 : O(2^N)
 */
