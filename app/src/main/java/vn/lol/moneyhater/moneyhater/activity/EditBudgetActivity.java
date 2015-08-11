package vn.lol.moneyhater.moneyhater.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.model.Budget;

public class EditBudgetActivity extends ActionBarActivity {
    private DatabaseHelper mDbHelper;
    private Budget budget;
    private int id = 0;
    EditText name;
    EditText cash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_budget, menu);
        mDbHelper = new DatabaseHelper(getApplicationContext());
        Intent intent = getIntent();
        id = intent.getIntExtra(ConstantValue.ACCOUNT_ID, 0);
        budget = mDbHelper.getBudget(id);
        name = (EditText) findViewById(R.id.et_edit_bud_name);
        name.setText(budget.getBudgetName());
        cash = (EditText) findViewById(R.id.et_edit_cash);
        cash.setText(budget.getCash() + "");

        Button updateBudget = (Button) findViewById(R.id.bt_save_bud);
        updateBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nameVal = name.getText().toString();
                    budget.setBudgetName(nameVal);
                    if(nameVal.isEmpty() || name == null){
                        Toast.makeText(EditBudgetActivity.this, "Name Value is Empty", Toast.LENGTH_LONG).show();
                        return;
                    }
                    budget.setCash(Double.parseDouble(cash.getText().toString()));
                    mDbHelper.updateBudget(budget);
                    finish();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(EditBudgetActivity.this,"Error Cash Value", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button deleteAccount = (Button) findViewById(R.id.bt_delete_bud);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbHelper.deleteBudget(id);
                finish();
            }
        });

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
