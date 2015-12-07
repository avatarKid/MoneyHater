package vn.lol.moneyhater.moneyhater.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DataManager;
import vn.lol.moneyhater.moneyhater.Util.CommonFunction;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.adapter.ListTransactionAdapter;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.TransactionDate;

public class EditAccountActivity extends ActionBarActivity {

    private DataManager mDbHelper;
    private Account accountEdit;
    private ListTransactionAdapter mAdapterTransactionAcc;
    private ArrayList<TransactionDate> listTransactionAcc;
    private int accountID = 0;
    private int accountType = 0;
    EditText editAccName;
    EditText editCash;
    RadioButton radioCash;
    RadioButton radioCard;
    ListView listTransaction;
    String current = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);


        Intent intent = getIntent();
        // get global XML helper
        mDbHelper= (DataManager)getApplicationContext();

        accountID = intent.getIntExtra(ConstantValue.ACCOUNT_ID,0);
        accountEdit = mDbHelper.getAccount(accountID);
        editAccName = (EditText) findViewById(R.id.editAccountName);
        editAccName.setText(accountEdit.getAccountName());
        editCash = (EditText) findViewById(R.id.editCash);
        editCash.setText(NumberFormat.getInstance().format(accountEdit.getCash()));

        //List Transaction
        listTransaction = (ListView) findViewById(R.id.lvTransactionAcc);
        listTransactionAcc = CommonFunction.getListTransactionAndDate(mDbHelper.getTransactionByAccountID(accountID));
        if (!listTransactionAcc.isEmpty()) {
            mAdapterTransactionAcc = new ListTransactionAdapter(EditAccountActivity.this, listTransactionAcc, mDbHelper);
            listTransaction.setAdapter(mAdapterTransactionAcc);
            mAdapterTransactionAcc.notifyDataSetChanged();
        }

        editCash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                if(!charSequence.toString().equals(""))
                {
                    if(!charSequence.toString().equals(current)){
                        String cash = charSequence.toString().replaceAll("[,]", "");
                        double parsed = Double.parseDouble(cash);
                        String formated = NumberFormat.getInstance().format((parsed));
                        current = formated;
                        editCash.setText(formated);
                        editCash.setSelection(editCash.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        accountType = accountEdit.getAccountTypeID();
        radioCash = (RadioButton) findViewById(R.id.rbtCash);
        radioCard = (RadioButton) findViewById(R.id.rbtCard);
        if(accountType == 1){
            radioCash.setChecked(true);
        }
        if(accountType == 0){
            radioCard.setChecked(true);
        }

        Button updateAccount = (Button) findViewById(R.id.btSaveAcc);
        updateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(editAccName.getText().toString().trim().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please enter Account Name!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(editCash.getText().toString().trim().isEmpty() || editCash.getText().toString().trim().equals("0")){
                        Toast.makeText(getApplicationContext(), "Please enter Account Money!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (radioCard.isChecked()) {
                        accountType = 0;
                    }
                    if (radioCash.isChecked()) {
                        accountType = 1;
                    }
                    if(!editAccName.getText().toString().isEmpty()) {
                        accountEdit.setAccountName(editAccName.getText().toString());
                        accountEdit.setCash(Double.parseDouble(editCash.getText().toString().replaceAll("[,]", "")));
                        accountEdit.setAccountTypeID(accountType);
                        mDbHelper.updateAccount(accountEdit);
                        finish();
                    }
                } catch (Exception e){
                    Log.d("Edit Account" , e.getMessage());
                }
            }
        });

        Button deleteAccount = (Button) findViewById(R.id.btDeleteAcc);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbHelper.deleteAccount(accountID);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_account, menu);
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
