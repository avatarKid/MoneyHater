package vn.lol.moneyhater.moneyhater.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

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
        btAddNewTransaction = (Button) findViewById(R.id.btAddNewTran);
        etTransactionName = (EditText) findViewById(R.id.etAddTransName);
        etTransactionMoney = (EditText) findViewById(R.id.etTransactionMoney);
        spTransactionBudget = (Spinner) findViewById(R.id.spAddTransBudgetName);
        spTransactionAccount = (Spinner) findViewById(R.id.spAddTransAccountName);
//        dpTransactionDate = (DatePicker) findViewById(R.id.dpTransactionDate);
        swTransactionType = (Switch) findViewById(R.id.swTransactionType);
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

        //TODO add Category
    }

    public void addTransaction() {

        transaction.setTransactionName(etTransactionName.getText().toString());

        try {
            transaction.setCash(Double.parseDouble(etTransactionMoney.getText().toString()));
        } catch (NumberFormatException e) {
            transaction.setCash(0);
            e.printStackTrace();
        }

        // TODO get from datePicker dialog
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(dpTransactionDate.getYear(), dpTransactionDate.getMonth(), dpTransactionDate.getDayOfMonth());
//        transaction.setDate(calendar);
//
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
