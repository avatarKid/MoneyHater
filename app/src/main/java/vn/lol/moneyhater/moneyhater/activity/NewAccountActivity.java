package vn.lol.moneyhater.moneyhater.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.model.Account;

/**
 * Created by TuanAnh on 7/25/2015.
 */
public class NewAccountActivity extends ActionBarActivity {

    private DatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        mDbHelper = new DatabaseHelper(getApplicationContext());
        Button btAddAcc = (Button) findViewById(R.id.btAddNewAcc);
        btAddAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewAccount();
                mDbHelper.close();
                finish();
            }
        });
    }

    public void addNewAccount(){
        EditText accountName = (EditText) findViewById(R.id.etAccountName);
        EditText accountCash = (EditText) findViewById(R.id.etCash);
        Account newAccount = new Account(accountName.getText().toString(),
                Double.parseDouble(accountCash.getText().toString()),0);
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
