import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;


public class Subtree {

	public static int N, M;
	public static HashMap<Integer, ArrayList<Integer>> bigtree = new HashMap<Integer, ArrayList<Integer>>();
	public static HashMap<Integer, ArrayList<Integer>> smalltree = new HashMap<Integer, ArrayList<Integer>>();
	public static int[] bigtreelevel;
	public static int[] smalltreelevel;
	public static int[] bigtreeorder;
	public static int[] smalltreeorder;
	public static boolean[][] fittable;
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		int C = sc.nextInt();
		for (int i = 0; i < C; i++) {
			bigtree.clear();
			smalltree.clear();
			N = sc.nextInt();
			for (int j = 0; j < N-1; j++) {
				int n1 = sc.nextInt()-1;
				int n2 = sc.nextInt()-1;
				addTo(bigtree, n1, n2);
				addTo(bigtree, n2, n1);
			}
			M = sc.nextInt();
			for (int j = 0; j < M-1; j++) {
				int n1 = sc.nextInt()-1;
				int n2 = sc.nextInt()-1;
				addTo(smalltree, n1, n2);
				addTo(smalltree, n2, n1);
			}
			if (isSub()) System.out.printf("Case #%d: YES\n", i+1);
			else System.out.printf("Case #%d: NO\n", i+1);
		}
	}
	
	public static void printTree(HashMap<Integer, ArrayList<Integer>> tree) {
		for (Integer in : tree.keySet()) {
			System.out.print(in.intValue() + ":");
			for (Integer j : tree.get(in)) System.out.print("\t" + j.intValue());
			System.out.println();
		}
	}
	
	public static void addTo(HashMap<Integer, ArrayList<Integer>> tree, int n1, int n2) {
		if (tree.keySet().contains(n1)) tree.get(n1).add(n2);
		else {
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(n2);
			tree.put(n1, list);
		}
		
	}
	
	public static boolean isSub() {
		if (smalltree.isEmpty()) return true;
		smalltreelevel = new int[smalltree.size()];
		smalltreeorder = new int[smalltree.size()];
		bigtreelevel = new int[bigtree.size()];
		bigtreeorder = new int[bigtree.size()];
		organize(smalltree, 0, smalltreelevel, smalltreeorder);
		for (int i = 0; i < N; i++) {
//			System.out.println("-----bigtree------");
//			printTree(bigtree);
			organize(bigtree, i, bigtreelevel, bigtreeorder);
			if (fit(0, i)) return true;
		}
		return false;
	}
	
	public static void organize(HashMap<Integer, ArrayList<Integer>> tree, int root, int[] level, int[] order) {
		boolean[] visited = new boolean[level.length];
		Arrays.fill(visited, false);
		ArrayList<Integer> array = new ArrayList<Integer>();
		level[root] = 0;
		visited[root] = true;
		array.add(root);
		int p = 0;
		while (p < array.size()) {
			int node = array.get(p);
			ArrayList<Integer> list = tree.get(node);
			for (Integer i : list) if (!visited[i]) {
				visited[i] = true;
//				System.out.println("add " + i.intValue());
				array.add(i);
//				for (Integer it : array) System.out.print(" " + it.intValue());
//				System.out.println();
				level[i] = level[node]+1;
			}
			p++;
		}
//		System.out.println("arraysize = " + array.size());
		for (int i = 0; i < order.length; i++) order[i] = array.get(i);
	}
	
	public static boolean fit(int root1, int root2) {
		fittable = new boolean[M][N];
		for (int i = 0; i < M; i++) Arrays.fill(fittable[i], false);
		for (int i = M-1; i >= 0; i--) for (int j = N-1; j >= 0; j--) {
			int n1 = smalltreeorder[i];
			int n2 = bigtreeorder[j];
			fittable[n1][n2] = fit1(n1, n2);
		}
		return fittable[root1][root2];
	}
	
	public static boolean fit1(int node1, int node2) {
		if (smalltreelevel[node1] != bigtreelevel[node2]) return false;
		ArrayList<Integer> son1 = getSon(smalltree, node1, smalltreelevel);
		ArrayList<Integer> son2 = getSon(bigtree, node2, bigtreelevel);
		return maxmatch(son1, son2);
	}
	
	public static ArrayList<Integer> getSon(HashMap<Integer, ArrayList<Integer>> tree, int node, int[] level) {
		ArrayList<Integer> son1 = tree.get(node);
		ArrayList<Integer> son = new ArrayList<Integer>();
		son.addAll(son1);
		for (Iterator<Integer> it = son.iterator(); it.hasNext(); ) if (level[it.next()] < level[node]) {
			it.remove();
			break;
		}
		return son;
	}
	
	public static boolean maxmatch(ArrayList<Integer> son1, ArrayList<Integer> son2) {
		if (son1.isEmpty()) return true;
		int result = 0;
		int[] p1 = new int[M];
		int[] p2 = new int[N];
		Arrays.fill(p1, -1);
		Arrays.fill(p2, -1);
		for (Integer i : son1) for (Integer j : son2) if (fittable[i][j] && p1[i] < 0 && p2[j] < 0) {
			p1[i]=j;
			p2[j]=i;
			result++;
		}
		boolean[] visited = new boolean[N];
		for (Integer i : son1) if (p1[i] < 0) {
			Arrays.fill(visited, false);
			if (find(i, son2, p1, p2, visited)) result++;
		}
		return result == son1.size();

	}
	
	public static boolean find(int v, ArrayList<Integer> son2, int[] p1, int[] p2, boolean[] visited) {
		for (Integer i : son2) if (!visited[i] && fittable[v][i]) {
			visited[i]=true;
			if (p2[i] < 0 || find(p2[i], son2, p1, p2, visited)) {
				p1[v]=i;
				p2[i]=v;
				return true;
			}
		}
		return false;
	}

}
