package vn.lol.moneyhater.moneyhater.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.model.Account;

public class EditAccountActivity extends ActionBarActivity {

    private DatabaseHelper mDbHelper;
    private Account accountEdit;
    private int accountID = 0;
    EditText editAccName;
    EditText editCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        mDbHelper = new DatabaseHelper(getApplicationContext());
        Intent intent = getIntent();
        accountID = intent.getIntExtra(ConstantValue.ACCOUNT_ID,0);
        accountEdit = mDbHelper.SelectAccount(accountID);
        editAccName = (EditText) findViewById(R.id.editAccountName);
        editAccName.setText(accountEdit.getAccountName());
        editCash = (EditText) findViewById(R.id.editCash);
        editCash.setText(String.format("%.0f",accountEdit.getCash()));

        Button updateAccount = (Button) findViewById(R.id.btSaveAcc);
        updateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountEdit.setAccountName(editAccName.getText().toString());
                accountEdit.setCash(Double.parseDouble(editCash.getText().toString()));
                mDbHelper.updateAccount(accountEdit);
                finish();
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
