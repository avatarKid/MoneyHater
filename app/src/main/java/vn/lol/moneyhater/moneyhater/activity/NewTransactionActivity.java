package vn.lol.moneyhater.moneyhater.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
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
import vn.lol.moneyhater.moneyhater.fragment.TransactionFragment;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.Budget;
import vn.lol.moneyhater.moneyhater.model.Category;
import vn.lol.moneyhater.moneyhater.model.Transaction;


public class NewTransactionActivity extends ActionBarActivity {

    EditText etTransactionName;
    EditText etTransactionMoney;
    EditText etAddTransactionDate;
    Switch swTransactionType;
    Spinner spTransactionAccount;
    Spinner spTransactionBudget;
    DatePicker dpTransactionDate;
    Button btAddNewTransaction;
    Transaction transaction = new Transaction();
    ArrayList<Budget> listBudget;
    ArrayList<Category> listCategory;
    ArrayList<Account> listAccount;
    DatabaseHelper mDbHelper;
    int mDay, mMonth, mYear;

    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);
        init();
        btAddNewTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaction();
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.putExtra("transaction", transaction);
//                startActivity(intent);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(ConstantValue.NEW_TRANSACTION, transaction);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        etAddTransactionDate.setOnTouchListener(new View.OnTouchListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_transaction, menu);
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

    public void init() {
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        etAddTransactionDate = (EditText) findViewById(R.id.etAddTransactionDate);
        btAddNewTransaction = (Button) findViewById(R.id.btAddNewTran);
        etTransactionName = (EditText) findViewById(R.id.etAddTransName);
        etTransactionMoney = (EditText) findViewById(R.id.etTransactionMoney);
        spTransactionBudget = (Spinner) findViewById(R.id.spAddTransBudgetName);
        spTransactionAccount = (Spinner) findViewById(R.id.spAddTransAccountName);
        swTransactionType = (Switch) findViewById(R.id.swTransactionType);


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


        //Load data from DB
        mDbHelper = new DatabaseHelper(getApplicationContext());
        listAccount = mDbHelper.getAllAccounts();
        listBudget = mDbHelper.getAllBudgets();
        listCategory = mDbHelper.getAllCategory();

        // add item to Account spinner
        ArrayAdapter<Account> adapterAccount = new ArrayAdapter<Account>(this, android.R.layout.simple_spinner_dropdown_item, listAccount);
        spTransactionAccount.setAdapter(adapterAccount);

        // add item to Budget spinner
        ArrayAdapter<Budget> adapterBudget = new ArrayAdapter<Budget>(this, android.R.layout.simple_spinner_dropdown_item, listBudget);
        spTransactionBudget.setAdapter(adapterBudget);

        // fill today to edit text
        etAddTransactionDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);

        //TODO add Category
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
            etAddTransactionDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
        }
    };

    public void addTransaction() {

        transaction.setTransactionName(etTransactionName.getText().toString());

        try {
            transaction.setCash(Double.parseDouble(etTransactionMoney.getText().toString().replaceAll("[,]", "")));
        } catch (NumberFormatException e) {
            transaction.setCash(0);
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        transaction.setDate(calendar);
        transaction.setType(swTransactionType.isChecked() ? ConstantValue.TRANSACTION_TYPE_INCOME : ConstantValue.TRANSACTION_TYPE_EXPENSE);
        Account account = (Account) spTransactionAccount.getSelectedItem();
        if (account != null) {
            transaction.setAccountID(account.getAccountID());
        }
        Budget budget = (Budget) spTransactionBudget.getSelectedItem();
        if (budget != null) {
            transaction.setBudgetID(budget.getBudgetID());
        }
    }
}
