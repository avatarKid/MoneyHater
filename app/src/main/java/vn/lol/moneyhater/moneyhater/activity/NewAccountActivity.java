package vn.lol.moneyhater.moneyhater.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.NumberFormat;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.model.Account;

/**
 * Created by TuanAnh on 7/25/2015.
 */
public class NewAccountActivity extends ActionBarActivity {

    private DatabaseHelper mDbHelper;
    EditText accountName;
    EditText accountCash;
    RadioButton radioCard;
    RadioButton radioCash;
    Button btAddAcc;
    String current = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        mDbHelper = new DatabaseHelper(getApplicationContext());
        accountName = (EditText) findViewById(R.id.etAccountName);
        accountCash = (EditText) findViewById(R.id.etCash);
        radioCard = (RadioButton) findViewById(R.id.rbtCard);
        radioCash = (RadioButton) findViewById(R.id.rbtCash);
        btAddAcc = (Button) findViewById(R.id.btAddNewAcc);
        accountCash.addTextChangedListener(new TextWatcher() {
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
                        accountCash.setText(formated);
                        accountCash.setSelection(accountCash.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btAddAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addNewAccount();
                    finish();
                }  catch (Exception e){
                    Log.d("Add Account", e.getMessage());
                }
            }
        });
    }

    public void addNewAccount() {
        int accountType = 1;
        if (radioCard.isChecked()) {
            accountType = 0;
        }
        if (radioCash.isChecked()) {
            accountType = 1;
        }

        Account newAccount = new Account(accountName.getText().toString(),
                Double.parseDouble(accountCash.getText().toString().replaceAll("[,]", "")),
                accountType);
        mDbHelper.insertAccount(newAccount);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_account, menu);
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
