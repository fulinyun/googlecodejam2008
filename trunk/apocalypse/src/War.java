import java.util.Scanner;


public class War {

	public static int C, R, c, r;
	public static int[][] start;
	public static int[][] previous;
	public static int[][] current;
	public static int dirr[] = {-1, 0, 0, 1};
	public static int dirc[] = {0, -1, 1, 0};
	public static int dayPassed = 0;
	public static int max = 0;
	public static int forever = 99999999;
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int T = scanner.nextInt();
		for (int i = 0; i < T; i++) {
			dayPassed = 0;
			C = scanner.nextInt();
			R = scanner.nextInt();
			c = scanner.nextInt()-1;
			r = scanner.nextInt()-1;
			start = new int[R][C];
			max = 0;
			for (int j = 0; j < R; j++) for (int k = 0; k < C; k++) start[j][k] = scanner.nextInt();
			System.out.printf("Case #%d: %s\n", i+1, solve());
		}
	}

	public static boolean zeroAround(int[][] state, int r, int c) {
		for (int i = 0; i < 4; i++) {
			int tr = r + dirr[i];
			int tc = c + dirc[i];
			if (tr < 0 || tr >= R || tc < 0 || tc >= C) continue;
			if (state[tr][tc] != 0) return false;
		}
		return true;
	}
	
	public static String solve() {
		if (zeroAround(start, r, c)) return "forever";
		previous = new int[R][C];
		current = new int[R][C];
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) previous[i][j] = start[i][j];
		proceed(0);
		for (int i = 0; i < 4; i++) {
			if (max == forever) break;
			proceed(i, 0);
		}
		if (max == forever) return "forever";
		return max + " day(s)";
	}
	
	public static void attack(int i, int j) {
		if (zeroAround(previous, i, j) || previous[i][j] == 0) return;
		int S = previous[i][j];
		int[] neighbourS = {0, 0, 0, 0};
		for (int k = 0; k < 4; k++) {
			int ar = i + dirr[k];
			int ac = j + dirc[k];
			if (ar >= R || ar < 0 || ac >= C || ac < 0) continue;
			neighbourS[k] = previous[ar][ac];
		}
		int max = 0;
		for (int k = 1; k < 4; k++) if (neighbourS[k] > neighbourS[max]) max = k;
		int ar = i + dirr[max];
		int ac = j + dirc[max];
		current[ar][ac] -= S;

	}
	
	public static void proceed(int day) {
		if (max == forever) return;
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) current[i][j] = previous[i][j];
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) if (i != r || j != c) attack(i, j);
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) if (current[i][j] < 0) current[i][j] = 0;
		if (current[r][c] == 0) {
			if (max < day) max = day;
			return;
		}
		if (zeroAround(current, r, c)) {
			max = forever;
			return;
		}
		int[][] temp = new int[R][C];
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) temp[i][j] = previous[i][j];
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) previous[i][j] = current[i][j];
		proceed(day+1);
		for (int i = 0; i < 4; i++) {
			if (max == forever) return;
			proceed(i, day+1);
		}
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) previous[i][j] = temp[i][j];

	}
	
	public static void proceed(int dir, int day) {
		if (max == forever) return;
		int attackr = r + dirr[dir];
		int attackc = c + dirc[dir];
		if (attackr >= R || attackr < 0 || attackc >= C || attackc < 0 || previous[attackr][attackc] == 0) return;
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) current[i][j] = previous[i][j];
		current[attackr][attackc] -= previous[r][c];
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) if (i != r || j != c) attack(i, j);
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) if (current[i][j] < 0) current[i][j] = 0;
		if (current[r][c] == 0) {
			if (max < day) max = day;
			return;
		}
		if (zeroAround(current, r, c)) {
			max = forever;
			return;
		}

		int[][] temp = new int[R][C];
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) temp[i][j] = previous[i][j];
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) previous[i][j] = current[i][j];
		proceed(day+1);
		for (int i = 0; i < 4; i++) {
			if (max == forever) return;
			proceed(i, day+1);
		}
		for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) previous[i][j] = temp[i][j];
	}
}
