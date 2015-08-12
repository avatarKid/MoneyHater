package vn.lol.moneyhater.moneyhater.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import vn.lol.moneyhater.momeyhater.R;

/**
 * Created by huy on 8/12/2015.
 */
public class CategoryAdapter extends ArrayAdapter {

//    private ArrayList<Category> mLstCategory;
    private String[] arrCategories;
    private Drawable[] arrImage;

    public CategoryAdapter(Context context, int resourceId, String[] arr) {
        super(context, resourceId,arr);
        try {
            arrCategories=arr;
            if (arrCategories.length==18){
                arrImage=new Drawable[18];
                arrImage[0]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.education,null);
                arrImage[1]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.entertainment,null);
                arrImage[2]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.family,null);
                arrImage[3]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.food_drink,null);
                arrImage[4]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.friend_lover,null);
                arrImage[5]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.invest,null);
                arrImage[6]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.loan,null);
                arrImage[7]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.medical,null);
                arrImage[8]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.shopping,null);
                arrImage[9]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.transport,null);
                arrImage[10]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.travel,null);
                arrImage[11]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.award,null);
                arrImage[12]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.debt,null);
                arrImage[13]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.give,null);
                arrImage[14]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.interest_money,null);
                arrImage[15]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.salary,null);
                arrImage[16]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.selling,null);
                arrImage[17]=ResourcesCompat.getDrawable(context.getResources(),R.drawable.other,null);
                for (int i=0;i<18;i++) {
                    arrImage[i] = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(((BitmapDrawable) arrImage[i]).getBitmap(), 80, 80, true));
                }
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Loi nay","Loi nay");
            e.printStackTrace();
        }

//        mLstCategory=new ArrayList();
//        mLstCategory.add(new Category());
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            TextView textView = (TextView) super.getView(position, convertView, parent);
            textView.setTextColor(Color.BLACK);
            textView.setText(arrCategories[position]);
            textView.setPadding(20,10,10,10);
            textView.setCompoundDrawablesWithIntrinsicBounds(arrImage[position], null, null, null);
            textView.setCompoundDrawablePadding(50);
            return textView;
        } catch (Exception e) {
            Log.e("Loi nay","Loi nay");
            e.printStackTrace();
        }
        return null;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}