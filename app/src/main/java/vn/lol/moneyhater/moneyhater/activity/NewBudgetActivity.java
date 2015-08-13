package vn.lol.moneyhater.moneyhater.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.Budget;

public class NewBudgetActivity extends ActionBarActivity {
    private DatabaseHelper mDbHelper;
    EditText budgetName ,budgetCash;
    String current = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_budget);
        budgetName = (EditText) findViewById(R.id.et_budget_name);
        budgetCash  = (EditText) findViewById(R.id.et_budget_cash);

        budgetCash.addTextChangedListener(new TextWatcher() {
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
                        budgetCash.setText(formated);
                        budgetCash.setSelection(budgetCash.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDbHelper = new DatabaseHelper(getApplicationContext());
        Button btAddAcc = (Button) findViewById(R.id.bt_add);
        btAddAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBudget();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_budget, menu);
        return true;
    }
    public void addNewBudget(){


        try {
            String name = budgetName.getText().toString();
            if(name.isEmpty() || name == null){
                Toast.makeText(NewBudgetActivity.this, "Error Value", Toast.LENGTH_LONG).show();
                return;
            }
            Double cash = Double.parseDouble(budgetCash.getText().toString().replaceAll("[,]", ""));
            Budget newBudget = new Budget(name ,0, cash);
            mDbHelper.insertBudget(newBudget);
            finish();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(NewBudgetActivity.this, "Error Value", Toast.LENGTH_LONG).show();
        }


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
