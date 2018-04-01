package Util;

public class Util {
	public static double getAngle(double x, double y, double x2, double y2) {
		return Math.toDegrees(Math.atan2(x2 - x, y - y2)) - 90;
	}
}
