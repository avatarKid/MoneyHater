package vn.lol.moneyhater.moneyhater.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.model.Transaction;

public class EditTransaction extends AppCompatActivity {

    Transaction transaction;
    int transactionID;
    DatabaseHelper databaseHelper;

    EditText etEditTransactionName, etEditTransactionMoney;
    Button btSave, btDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        btSave = (Button) findViewById(R.id.btSaveTransaction);
        btDelete = (Button) findViewById(R.id.btDeleteTransaction);

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
