package util;

public class Numeric {
    public static float resolveFloat(int bits) {
        int s = ((bits >> 31) == 0) ? 1 : -1;
        int e = ((bits >> 23) & 0xff);
        int m = (e == 0) ?
                (bits & 0x7fffff) << 1 :
                (bits & 0x7fffff) | 0x800000;
        return (float) (s * m * Math.pow(2, e - 150));
    }

    public static double resolveDouble(int high, int low) {
        long bits = ((long) high << 32) + low;
        int s = ((bits >> 63) == 0) ? 1 : -1;
        int e = (int) ((bits >> 52) & 0x7ffL);
        long m = (e == 0) ?
                (bits & 0xfffffffffffffL) << 1 :
                (bits & 0xfffffffffffffL) | 0x10000000000000L;
        return s * m * Math.pow(2.0, e - 1075);
    }
}
