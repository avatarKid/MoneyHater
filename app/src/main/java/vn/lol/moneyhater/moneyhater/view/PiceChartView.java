package vn.lol.moneyhater.moneyhater.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import vn.lol.moneyhater.momeyhater.R;

/**
 * Created by TuanAnh on 7/23/2015.
 */
public class PiceChartView extends View {
    Paint paint;
    Paint bgpaint;
    Paint txtPaint;
    Paint cenPaint;
//    Paint lnPaint;
    RectF rect;
    RectF Cenrect;
    float percentage = 0;
    float current=0,spend=0;
    int cx,cy;
    public PiceChartView(Context context) {
        super(context);
        init();
    }

    public PiceChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PiceChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        paint = new Paint();
        paint.setColor(getContext().getResources().getColor(R.color.blue));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        bgpaint = new Paint();
        bgpaint.setColor(getContext().getResources().getColor(R.color.red_light));
        bgpaint.setAntiAlias(true);
        bgpaint.setStyle(Paint.Style.FILL);

        cenPaint = new Paint();
        cenPaint.setStyle(Paint.Style.FILL);
        cenPaint.setAntiAlias(true);
        cenPaint.setColor(getContext().getResources().getColor(R.color.white));

        txtPaint = new Paint();
        txtPaint.setColor(getContext().getResources().getColor(R.color.black));
        txtPaint.setAntiAlias(true);
        txtPaint.setStyle(Paint.Style.FILL);
        final float textSize = txtPaint.getTextSize();
        txtPaint.setTextSize(textSize * 5);
        rect = new RectF();
        Cenrect = new RectF();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int left = 0;
        final int width = getWidth();
        final int top = 0;
        final int rectInfo = getWidth()/10;
        final int poshowInfo = getWidth()+rectInfo;
        cx = cy = getWidth() / 2;
        canvas.drawCircle(cx, cy, cx, paint);
        rect.set(left, top, width, width);
        if (percentage != 0) {
            canvas.drawArc(rect, -90, (360 * percentage), true, bgpaint);
        }
        txtPaint.setTextSize(rectInfo/2 + rectInfo / 3);
        canvas.drawRect(left, poshowInfo, rectInfo, poshowInfo + rectInfo, bgpaint);
        canvas.drawText("Spend  : " + (int) spend, rectInfo * 1.5f, poshowInfo + rectInfo - rectInfo / 10, txtPaint);
        canvas.drawRect(left, poshowInfo + rectInfo * 1.5f, rectInfo, poshowInfo + rectInfo * 2.5f, paint);
        canvas.drawText("Current: " + (int)current, rectInfo *1.5f, poshowInfo + rectInfo * 2.5f - rectInfo / 10, txtPaint);
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
        invalidate();
    }

    public void setPercentage(float spend, float current) {
        this.spend = spend;
        this.current = current;
        this.percentage = spend/(spend+current);
        invalidate();
    }

}
