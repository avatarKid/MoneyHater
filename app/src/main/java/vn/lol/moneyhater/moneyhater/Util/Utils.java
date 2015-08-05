package vn.lol.moneyhater.moneyhater.Util;

/**
 * Created by TuanAnh on 8/2/2015.
 */
import android.graphics.Color;
public class Utils {
    public static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }
}
