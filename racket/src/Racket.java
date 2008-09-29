import java.util.Scanner;


public class Racket {

	public static double outR = 0;
	public static double inR = 0;
	public static double sr = 0;
	public static double sp = 0;
	public static double pos = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		for (int i = 0; i < N; i++) {
			input(sc);
			work();
			output(i);
		}
	}
	
	public static void input(Scanner sc) {
		double f = sc.nextDouble();
		double R = sc.nextDouble();
		double t = sc.nextDouble();
		double r = sc.nextDouble();
		double g = sc.nextDouble();
		outR = R;
		inR = R-t-f;
		sr = r+f;
		sp = g-2.0*f;
		if (inR < 0) inR = 0;
		if (sp < 0) sp = 0;
	}
	
	public static void work() {
		double area = Math.PI*(outR*outR-inR*inR);
		area += 4.0*stripArea(0, sr);
		for (double d = sr+sp; d < inR; d += (2.0*sr+sp)) area += 4.0*stripArea(d, d+2.0*sr);
		area -= 4.0*overlap(0, sr, 0, sr);
		for (double d = sr+sp; d < inR; d += (2.0*sr+sp)) area -= 8.0*overlap(0, sr, d, d+2.0*sr);
		for (double d = sr+sp; d < inR; d += (2.0*sr+sp)) for (double e = sr+sp; e < inR; e += (2.0*sr+sp)) 
			area -= 4.0*overlap(d, d+2.0*sr, e, e+2.0*sr);
		pos = area/Math.PI/outR/outR;
	}
	
	public static double stripArea(double x1, double x2) {
		if (x1 >= inR) return 0;
		if (x2 > inR) x2 = inR;
		double arc1 = Math.acos(x1/inR)*inR*inR - x1*Math.sqrt(inR*inR-x1*x1);
		double arc2 = Math.acos(x2/inR)*inR*inR - x2*Math.sqrt(inR*inR-x2*x2);
		return arc1-arc2;
	}
	
	public static double overlap(double x1, double x2, double y1, double y2) {
		if (x1 >= inR) return 0;
		if (y1 >= inR) return 0;
		if (x1*x1+y1*y1 > inR*inR) return 0;
		if (x2 > inR) x2 = inR;
		if (y2 > inR) y2 = inR;
		if (x2*x2+y2*y2 <= inR*inR) return (y2-y1)*(x2-x1);
		if (y1*y1+x2*x2 < inR*inR && x1*x1+y2*y2 < inR*inR) {
			double areaCorner = corner(x2, y2);
			return (y2-y1)*(x2-x1)-areaCorner;
		}
		if (x2*x2+y1*y1 >= inR*inR && y2*y2+x1*x1 >= inR*inR) {
			double x3 = Math.sqrt(inR*inR-y1*y1);
			double y3 = Math.sqrt(inR*inR-x1*x1);
			double areaCorner = corner(x3,y3);
			return (y3-y1)*(x3-x1)-areaCorner;
		}
		if (y1*y1+x2*x2 < inR*inR && y2*y2+x1*x1 >= inR*inR) {
			double y3 = Math.sqrt(inR*inR-x1*x1);
			double areaCorner = corner(y3, x2);
			return (y3-y1)*(x2-x1)-areaCorner;
		}
		if (x2*x2+y1*y1 >= inR*inR && x1*x1+y2*y2 < inR*inR) {
			double x3 = Math.sqrt(inR*inR-y1*y1);
			double areaCorner = corner(y2, x3);
			return (x3-x1)*(y2-y1)-areaCorner;
		}
		System.out.println("x1 = " + x1 + " ; x2 = " + x2 + " ; y1 = " + y1 + " ; y2 = " + y2);
		return 0;
	}

	public static double corner(double x, double y) {
		double alpha = Math.PI/2.0-Math.acos(x/inR)-Math.acos(y/inR);
		double areaFan = alpha/2.0*inR*inR;
		return x*y-areaFan-x*Math.sqrt(inR*inR-x*x)/2.0-y*Math.sqrt(inR*inR-y*y)/2.0;
	}
	
	public static void output(int t) {
		System.out.printf("Case #%d: %.6f\n", t+1, pos);
	}
}
