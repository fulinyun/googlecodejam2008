import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;


public class Recipe {

	public static HashMap<String, ArrayList<String>> ingredient = new HashMap<String, ArrayList<String>>();
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int C = scanner.nextInt();
		for (int i = 0; i < C; i++) {
			int N = scanner.nextInt();
			ingredient.clear();
			for (int j = 0; j < N; j++) {
				String key = scanner.next();
				int len = scanner.nextInt();
				ArrayList<String> value = new ArrayList<String>(len);
				for (int k = 0; k < len; k++) value.add(scanner.next());
				ingredient.put(key, value);
			}
			System.out.printf("Case #%d: %d\n", i+1, solve());
		}
	}
	
	public static int solve() {
		HashMap<String, Integer> bowl = new HashMap<String, Integer>();
		for (String mix : ingredient.keySet()) solve(mix, bowl);
		int max = 0;
		for (String mix: bowl.keySet()) if (bowl.get(mix) > max) max = bowl.get(mix);
		return max;
	}
	
	public static void solve(String mix, final HashMap<String, Integer> bowl) {
		ArrayList<String> list = ingredient.get(mix);
		ArrayList<String> mixlist = new ArrayList<String>();
		for (String s : list) if (Character.isUpperCase(s.charAt(0))) mixlist.add(s);
		if (mixlist.isEmpty()) {
			bowl.put(mix, 1);
//			System.out.println(mix + " : 1");
			return;
		}
		for (String s : mixlist) if (!bowl.keySet().contains(s)) solve(s, bowl);
		String[] array = new String[mixlist.size()];
		mixlist.toArray(array);
		Arrays.sort(array, new Comparator<String>() {
			public int compare(String a, String b) {
				if (bowl.get(a) > bowl.get(b)) return -1;
				if (bowl.get(a) < bowl.get(b)) return 1;
				return 0;
			}
		});
//		System.out.print(mix + " :");
//		for (String s : array) System.out.print(" " + s + ":" + bowl.get(s).intValue());
//		System.out.println();
		int t = bowl.get(array[0]);
		int remain = 0;
		for (int i = 1; i < array.length; i++) {
			int pre = bowl.get(array[i-1]);
			int cur = bowl.get(array[i]);
			if (pre == cur) {
				if (remain > 0) remain--;
				else t += 1;
			} else if (pre > cur+1) remain += (pre-cur-1);
//			System.out.println(i + " : t = " + t + " ; remain = " + remain);
		}
		remain += bowl.get(array[array.length-1])-1;
		if (remain > 0) bowl.put(mix, t);
		else bowl.put(mix, t+1);
	}
	
}
