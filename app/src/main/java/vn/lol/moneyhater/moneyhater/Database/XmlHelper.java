package vn.lol.moneyhater.moneyhater.Database;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import vn.lol.moneyhater.moneyhater.model.Account;
import vn.lol.moneyhater.moneyhater.model.Budget;
import vn.lol.moneyhater.moneyhater.model.Transaction;

/**
 * Created by huybu on 11/24/2015.
 */
public class XmlHelper {
    private XmlPullParserFactory pullParserFactory;
    private XmlPullParser parser;
    public static String xmlFile;
    private String nameTag=null;
    private ArrayList<Account> allAccounts;
    private ArrayList<Budget> allBudgets;
    private ArrayList<Transaction> allTransactions;

    public ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

    public void setAllAccounts(ArrayList<Account> allAccounts) {
        this.allAccounts = allAccounts;
    }

    public ArrayList<Budget> getAllBudgets() {
        return allBudgets;
    }

    public void setAllBudgets(ArrayList<Budget> allBudgets) {
        this.allBudgets = allBudgets;
    }

    public ArrayList<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public void setAllTransactions(ArrayList<Transaction> allTransactions) {
        this.allTransactions = allTransactions;
    }

    public XmlHelper(Context context) {
        try {
            allAccounts = new ArrayList<>();
            allBudgets = new ArrayList<>();
            allTransactions = new ArrayList<>();
            xmlFile = context.getFilesDir().getPath().toString() + "/moneyhater.xml";
            pullParserFactory = XmlPullParserFactory.newInstance();
            parser = pullParserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void readDataXml() {
        try {
            FileReader fr = new FileReader(xmlFile);
            parser.setInput(fr);
            parser.nextTag();

            Account curAcc = null;
            Budget curBudget = null;
            Transaction curTran = null;

            int eventType = parser.getEventType();
            nameTag = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                try {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            switch (parser.getName()) {
                                case "Account":
                                    nameTag = parser.getName();
                                    curAcc = new Account();
                                    curAcc.setAccountID(Integer.parseInt(parser.getAttributeValue(null, "id")));
                                    break;
                                case "Budget":
                                    curBudget = new Budget();
                                    nameTag = parser.getName();
                                    curBudget.setBudgetID(Integer.parseInt(parser.getAttributeValue(null, "id")));
                                    break;
                                case "Transaction":
                                    curTran = new Transaction();
                                    nameTag = parser.getName();
                                    curTran.setTransactionID(Integer.parseInt(parser.getAttributeValue(null, "id")));
                                    break;
                                case "name":
                                    if (isAccount()) curAcc.setAccountName(parser.nextText());
                                    if (isBudget()) curBudget.setBudgetName(parser.nextText());
                                    if (isTransaction()) curTran.setTransactionName(parser.nextText());
                                    break;
                                case "cash":
                                    if (isAccount())
                                        curAcc.setCash(Double.parseDouble(parser.nextText()));
                                    if (isBudget())
                                        curBudget.setCash(Double.parseDouble(parser.nextText()));
                                    if (isTransaction())
                                        curTran.setCash(Double.parseDouble(parser.nextText()));
                                    break;
                                case "type_id":
                                    curAcc.setAccountTypeID(Integer.parseInt(parser.nextText()));
                                    break;
                                case "image_id":
                                    curBudget.setImageID(Integer.parseInt(parser.nextText()));
                                    break;
                                case "type":
                                    curTran.setType(Integer.parseInt(parser.nextText()));
                                    break;
                                case "category_id":
                                    curTran.setCategoryID(Integer.parseInt(parser.nextText()));
                                    break;
                                case "account_id":
                                    curTran.setAccountID(Integer.parseInt(parser.nextText()));
                                    break;
                                case "budget_id":
                                    curTran.setBudgetID(Integer.parseInt(parser.nextText()));
                                    break;
                                case "date":
                                    curTran.setDate((parser.nextText()));
                                    break;
                                case "id_deleted":
                                    curAcc.setIsDeleted(Integer.parseInt(parser.nextText()));
                                    break;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            switch (parser.getName()) {
                                case "Account":
                                    allAccounts.add(curAcc);
                                    break;
                                case "Budget":
                                    allBudgets.add(curBudget);
                                    break;
                                case "Transaction":
                                    allTransactions.add(curTran);
                                    break;
                            }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                eventType = parser.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeXml() {
        Log.e("write :", "XML");
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "moneyhater");

            serializer.startTag("", "Accounts");
            for (Account acc : allAccounts) {
                serializer.startTag("", "Account");
                serializer.attribute("", "id", acc.getAccountID() + "");
                addLeaf(serializer, "name", acc.getAccountName());
                addLeaf(serializer, "type_id", acc.getAccountTypeID() + "");
                addLeaf(serializer, "cash", acc.getCash() + "");
                addLeaf(serializer, "id_deleted", acc.getIsDeleted() + "");
                serializer.endTag("", "Account");
            }
            serializer.endTag("", "Accounts");

            serializer.startTag("", "Budgets");
            for (Budget acc : allBudgets) {
                serializer.startTag("", "Budget");
                serializer.attribute("", "id", acc.getBudgetID() + "");
                addLeaf(serializer, "name", acc.getBudgetName());
                addLeaf(serializer, "cash", acc.getCash() + "");
                serializer.endTag("", "Budget");
            }
            serializer.endTag("", "Budgets");

            serializer.startTag("", "Transactions");
            for (Transaction acc : allTransactions) {
                serializer.startTag("", "Transaction");
                serializer.attribute("", "id", acc.getTransactionID() + "");
                addLeaf(serializer, "name", acc.getTransactionName());
                addLeaf(serializer, "type", acc.getType() + "");
                addLeaf(serializer, "cash", acc.getCash() + "");
                addLeaf(serializer, "category_id", acc.getCategoryID() + "");
                addLeaf(serializer, "account_id", acc.getAccountID() + "");
                addLeaf(serializer, "budget_id", acc.getBudgetID() + "");
                addLeaf(serializer, "date", acc.getDateTime() + "");
                serializer.endTag("", "Transaction");
            }
            serializer.endTag("", "Transactions");

            serializer.endTag("", "moneyhater");
            serializer.endDocument();

            FileWriter f = new FileWriter(xmlFile);
            f.write(writer.toString());
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addLeaf(XmlSerializer serializer, String tagName, String value) throws IOException {
        serializer.startTag("", tagName);
        serializer.text(value);
        serializer.endTag("", tagName);
    }

    private boolean isAccount() {
        return nameTag.equals("Account");
    }

    private boolean isBudget() {
        return nameTag.equals("Budget");
    }

    private boolean isTransaction() {
        return nameTag.equals("Transaction");
    }
}
