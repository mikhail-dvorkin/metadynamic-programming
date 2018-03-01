package tools;

public class Bits {
    public static boolean isSet(long v, int i) {
        return ((v >> i) & 1) == 1;
    }
    public static boolean isClear(long v, int i) {
        return ((v >> i) & 1) == 0;
    }
    public static long set(long v, int i) {
        return v | (1 << i);
    }
    public static long clear(long v, int i) {
        return v & ~(1 << i);
    }
    public static int bitCount(long v) {
    	return Long.bitCount(v);
    }
    public static int bitSum(long v, int bits) {
		int s = 0;
		for (int i = 0; i < bits; i++) {
			if (Bits.isSet(v, i)) {
				s += i;
			}
		}
		return s;
    }
    
    public static boolean isSet(int v, int i) {
        return ((v >> i) & 1) == 1;
    }
    public static boolean isClear(int v, int i) {
        return ((v >> i) & 1) == 0;
    }
    public static boolean isClear(int v, int lo, int hi) {
    	return ((v >> lo) & ((1 << (hi - lo)) - 1)) == 0;
    }
    public static int set(int v, int i) {
        return v | (1 << i);
    }
    public static int set(int v, int lo, int hi) {
    	return v | (((1 << (hi - lo)) - 1) << lo);
    }
    public static int clear(int v, int i) {
        return v & ~(1 << i);
    }
    public static int bitCount(int v) {
    	return Integer.bitCount(v);
    }
    public static int bitSum(int v, int bits) {
		int s = 0;
		for (int i = 0; i < bits; i++) {
			if (Bits.isSet(v, i)) {
				s += i;
			}
		}
		return s;
    }
}
