package samsung;

import java.util.Scanner;

public class Pipe_Endoscope {
	
	private static int T, N, M, R, C, L;
	private static long ans;
	private static int[][] mat;
	private static boolean[][] visited;
	private static int[] moveX = {0, 0, -1, 1};
	private static int[] moveY = {1, -1, 0, 0};
	
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		
		
		T = scan.nextInt();
		
		while(T > 0) {
			N = scan.nextInt();
			M = scan.nextInt();
			R = scan.nextInt();
			C = scan.nextInt();
			L = scan.nextInt();
			
			init();
			
			T--;
		}
		
		scan.close();
	}

	private static void init() {
		
		mat = new int[N][M];
		visited = new boolean[N][M];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				mat[i][j] = scan.nextInt();
			}
		}
		
		ans = 0;
		if(mat[R][C] != 0) {
			ans = connectedPipe(R, C);
		}
		
		System.out.println(ans);
	}

	private static long connectedPipe(int startX, int startY) {
		
		int ans = 1;
		Queue q = new Queue();
		QueueNode source = new QueueNode(startX, startY);
		source.dist = 1;
		q.enq(source);
		visited[source.x][source.y] = true;
		
		while(!q.isEmpty()) {
			
			QueueNode v = q.deq();
			
			for (int i = 0; i < 4; i++) {
				
				if(isValid(v.x+moveX[i], v.y+moveY[i], v.dist+1)) {
					
					if(canGo(mat[v.x][v.y], i, mat[v.x+moveX[i]][v.y+moveY[i]])) {
						
						QueueNode u = new QueueNode(v.x+moveX[i], v.y+moveY[i]);
						u.dist = v.dist + 1;
						visited[u.x][u.y] = true;
						q.enq(u);
						ans++;
					}
				}
			}
		}
		
		return ans;
	}

	private static boolean canGo(int type, int dir, int next) {
		
		if(dir == 0) {
			if(type == 1 || type == 3 || type == 4 || type == 5) {
				return (next == 1 || next == 3 || next == 6 || next == 7);
			}
		}
		else if(dir == 1) {
			if(type == 1 || type == 3 || type == 6 || type == 7) {
				return (next == 1 || next == 3 || next == 4 || next == 5);
			}
		}
		else if(dir == 2) {
			if(type == 1 || type == 2 || type == 4 || type == 7) {
				return (next == 1 || next == 2 || next == 5 || next == 6);
			}
		}
		else if(dir == 3) {
			if(type == 1 || type == 2 || type == 5 || type == 6) {
				return (next == 1 || next == 2 || next == 4 || next == 7);
			}
		}
		
		return false;
	}

	private static boolean isValid(int x, int y, int length) {

		if(x < 0 || x >= N || y < 0 || y >= M)
			return false;
		if(visited[x][y] || length > L || mat[x][y] == 0)
			return false;
		
		return true;
	}

}
