package vn.lol.moneyhater.moneyhater.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.adapter.CategoryAdapter;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.Budget;
import vn.lol.moneyhater.moneyhater.model.Category;
import vn.lol.moneyhater.moneyhater.model.Transaction;

public class EditTransaction extends AppCompatActivity {

    Transaction transaction;
    int transactionID;
    DatabaseHelper mDbHelper;

    ArrayList<Budget> listBudget;
    ArrayList<Category> listCategory;
    ArrayList<Account> listAccount;

    EditText etTransactionName;
    EditText etTransactionMoney;
    Switch swTransactionType;
    Spinner spTransactionAccount;
    Spinner spTransactionBudget;
    Spinner spTransactionCategory;
    EditText edTransactionDate;

    double oldCash = 0;
    int oldAccountID = 0;
    int oldBudgetID = 0;
    int oldType = 0;
    double newCash = 0;

    int mDay, mMonth, mYear;
    static final int DIALOG_ID = 0;

    Button btSave, btDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        init();
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(ConstantValue.TRANSACTION_ID, transactionID);
                setResult(ConstantValue.RESULT_CODE_DELETE_TRANSACTION, intent);
                finish();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTransaction();
                Intent intent = new Intent();
                intent.putExtra(ConstantValue.SAVE_TRANSACTION, transaction);
                setResult(ConstantValue.RESULT_CODE_SAVE_TRANSACTION, intent);
                finish();
            }
        });

        edTransactionDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {
                    showDialogPickDay();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Err Pick Day", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    public void updateTransaction() {
        //Name
        transaction.setTransactionName(etTransactionName.getText().toString());

        //Date
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        transaction.setDate(calendar);

        //Money
        try {
            transaction.setCash(Double.parseDouble(etTransactionMoney.getText().toString().replaceAll("[,]", "")));
        } catch (NumberFormatException e) {
            transaction.setCash(0);
            e.printStackTrace();
        }

        //Type
        transaction.setType(swTransactionType.isChecked() ? ConstantValue.TRANSACTION_TYPE_INCOME : ConstantValue.TRANSACTION_TYPE_EXPENSE);
        newCash = transaction.getCash();

        //update Account
        Account newAccount = (Account) spTransactionAccount.getSelectedItem();
        double newAccountMoney = newAccount.getCash();

        Account oldAccount = mDbHelper.getAccount(oldAccountID);
        double oldAccountMoney = oldAccount.getCash();

        if (newAccount != null) {
            transaction.setAccountID(newAccount.getAccountID());
            if(newAccount.getAccountID() == oldAccountID){ // if not change Account
                if(transaction.getType() == oldType){ // if not change Type of Transaction
                    if(oldType == ConstantValue.TRANSACTION_TYPE_EXPENSE){
                        newAccountMoney = newAccountMoney + oldCash - newCash;
                    }else {
                        newAccountMoney = newAccountMoney - oldCash + newCash;
                    }
                } else { // change Type of Transaction
                    if(oldType == ConstantValue.TRANSACTION_TYPE_EXPENSE){
                        newAccountMoney = newAccountMoney + oldCash + newCash;
                    }else {
                        newAccountMoney = newAccountMoney - oldCash - newCash;
                    }
                }
                newAccount.setCash(newAccountMoney);
                mDbHelper.updateAccount(newAccount);
            } else { // if Change account
                if(transaction.getType() == oldType){ // if not change Type of Transaction
                    if(oldType == ConstantValue.TRANSACTION_TYPE_EXPENSE){
                        newAccountMoney -= newCash;
                        oldAccountMoney += oldCash;
                    }else {
                        newAccountMoney += newCash;
                        oldAccountMoney -= oldCash;
                    }
                } else { // change Type of Transaction
                    if(oldType == ConstantValue.TRANSACTION_TYPE_EXPENSE){
                        newAccountMoney += newCash;
                        oldAccountMoney += oldCash;
                    }else {
                        newAccountMoney -= newCash;
                        oldAccountMoney -= oldCash;
                    }
                }
                newAccount.setCash(newAccountMoney);
                oldAccount.setCash(oldAccountMoney);
                mDbHelper.updateAccount(newAccount);
                mDbHelper.updateAccount(oldAccount);
            }

        }

        //update Budget
        Budget newBudget = (Budget) spTransactionBudget.getSelectedItem();
        double newBudgetMoney = newBudget.getCash();

        Budget oldBudget = mDbHelper.getBudget(oldBudgetID);
        double oldBudgetMoney = oldBudget.getCash();

        if (newBudget != null) {
            transaction.setBudgetID(newBudget.getBudgetID());
            if(newBudget.getBudgetID() == oldBudgetID){ // if not change Budget
                if(transaction.getType() == oldType){ // if not change Type of Transaction
                    if(oldType == ConstantValue.TRANSACTION_TYPE_EXPENSE){
                        newBudgetMoney = newBudgetMoney + oldCash - newCash;
                    }else {
                        newBudgetMoney = newBudgetMoney - oldCash + newCash;
                    }
                } else { // change Type of Transaction
                    if(oldType == ConstantValue.TRANSACTION_TYPE_EXPENSE){
                        newBudgetMoney = newBudgetMoney + oldCash + newCash;
                    }else {
                        newBudgetMoney = newBudgetMoney - oldCash - newCash;
                    }
                }
                newBudget.setCash(newBudgetMoney);
                mDbHelper.updateBudget(newBudget);
            } else { // if Change account
                if(transaction.getType() == oldType){ // if not change Type of Transaction
                    if(oldType == ConstantValue.TRANSACTION_TYPE_EXPENSE){
                        newBudgetMoney -= newCash;
                        oldBudgetMoney += oldCash;
                    }else {
                        newBudgetMoney += newCash;
                        oldBudgetMoney -= oldCash;
                    }
                } else { // change Type of Transaction
                    if(oldType == ConstantValue.TRANSACTION_TYPE_EXPENSE){
                        newBudgetMoney += newCash;
                        oldBudgetMoney += oldCash;
                    }else {
                        newBudgetMoney -= newCash;
                        oldBudgetMoney -= oldCash;
                    }
                }
                newBudget.setCash(newBudgetMoney);
                oldBudget.setCash(oldBudgetMoney);
                mDbHelper.updateBudget(newBudget);
                mDbHelper.updateBudget(oldBudget);
            }

        }

        //TODO category
        transaction.setCategoryID(spTransactionCategory.getSelectedItemPosition());

    }

    public void init() {
        // get value of selected transaction
        transaction = (Transaction) getIntent().getSerializableExtra(ConstantValue.EDIT_TRANSACTION);
        transactionID = transaction.getTransactionID();
        oldCash = transaction.getCash();
        oldAccountID = transaction.getAccountID();
        oldBudgetID = transaction.getBudgetID();
        oldType = transaction.getType();

        // get date of current transaction
        mDay = transaction.getCalendar().get(Calendar.DAY_OF_MONTH);
        mMonth = transaction.getCalendar().get(Calendar.MONTH);
        mYear = transaction.getCalendar().get(Calendar.YEAR);

        //Init view
        btSave = (Button) findViewById(R.id.btSaveTransaction);
        btDelete = (Button) findViewById(R.id.btDeleteTransaction);
        etTransactionName = (EditText) findViewById(R.id.etEditTransName);
        etTransactionMoney = (EditText) findViewById(R.id.etEditTransactionMoney);
        edTransactionDate = (EditText) findViewById(R.id.etEditTransactionDate);
        spTransactionBudget = (Spinner) findViewById(R.id.spEditTransBudgetName);
        spTransactionAccount = (Spinner) findViewById(R.id.spEditTransAccountName);
        spTransactionCategory = (Spinner) findViewById(R.id.spEditTransCategoryName);
        swTransactionType = (Switch) findViewById(R.id.swEditTransactionType);
        //TODO init category
        try {
            String[] arr = getResources().getStringArray(R.array.category);
            CategoryAdapter ca = new CategoryAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,arr);
            spTransactionCategory.setAdapter(ca);
            spTransactionCategory.setSelection(transaction.getCategoryID());
        } catch (Resources.NotFoundException e) {
            Log.e("Loi nay","Loi nay");
            e.printStackTrace();
        }

        etTransactionMoney.addTextChangedListener(new TextWatcher() {

            String current = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                if (!charSequence.toString().equals("")) {
                    if (!charSequence.toString().equals(current)) {
                        String cash = charSequence.toString().replaceAll("[,]", "");
                        double parsed = Double.parseDouble(cash);
                        String formated = NumberFormat.getInstance().format((parsed));
                        current = formated;
                        etTransactionMoney.setText(formated);
                        etTransactionMoney.setSelection(etTransactionMoney.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // load DB
        mDbHelper = new DatabaseHelper(getApplicationContext());
        listAccount = mDbHelper.getAllAccounts();
        listBudget = mDbHelper.getAllBudgets();
        listCategory = mDbHelper.getAllCategory();
        Account currentAccount = mDbHelper.getAccount(transaction.getAccountID());
        Budget currentBudget = mDbHelper.getBudget(transaction.getBudgetID());

        // add item to Account spinner
        ArrayAdapter<Account> adapterAccount = new ArrayAdapter<Account>(this, android.R.layout.simple_spinner_dropdown_item, listAccount);
        spTransactionAccount.setAdapter(adapterAccount);

        // add item to Budget spinner
        ArrayAdapter<Budget> adapterBudget = new ArrayAdapter<Budget>(this, android.R.layout.simple_spinner_dropdown_item, listBudget);
        spTransactionBudget.setAdapter(adapterBudget);

        //TODO add Category

        etTransactionName.setText(transaction.getTransactionName());
        etTransactionMoney.setText(NumberFormat.getInstance().format(transaction.getCash()));
        swTransactionType.setChecked(transaction.getType() == 0 ? false : true);

        spTransactionAccount.setSelection(adapterAccount.getPosition(currentAccount));
        spTransactionBudget.setSelection(adapterBudget.getPosition(currentBudget));
        edTransactionDate.setText(transaction.getCalendar().get(Calendar.DAY_OF_MONTH) + "/"
                + (transaction.getCalendar().get(Calendar.MONTH) + 1) + "/"
                + transaction.getCalendar().get(Calendar.YEAR));
    }


    public void showDialogPickDay() {
        showDialog(DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new DatePickerDialog(this, dpickerListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            edTransactionDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_transaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
