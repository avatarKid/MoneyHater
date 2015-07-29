package vn.lol.moneyhater.moneyhater.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import vn.lol.moneyhater.moneyhater.Util.CommonFunction;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.Budget;
import vn.lol.moneyhater.moneyhater.model.Category;
import vn.lol.moneyhater.moneyhater.model.Transaction;

/**
 * Created by huy on 7/28/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = this.getClass().getName();

    // database version
    private static final int DATABASE_VERSION=1;

    // database name
    private static final String DATABASE_NAME = "moneyhater";

    // table name

    private final String TABLE_ACCOUNT = "account";
    private final String TABLE_BUDGET = "budget";
    private final String TABLE_CATEGORY = "category";
    private final String TABLE_TRANSACTION = "transaction";

    // common key
    private final String FIELD_ID = "id";
    private final String FIELD_NAME = "name";
    private final String FIELD_CASH = "cash";
    private final String FIELD_IMAGE_ID = "image_id";
    // account table
    private final String FIELD_ACCOUNT_TYPE_ID ="type_id";

    // budget table
    // category table
    // transaction table
    private final String FIELD_TRANSACTION_TYPE = "type";
    private final String FIELD_TRANSACTION_ACCOUNT_ID = "account_id";
    private final String FIELD_TRANSACTION_BUDGET_ID = "budget_id";
    private final String FIELD_TRANSACTION_CATEGORY_ID = "category_id";
    private final String FIELD_DATE = "date";

    // create statement
    private final String CREATE_TABLE_ACCOUNT = "CREATE TABLE \"account\" (\n" +
            "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`name`\tTEXT NOT NULL,\n" +
            "\t`cash`\tINTEGER NOT NULL DEFAULT 0,\n" +
            "\t`type_id`\tNUMERIC NOT NULL DEFAULT 0\n" +
            ")";

    private final String CREATE_TABLE_BUDGET ="CREATE TABLE `budget` (\n" +
            "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`name`\tTEXT NOT NULL,\n" +
            "\t`image_id`\tINTEGER NOT NULL DEFAULT 0,\n" +
            "\t`cash`\tNUMERIC\n" +
            ")";

    private final String CREATE_TABLE_CATEGORY ="CREATE TABLE `category` (\n" +
            "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`name`\tTEXT NOT NULL,\n" +
            "\t`image_id`\tNUMERIC NOT NULL DEFAULT 0\n" +
            ")";

    private final String CREATE_TABLE_TRANSACTION ="CREATE TABLE \"transaction\" (\n" +
            "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`type`\tINTEGER NOT NULL DEFAULT 0,\n" +
            "\t`name`\tTEXT,\n" +
            "\t`cash`\tNUMERIC NOT NULL,\n" +
            "\t`category_id`\tINTEGER,\n" +
            "\t`account_id`\tINTEGER,\n" +
            "\t`budget_id`\tINTEGER,\n" +
            "\t`date_time`\tBLOB\n" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_BUDGET);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_TRANSACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);

        // create new tables
        onCreate(db);
    }

    // TABLE Account insert, add, , delete, find, find all
    public Account SelectAccount(String accountID) {
        Account account=null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE "
                    + FIELD_ID + " = " + accountID;

            Log.e(TAG, selectQuery);

            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null) {
                c.moveToFirst();
                account=new Account();
                account.setAccountID(c.getInt(c.getColumnIndex(FIELD_ID)));
                account.setCash((c.getDouble(c.getColumnIndex(FIELD_CASH))));
                account.setAccountName(c.getString(c.getColumnIndex(FIELD_NAME)));
                account.setAccountTypeID(c.getInt(c.getColumnIndex(FIELD_ACCOUNT_TYPE_ID)));
            }
        } catch (Exception e) {
            Log.e(TAG,"SelectAccount");
            e.printStackTrace();
        }
        return account;
    }

    public ArrayList<Account> getAllAccounts() {
        ArrayList<Account> lstAccounts = new ArrayList<Account>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNT;

        Log.e(TAG, selectQuery);

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Account account=new Account();
                    account.setAccountID(c.getInt(c.getColumnIndex(FIELD_ID)));
                    account.setCash((c.getDouble(c.getColumnIndex(FIELD_CASH))));
                    account.setAccountName(c.getString(c.getColumnIndex(FIELD_NAME)));
                    account.setAccountTypeID(c.getInt(c.getColumnIndex(FIELD_ACCOUNT_TYPE_ID)));

                    // adding to todo list
                    lstAccounts.add(account);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG,"getAllAccounts");
            e.printStackTrace();
        }

        return lstAccounts;
    }

    public boolean insertAccount(Account account) {
        long accountID = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(FIELD_NAME, account.getAccountName());
            values.put(FIELD_CASH, account.getCash());
            values.put(FIELD_ACCOUNT_TYPE_ID, account.getAccountTypeID());

            // insert row
            accountID = db.insert(TABLE_ACCOUNT, null, values);
            account.setAccountID(CommonFunction.safeLongToInt(accountID));
        } catch (Exception e) {
            Log.e(TAG,"insertAccount");
            e.printStackTrace();
        }
        return accountID>0;
    }

    public boolean deleteAccount(int accountID){
        int deleteNumber = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String whereClause = FIELD_ID + "=?";
            String[] whereArgs = new String[] { String.valueOf(accountID) };
            deleteNumber=db.delete(TABLE_ACCOUNT,whereClause,whereArgs);
        } catch (Exception e) {
            Log.e(TAG,"deleteAccount");
            e.printStackTrace();
        }
        return deleteNumber>0;
    }

    // TABLE Budget insert, add, , delete, find, find all
    public Budget SelectBudget(int budgetID){
        Budget budget=null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT * FROM " + TABLE_BUDGET + " WHERE "
                    + FIELD_ID + " = " + budgetID;

            Log.e(TAG, selectQuery);

            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null) {
                c.moveToFirst();
                budget=new Budget();
                budget.setBudgetID(c.getInt(c.getColumnIndex(FIELD_ID)));
                budget.setCash((c.getDouble(c.getColumnIndex(FIELD_CASH))));
                budget.setBudgetName(c.getString(c.getColumnIndex(FIELD_NAME)));
                budget.setImageID(c.getInt(c.getColumnIndex(FIELD_IMAGE_ID)));
            }
        } catch (Exception e) {
            Log.e(TAG,"SelectBudget");
            e.printStackTrace();
        }
        return budget;
    }

    public ArrayList<Budget> getAllBudgets() {
        ArrayList<Budget> lstAccounts = new ArrayList<Budget>();
        String selectQuery = "SELECT  * FROM " + TABLE_BUDGET;

        Log.e(TAG, selectQuery);

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Budget budget=new Budget();
                    budget.setBudgetID(c.getInt(c.getColumnIndex(FIELD_ID)));
                    budget.setCash((c.getDouble(c.getColumnIndex(FIELD_CASH))));
                    budget.setBudgetName(c.getString(c.getColumnIndex(FIELD_NAME)));
                    budget.setImageID(c.getInt(c.getColumnIndex(FIELD_IMAGE_ID)));

                    // adding to todo list
                    lstAccounts.add(budget);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG,"getAllBudgets");
            e.printStackTrace();
        }

        return lstAccounts;
    }

    public boolean insertBudget(Budget budget) {
        long budgetID = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(FIELD_NAME, budget.getBudgetName());
            values.put(FIELD_CASH, budget.getCash());
            values.put(FIELD_IMAGE_ID, budget.getImageID());

            // insert row
            budgetID = db.insert(TABLE_BUDGET, null, values);
            budget.setBudgetID(CommonFunction.safeLongToInt(budgetID));
        } catch (Exception e) {
            Log.e(TAG,"insertBudget");
            e.printStackTrace();
        }
        return budgetID>0;
    }

    public boolean deleteBudget(int budgetID){
        int deleteNumber = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String whereClause = FIELD_ID + "=?";
            String[] whereArgs = new String[] { String.valueOf(budgetID) };
            deleteNumber=db.delete(TABLE_BUDGET,whereClause,whereArgs);
        } catch (Exception e) {
            Log.e(TAG,"deleteBudget");
            e.printStackTrace();
        }
        return deleteNumber>0;
    }

    // TABLE Category insert, add, , delete, find, find all
    public Category SelectCategory(int categoryID){
        Category category=null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT * FROM " + TABLE_CATEGORY + " WHERE "
                    + FIELD_ID + " = " + categoryID;

            Log.e(TAG, selectQuery);

            Cursor c = db.rawQuery(selectQuery, null);

            if (c != null) {
                c.moveToFirst();
                category=new Category();
                category.setCategoryID(c.getInt(c.getColumnIndex(FIELD_ID)));
                category.setCategoryName(c.getString(c.getColumnIndex(FIELD_NAME)));
                category.setImageID(c.getInt(c.getColumnIndex(FIELD_IMAGE_ID)));
            }
        } catch (Exception e) {
            Log.e(TAG,"SelectBudget");
            e.printStackTrace();
        }
        return category;
    }

    public ArrayList<Category> getAllCategory() {
        ArrayList<Category> lastCategory= new ArrayList<Category>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        Log.e(TAG, selectQuery);

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Category category=new Category();
                    category.setCategoryID(c.getInt(c.getColumnIndex(FIELD_ID)));
                    category.setCategoryName(c.getString(c.getColumnIndex(FIELD_NAME)));
                    category.setImageID(c.getInt(c.getColumnIndex(FIELD_IMAGE_ID)));

                    // adding to todo list
                    lastCategory.add(category);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG,"getAllCategory");
            e.printStackTrace();
        }

        return lastCategory;
    }

    public boolean insertCategory(Category category) {
        long categoryID = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(FIELD_NAME, category.getCategoryName());
            values.put(FIELD_IMAGE_ID, category.getImageID());

            // insert row
            categoryID = db.insert(TABLE_CATEGORY, null, values);
            category.setCategoryID(CommonFunction.safeLongToInt(categoryID));
        } catch (Exception e) {
            Log.e(TAG,"insertCategory");
            e.printStackTrace();
        }
        return categoryID>0;
    }

    public boolean deleteCategory(int budgetID){
        int deleteNumber = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String whereClause = FIELD_ID + "=?";
            String[] whereArgs = new String[] { String.valueOf(budgetID) };
            deleteNumber=db.delete(TABLE_CATEGORY,whereClause,whereArgs);
        } catch (Exception e) {
            Log.e(TAG,"deleteCategory");
            e.printStackTrace();
        }
        return deleteNumber>0;
    }

    // TABLE TRANSACTION insert, add, , delete, find, find all
}
