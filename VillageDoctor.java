package samsung;

import java.util.Scanner;

public class VillageDoctor {

	private static int N, E, T;
	private static double[][] graph;
	private static double[][] prob;
	private static int ans_node;
	private static double ans_prob;
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		int tc = scan.nextInt();
		
		for(int caseN = 1; caseN <= tc; caseN++) {
			
			N = scan.nextInt();
			E = scan.nextInt();
			T = scan.nextInt();
			
			graph = new double[N+1][N+1];
			prob = new double[N+1][T+1];
			
			for (int i = 0; i <= N; i++) {
				
				for (int j = 0; j <= N; j++) {
					graph[i][j] = -1;
				}
				
				for (int k = 0; k <= T; k++) {
					prob[i][k] = 0;
				}
			}
			
			while(E > 0) {
				int u = scan.nextInt();
				int v = scan.nextInt();
				double prb = scan.nextDouble();
				
				graph[u][v] = prb;
				
				E--;
			}
			
			ans_node = 0;
			ans_prob = 0.0;
			
			dfs(1, 0, 1.0);
			
			if(ans_node == 0)
				System.out.printf("Case#%d 0", caseN);
			else
				System.out.printf("Case#%d %d %.6f", caseN, ans_node, ans_prob);
			System.out.println();
		}
		
		scan.close();
	}

	private static void dfs(int node, int time, double probab) {
		
		if(time > T) {			
			return;
		}
		if(time == T || (time+10) > T) {
			
			prob[node][time] += probab;
			
			if(prob[node][time] > ans_prob) {
				ans_prob = prob[node][time];
				ans_node = node;
			}
			else if(prob[node][time] == ans_prob && node < ans_node) {
				ans_node = node;
			}
			
			return;
		}
		
		for (int i = 1; i <= N; i++) {
			
			if(graph[node][i] != -1) {
				dfs(i, time+10, probab*graph[node][i]);
			}
			
		}
	}

}
