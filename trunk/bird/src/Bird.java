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
			postprocess();
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
		for (Point p : not) {
			if (h >= p.h && w >= p.w && p.type == 1) return "NOT BIRD";
			if (h >= p.h && w <= p.w && p.type == 4) return "NOT BIRD";
			if (h <= p.h && w >= p.w && p.type == 2) return "NOT BIRD";
			if (h <= p.h && w <= p.w && p.type == 3) return "NOT BIRD";
			if (h == p.h && w == p.w && p.type == 0) return "NOT BIRD";
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
	
	public static void postprocess() {
		if (upperheight != -1 && upperweight != -1) for (Point p : not) {
			if (p.h > upperheight && p.w > upperweight) {
				p.type = 1;
			} else if (p.h > upperheight && p.w < lowerweight) {
				p.type = 4;
			} else if (p.h < lowerheight && p.w > upperweight) {
				p.type = 2;
			} else if (p.h < lowerheight && p.w < lowerweight) {
				p.type = 3;
			} else if (p.w <= upperweight && p.w >= lowerweight) {
				if (upperheight < p.h) {
					p.w = 0; 
					p.type = 1;
				}
				if (lowerheight > p.h) {
					p.w = 0;
					p.type = 2;
				}
			} else if (p.h <= upperheight && p.h >= lowerheight) {
				if (upperweight < p.w) {
					p.h = 0; 
					p.type = 1;
				}
				if (lowerweight > p.w) {
					p.h = 0;
					p.type = 4;
				}
			}
		}
	}

	public static void process(int h, int w, String x) {
		if (x.equals("NOT")) {
			not.add(new Point(h, w, 0));
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
	public int type;//phase
	
	public Point(int hh, int ww, int t) {
		h = hh;
		w = ww;
		type = t;
	}
}
