package vn.lol.moneyhater.moneyhater.Util;

/**
 * Created by huy on 7/29/2015.
 */
public class CommonFunction {
    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}
