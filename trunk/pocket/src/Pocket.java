import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Pocket {

	public static final int size = 7000;
	public static boolean[][] board = new boolean[size][size];
	public static ArrayList<VLine> vline = new ArrayList<VLine>();
	public static ArrayList<HLine> hline = new ArrayList<HLine>();
	public static int count = 0;
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		for (int i = 0; i < N; i++) {
			input(sc);
			work();
			output(i);
		}
		sc.close();
	}
	
	public static void output(int ind) {
		System.out.println("Case #" + (ind+1) + ": " + count);
	}
	
	public static void work() {
		for (int i = 0; i < size; i++) Arrays.fill(board[i], false);
		count = 0;
		for (int i = 0; i < size; i++) {
			ArrayList<Integer> vcut = new ArrayList<Integer>();
			for (VLine vl : vline) if (vl.y == i) vcut.add(vl.x);
			Integer[] t = new Integer[vcut.size()];
			vcut.toArray(t);
			Arrays.sort(t);
			for (int j = 2; j < vcut.size(); j += 2) for (int k = t[j-1]; k < t[j]; k++) if (!board[k][i]) {
				board[k][i] = true;
				count++;
			}
		}
		for (int i = 0; i < size; i++) {
			ArrayList<Integer> hcut = new ArrayList<Integer>();
			for (HLine hl : hline) if (hl.x == i) hcut.add(hl.y);
			Integer[] t = new Integer[hcut.size()];
			hcut.toArray(t);
			Arrays.sort(t);
			for (int j = 2; j < hcut.size(); j += 2) for (int k = t[j-1]; k < t[j]; k++) if (!board[i][k]) {
				board[i][k] = true;
				count++;
			}
		}
	}
	
	public static void input(Scanner sc) {
		vline.clear();
		hline.clear();
		int x = 3500, y = 3500, d = 0;
		int[] dirx = {0, 1, 0, -1};
		int[] diry = {1, 0, -1, 0};
		int L = sc.nextInt();
		for (int i = 0; i < L; i++) {
			String S = sc.next();
			int T = sc.nextInt();
			for (int j = 0; j < T; j++) {
				for (int k = 0; k < S.length(); k++) {
					switch (S.charAt(k)) {
					case 'F': 
						switch (d) {
						case 0: 
							vline.add(new VLine(x, y));
							break;
						case 1:
							hline.add(new HLine(x, y));
							break;
						case 2:
							vline.add(new VLine(x, y-1));
							break;
						case 3:
							hline.add(new HLine(x-1, y));
							break;
						}
						x += dirx[d];
						y += diry[d];
						break;
					case 'L':
						d--;
						if (d < 0) d += 4;
						break;
					case 'R':
						d++;
						if (d > 3) d -= 4;
						break;
					}
				}
			}
		}
	}
}

class VLine {
	public int x;
	public int y;
	public VLine(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class HLine {
	public int y;
	public int x;
	public HLine(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
