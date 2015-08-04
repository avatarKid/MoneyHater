package vn.lol.moneyhater.moneyhater.view;

/**
 * Created by TuanAnh on 8/2/2015.
 */
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Region;

import vn.lol.moneyhater.moneyhater.Util.Utils;

public class Bar {

    private final Path mPath = new Path();
    private final Region mRegion = new Region();
    private int mColor = 0xFF33B5E5;
    private int mLabelColor = -1;
    private int mSelectedColor = -1;
    private int mValueColor = Color.WHITE;
    private int mColorAlpha = 255;//no transparency by default. Used in animations to transition to a final alpha.
    private String mName = null;
    private float mValue;
    private float mOldValue;
    private float mGoalValue;
    private String mValueString = null;
    private String mValuePrefix = null;
    private String mValueSuffix = null;

    public int mAnimateSpecial = HoloGraphAnimate.ANIMATE_NORMAL;//add getter setter

    public int getColor() {
        return mColor;
    }

    public int getColorAlpha(){return mColorAlpha;}

    public void setColor(int color) {
        mColor = color;
        mColorAlpha = Color.alpha(color);
    }

    public int getLabelColor() {
        return mLabelColor == -1 ? mColor : mLabelColor;
    }

    public void setLabelColor(int labelColor) {
        mLabelColor = labelColor;
    }

    public int getSelectedColor() {
        if (-1 == mSelectedColor) mSelectedColor = Utils.darkenColor(mColor);
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    public int getValueColor() {
        return mValueColor;
    }

    public void setValueColor(int valueColor) {
        mValueColor = valueColor;
    }

    public String getName() {
        return (null == mName) ? "" : mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        mValue = value;
    }

    public float getOldValue() {
        return mOldValue;
    }

    public void setOldValue(float oldValue) { mOldValue = oldValue; }

    public float getGoalValue() {
        return mGoalValue;
    }

    public void setGoalValue(float goalValue) { mGoalValue = goalValue; }

    public String getValueString() {
//        if (mValueString != null) {
//            return mValueString;
//        } else {
//            return String.valueOf(mValue);
//        }
        return formatNum();
    }

    public String formatNum(){
        int num = (int)mValue;
        String numformat = "";
        if (num >= 1000 && num < 1000000) {
            numformat = num/1000 + "k";
        } else if (num >= 1000000 && num < 1000000000) {
            numformat = num/1000000 + "m";
        } else if (num >= 1000000000) {
            numformat = num/1000000000 + "b";
        } else {
            numformat = num+"";
        }
        return numformat;
    };


    public void setValueString(final String valueString) {
        mValueString = valueString;
    }
    public String getValuePrefix() {return mValuePrefix;}

    public void setValuePrefix(String valuePrefix) { mValuePrefix = valuePrefix; }

    public String getValueSuffix() {return mValueSuffix;}

    public void setValueSuffix(String valueSuffix) { mValueSuffix = valueSuffix; }

    public void makeValueString(int decimalPrecision){
        String base = String.format("%." + String.valueOf(decimalPrecision)+"f",mValue);
        if (getValuePrefix() != null) base = getValuePrefix() + base;
        if (getValueSuffix() != null) base = base + getValueSuffix();
        setValueString(base);
    }

    public Path getPath() {
        return mPath;
    }

    public Region getRegion() {
        return mRegion;
    }
}
