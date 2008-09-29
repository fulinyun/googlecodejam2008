import java.util.Scanner;


public class Bet {

	public static double million = 1000000.0;
	public static double[][] table;
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int N = scanner.nextInt();
		for (int i = 0; i < N; i++) {
			int M = scanner.nextInt();
			double P = scanner.nextDouble();
			int X = scanner.nextInt();
			if (M == 1) {
				if (X >= 1000000/2) System.out.printf("Case #%d: %.6f\n", i+1, P);
				else System.out.printf("Case #%d: %.6f\n", i+1, 0.0);
				continue;
			}
			if (X >= 1000000) {
				System.out.printf("Case #%d: %.6f\n", i+1, 1.0);
				continue;
			}
			if (X < million/(1<<M)) {
				System.out.printf("Case #%d: %.6f\n", i+1, 0.0);
				continue;
			}
			preprocess(M, P);
//			for (int j = 0; j < table.length; j++) {
//				for (int k = 0; k < table[j].length; k++) System.out.printf("%9.6f", table[j][k]);
//				System.out.println();
//			}
			System.out.printf("Case #%d: %.6f\n", i+1, solve(M, P, X));
		}
	}
	
	public static void preprocess(int M, double P) {
		table = new double[M+1][];
		for (int i = 0, j = 1; i <= M; i++, j *= 2) table[i] = new double[j+1];
		table[0][0] = 0.0;
		table[0][1] = 1.0;
		int unit = (1<<20);
		for (int i = 1; i <= M; i++) {
//			System.out.println(i + " rounds left");
			unit /= 2;
			table[i][0] = 0.0;
			table[i][table[i].length-1] = 1.0;
			for (int j = 1; j < table[i].length-1; j++) {
				//System.out.println(j + "th slot");
				int origin = j*unit;
				//System.out.println("origin = " + origin);
				double maxpro = 0;
				for (int k = 0; k < table[i-1].length; k++) {
					double pro = 0;
					int target = k*unit*2;
					//System.out.println("target = " + target);
					//System.out.println("target pro = " + table[i-1][k]);
					int bet;
					if (target == origin) {
						pro = table[i-1][k];
						if (pro > maxpro) maxpro = pro;
					} else if (target > origin) {
						bet = target-origin;
						if (bet <= origin) {
							pro += table[i-1][k]*P;
							int iflose = origin-bet;
							int index = iflose/2/unit;
							pro += table[i-1][index]*(1-P);
							if (pro > maxpro) maxpro = pro;
						}
					} else {
						bet = origin-target;
						pro += table[i-1][k]*(1-P);
						//System.out.println("pro += " + table[i-1][k]*(1-P));
						int ifwin = origin+bet;
						if (ifwin >= (1<<20)) {
							//System.out.println("pro += " + P);
							pro += P;
						} else {
							int index = ifwin/2/unit;
							//System.out.println("pro += " + table[i-1][index]*P);
							pro += table[i-1][index]*P;
						}
						if (pro > maxpro) maxpro = pro;
					}
				}
				table[i][j] = maxpro;
//				System.out.println("maxpro = " + maxpro);
			}
			
		}
	}
	
	public static double solve(int M, double P, double X) {
		double unit = million;
		int level = 1;
		for (int i = 1; i <= M; i++) {
			unit /= 2;
			level *= 2;
		}
		int i;
		//System.out.println("level = " + level + " ; unit = " + unit);
		for (i = level-1; i >= 0; i--) if (X >= i*unit-1e-8) break;
		if (i == -1) return 0.0;
		return table[M][i];
	}
}
