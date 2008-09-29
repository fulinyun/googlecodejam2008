import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class Knight {

	public static int mod = 10007;
	public static int H, W;
	public static Point[] stone1;
	public static Point[] stone;
	public static int[] itable;
	public static int[] ftable;
	public static int[] iftable;
	
	public static void setup() {
		itable = new int[mod];
		ftable = new int[mod];
		iftable = new int[mod];
		ftable[0] = 1;
		iftable[0] = 1;
		for (int i = 1; i < mod; i++) {
			itable[i] = inverse(i, mod);
			iftable[i] = iftable[i-1]*itable[i]%mod;
			ftable[i] = ftable[i-1]*i%mod;
		}
	}
	
	public static void main(String[] args) {
		setup();
		Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		for (int i = 0; i < N; i++) {
			H = scanner.nextInt()-1;
			W = scanner.nextInt()-1;
			int R = scanner.nextInt();
			stone1 = new Point[R];
			int size = 0;
			for (int j = 0; j < R; j++) {
				int r = scanner.nextInt()-1;
				int c = scanner.nextInt()-1;
				stone1[j] = new Point(r, c);
				if ((r+c)%3 == 0 && r <= 2*c && c <= 2*r) size++;
			}
			stone = new Point[size];
			int k = 0;
			for (int j = 0; j < R; j++) {
				if ((stone1[j].r+stone1[j].c)%3 == 0 && stone1[j].r <= 2*stone1[j].c && stone1[j].c <= 2*stone1[j].r) {
					stone[k] = stone1[j];
					k++;
				}
			}
			
			Arrays.sort(stone, new Comparator<Point>() {
				public int compare(Point a, Point b) {
					if (a.r+a.c < b.r+b.c) return -1;
					if (a.r+a.c > b.r+b.c) return 1;
					return 0;
				}
			});
			System.out.printf("Case #%d: %d\n", i+1, solve(H, W));
		}
	}
	
	public static int solve(int H, int W) {
		if (H == 0 && W == 0) return 1;
		if ((H+W)%3 != 0 || H > 2*W || W > 2*H) return 0;
		int ret = cal();
		ret %= mod;
		if (ret < 0) ret += mod;
		return ret;
	}

	public static int cal() {
		int ret = 0;
		int len = (1<<stone.length);
		for (int i = 0; i < len; i++) {
			Point pre = new Point(0, 0);
			int sign = 1;
			int par = 1;
			for (int j = 0; j < stone.length; j++) if ((i & (1<<j)) != 0) {
				par *= shift(stone[j].r-pre.r, stone[j].c-pre.c);
				par %= mod;
				sign = -sign;
				pre = stone[j];
			}
			par *= shift(H-pre.r, W-pre.c);
			par %= mod;
			if (sign == -1) ret -= par;
			else ret += par;
			ret %= mod;
		}
		return ret;
	}
	
	public static int shift(int h, int w) {
		int m = (h+w)/3;
		int n = h-m;
		if (n < 0 || n > m) return 0;
		return modc(m, n);
	}
	
	public static int modc(int m, int n) {
		int ret = 1;
		if (m-n < n) n = m-n;
		while (n != 0) {
			ret *= c(m%mod, n%mod);
			ret %= mod;
			m /= mod;
			n /= mod;
		}
		return ret;
	}
	
	public static int c(int m, int n) {
		if (m < n) return 0;
		return ftable[m]*iftable[n]%mod*iftable[m-n]%mod;
	}
	
	public static int inverse(int i, int mod) {
		if (i == 1) return 1;
		int ret = (1-mod*inverse(mod%i, i))/i%mod;
		if (ret < 0) ret += mod;
		return ret;
	}
}

class Point {
	public int r;
	public int c;
	public Point(int rr, int cc) {
		r = rr;
		c = cc;
	}
}
