#include <bits/stdc++.h>

using namespace std;

unordered_set<int> unique_nums;
bool visited[8];

bool isPrime(int n) {
    if(n<2) return false;
    for(int i=2; i<=sqrt(n); i++) {
        if(n%i==0) return false;
    }
    return true;
}

void dfs(string numbers, string current) {
    if(!current.empty())
        unique_nums.insert(stoi(current));
    
    for(int i=0; i<numbers.length(); i++) {
        if(visited[i]) continue;
        visited[i] = true;
        dfs(numbers, current+numbers[i]);
        visited[i] = false;
    }
}



int solution(string numbers) {
    int answer = 0;
    
    dfs(numbers, "");
    
    for(int n : unique_nums) {
        answer = isPrime(n) ? answer+1 : answer;
    }
    
    return answer;
}

/*
 * 알고리즘 : DFS + Backtracking (순열문제)
 * 시간복잡도 : O(NxN!)
 */
