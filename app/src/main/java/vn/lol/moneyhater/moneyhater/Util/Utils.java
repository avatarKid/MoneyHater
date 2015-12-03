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


    public static String convertKMB(float num){
        String numformat = "";
        float result = 0;
        if (num >= 1000 && num < 1000000) {
            result = num/1000;
            if(result != (int)result)
                numformat = String.format("%.1f", result) + "k";
            else
                numformat = ((int)result) + "k";
        } else if (num >= 1000000 && num < 1000000000) {
            result =  num/1000000;
            if(result != (int)result)
                numformat = String.format("%.1f", result) + "m";
            else
                numformat = ((int)result) + "m";
        } else if (num >= 1000000000) {
            result =  num/1000000000;
            if(result != (int)result)
                numformat = String.format("%.1f", result) + "b";
            else
                numformat = ((int)result) + "b";
        } else {
            numformat = (int)num +"";
        }
        return  numformat;
    }

}
