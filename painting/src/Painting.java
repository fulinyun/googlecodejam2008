import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;


public class Painting {

	public static HashMap<String, ArrayList<Painter>> painter = new HashMap<String, ArrayList<Painter>>();
	public static int fenceLength = 10000;
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int T = scanner.nextInt();
		for (int i = 0; i < T; i++) {
			painter.clear();
			int N = scanner.nextInt();
			for (int j = 0; j < N; j++) {
				String C = scanner.next();
				int A = scanner.nextInt();
				int B = scanner.nextInt();
				if (painter.containsKey(C)) painter.get(C).add(new Painter(A, B));
				else {
					ArrayList<Painter> value = new ArrayList<Painter>();
					value.add(new Painter(A, B)); 
					painter.put(C, value);
				}
			}
			System.out.printf("Case #%d: %s\n", i+1, solve());
		}
	}
	
	public static String solve() {
		int min = 400;
		String[] colors = new String[painter.keySet().size()];
		painter.keySet().toArray(colors);
		if (colors.length <= 2) {
			ArrayList<Painter> plist = new ArrayList<Painter>();
			for (String s : colors) plist.addAll(painter.get(s));
			Painter[] parray = new Painter[plist.size()];
			plist.toArray(parray);
			Arrays.sort(parray, new Comparator<Painter>() {
				public int compare(Painter a, Painter b) {
					if (a.a < b.a) return -1;
					if (a.a > b.a) return 1;
					if (a.b < b.b) return -1;
					if (a.b > b.b) return 1;
					return 0;
				}
			});
			int next = 1;
			int count = 0;
			for (int m = 0; m < parray.length; ) {
				int n = m;
				int maxb = 0;
				int maxn = -1;
				while (n < parray.length && parray[n].a <= next) {
					if (parray[n].b > maxb) {
						maxb = parray[n].b;
						maxn = n;
					}
					n++;
				}
				next = maxb+1;
				count++;
				if (next > fenceLength) break;
				if (maxn != -1) m = maxn+1;
				else break;
			}
			if (next > fenceLength && count < min) min = count;

		} else {
			for (int i = 0; i < colors.length; i++) for (int j = i+1; j < colors.length; j++) for (int k = j+1; k < colors.length; k++) {
				ArrayList<Painter> plist = new ArrayList<Painter>();
				plist.addAll(painter.get(colors[i]));
				plist.addAll(painter.get(colors[j]));
				plist.addAll(painter.get(colors[k]));
				Painter[] parray = new Painter[plist.size()];
				plist.toArray(parray);
				Arrays.sort(parray, new Comparator<Painter>() {
					public int compare(Painter a, Painter b) {
						if (a.a < b.a) return -1;
						if (a.a > b.a) return 1;
						if (a.b < b.b) return -1;
						if (a.b > b.b) return 1;
						return 0;
					}
				});
				int next = 1;
				int count = 0;
				for (int m = 0; m < parray.length; ) {
					int n = m;
					int maxb = 0;
					int maxn = -1;
					while (n < parray.length && parray[n].a <= next) {
						if (parray[n].b > maxb) {
							maxb = parray[n].b;
							maxn = n;
						}
						n++;
					}
					next = maxb+1;
					count++;
					if (next > fenceLength) break;
					if (maxn != -1) m = maxn+1;
					else break;
				}
				if (next > fenceLength && count < min) min = count;
			}
		}
		if (min != 400) return ""+min;
		else return "IMPOSSIBLE";
	}
}

class Painter {
	
	public int a;
	public int b;
	
	public Painter(int aa, int bb) {
		a = aa;
		b = bb;
	}
}
