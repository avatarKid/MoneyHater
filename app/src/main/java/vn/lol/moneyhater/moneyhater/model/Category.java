package vn.lol.moneyhater.moneyhater.model;

/**
 * Created by huy on 7/28/2015.
 */
public class Category {
    private int mCategoryID;
    private String mCategoryName;
    private int mImageID;

    public int getCategoryID() {
        return mCategoryID;
    }

    public void setCategoryID(int categoryID) {
        mCategoryID = categoryID;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = categoryName;
    }

    public int getImageID() {
        return mImageID;
    }

    public void setImageID(int imageID) {
        mImageID = imageID;
    }

    @Override
    public String toString() {
        return mCategoryName;
    }
}
