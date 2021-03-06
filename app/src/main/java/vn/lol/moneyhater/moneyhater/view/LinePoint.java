package vn.lol.moneyhater.moneyhater.view;

/**
 * Created by TuanAnh on 8/2/2015.
 */
import android.graphics.Path;
import android.graphics.Region;

import vn.lol.moneyhater.moneyhater.Util.Utils;

public class LinePoint {

    private final Path mPath = new Path();
    private final Region mRegion = new Region();
    private float mX;
    private float mY;
    private int mColor = 0xFF000000;
    private int mSelectedColor = -1;
    private String mName = null;

    public LinePoint() {
        this(0, 0);
    }

    public LinePoint(double x, double y) {
        this((float) x, (float) y);
    }

    public LinePoint(float x, float y) {
        mX = x;
        mY = y;
    }

    public float getX() {
        return mX;
    }

    public void setX(float x) {
        mX = x;
    }

    public float getY() {
        return mY;
    }

    public void setY(float y) {
        mY = y;
    }

    public void setX(double x) {
        mX = (float) x;
    }

    public void setY(double y) {
        mY = (float) y;
    }

    public Region getRegion() {
        return mRegion;
    }

    public Path getPath() {
        return mPath;
    }

    @Override
    public String toString() {
        return "x= " + mX + ", y= " + mY;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getSelectedColor() {
        if (-1 == mSelectedColor) {
            mSelectedColor = Utils.darkenColor(mColor);
            mSelectedColor &= 0x80FFFFFF;
        }
        return mSelectedColor;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }
}
