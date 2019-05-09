package samsung;

import java.util.Scanner;

public class Bipartite {
	
	private static Scanner scan = new Scanner(System.in);
	
	private static int[][] G;
	private static int[] color;
	private static int V;

	public static void main(String[] args) {

		int T = scan.nextInt();
		
		while(T > 0) {
			V = scan.nextInt();
			init();
			checkBipartite();
			T--;
		}
	}
	
	private static void init() {
		
		G = new int[V][V];
		color = new int[V];
		
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				G[i][j] = scan.nextInt();
			}
			color[i] = -1;
		}
	}

	private static void checkBipartite() {
		
		for (int i = 0; i < V; i++) {
			
			if(color[i] == -1) {
				color[i] = 0;
				if(!dfs(i)) {
					System.out.println("-1");
					return;
				}
			}
		}
		
		for (int i = 0; i < V; i++) {
			if(color[i] == 0) {
				System.out.print(i + " ");
			}
		}
		System.out.println();
		for (int i = 0; i < V; i++) {
			if(color[i] == 1) {
				System.out.print(i + " ");
			}
		}
	}

	private static boolean dfs(int i) {
		
		for (int j = 0; j < V; j++) {
			
			if(G[i][j] == 1 && color[j] == -1) {
				
				color[j] = 1 - color[i];
				
				if(!dfs(j))
					return false;
			}
			else if(G[i][j] == 1 && color[i] == color[j]) {
				return false;
			}
		}
		return true;
	}

}
