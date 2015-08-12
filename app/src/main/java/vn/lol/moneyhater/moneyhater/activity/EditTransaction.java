package vn.lol.moneyhater.moneyhater.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
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


    Button btSave, btDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        init();

        transaction = (Transaction) getIntent().getSerializableExtra("transaction");
        transactionID = transaction.getTransactionID();
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
    }

    public void updateTransaction() {
        transaction = new Transaction();
        transaction.setTransactionName(etTransactionName.getText().toString());

        try {
            transaction.setCash(Double.parseDouble(etTransactionMoney.getText().toString()));
        } catch (NumberFormatException e) {
            transaction.setCash(0);
            e.printStackTrace();
        }

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

    public void init() {
        btSave = (Button) findViewById(R.id.btSaveTransaction);
        etTransactionName = (EditText) findViewById(R.id.etEditTransName);
        etTransactionMoney = (EditText) findViewById(R.id.etEditTransactionMoney);
        spTransactionBudget = (Spinner) findViewById(R.id.spEditTransBudgetName);
        spTransactionAccount = (Spinner) findViewById(R.id.spEditTransAccountName);
//        dpTransactionDate = (DatePicker) findViewById(R.id.dpTransactionDate);
        swTransactionType = (Switch) findViewById(R.id.swEditTransactionType);
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
        etTransactionMoney.setText(transaction.getCash() + "");
        swTransactionType.setChecked(transaction.getType() == 0 ? false : true);
        spTransactionAccount.setSelection(adapterAccount.getPosition(currentAccount));
        spTransactionBudget.setSelection(adapterBudget.getPosition(currentBudget));
        //TODO get date to edittext
    }

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
