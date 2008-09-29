import java.util.HashSet;
import java.util.Scanner;


public class Bird {

	public static int upperheight;
	public static int lowerheight;
	public static int upperweight;
	public static int lowerweight;
	public static HashSet<Point> not = new HashSet<Point>();
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int C = scanner.nextInt();
		for (int i = 0; i < C; i++) {
			init();
			int N = scanner.nextInt();
			scanner.nextLine();
			for (int j = 0; j < N; j++) {
				String line = scanner.nextLine();
				Scanner sc = new Scanner(line);
				int H = sc.nextInt();
				int W = sc.nextInt();
				String X = sc.next();
				process(H, W, X);
			}
			System.out.print("Case #" + (i+1) + ":\n");
			int M = scanner.nextInt();
			for (int j = 0; j < M; j++) {
				int H = scanner.nextInt();
				int W = scanner.nextInt();
				System.out.print(judge(H, W)+"\n");
			}
		}
	}
	
	public static String judge(int h, int w) {
		if (h >= lowerheight && h <= upperheight && w >= lowerweight && w <= upperweight) return "BIRD";
		int lh = lowerheight;
		int uh = upperheight;
		int lw = lowerweight;
		int uw = upperweight;
		if (h > uh) uh = h;
		if (h < lh) lh = h;
		if (w > uw) uw = w;
		if (w < lw) lw = w;
		for (Point p : not) {
			if (uh >= p.h && lh <= p.h && p.w >= lw && p.w <= uw) return "NOT BIRD";
		}
		return "UNKNOWN";
	}
	
	public static void init() {
		upperheight = -1;
		upperweight = -1;
		lowerheight = 1000001;
		lowerweight = 1000001;
		not.clear();
	}
	
	public static void process(int h, int w, String x) {
		if (x.equals("NOT")) {
			not.add(new Point(h, w));
		} else {
			if (upperheight < h) upperheight = h;
			if (lowerheight > h) lowerheight = h;
			if (upperweight < w) upperweight = w;
			if (lowerweight > w) lowerweight = w;
		}
	}
}

class Point {
	public int h;
	public int w;
	
	public Point(int hh, int ww) {
		h = hh;
		w = ww;
	}
}
