package vn.lol.moneyhater.moneyhater.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.lol.moneyhater.momeyhater.R;
import vn.lol.moneyhater.moneyhater.Database.DatabaseHelper;
import vn.lol.moneyhater.moneyhater.Util.ConstantValue;
import vn.lol.moneyhater.moneyhater.view.Bar;
import vn.lol.moneyhater.moneyhater.view.BarGraph;
import vn.lol.moneyhater.moneyhater.view.Line;
import vn.lol.moneyhater.moneyhater.view.LineGraph;
import vn.lol.moneyhater.moneyhater.view.LinePoint;

public class ChartFragment extends Fragment {
    private DatabaseHelper mDbHelper;
    float [] lMoneyExpense;
    float [] lMoneyIncome;
    private int [] lPointMoneyE;
    private int [] lPointMoneyI;
    private float maxMoney = 0;
    private float bMoneyExpense;
    private float bMoneyIncome;
    private Line l,l1;
    int month, year;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container,
                false);
        lMoneyExpense = new float[12];
        lMoneyIncome = new float[12];
        lPointMoneyE = new int[12];
        lPointMoneyI = new int[12];
        l = new Line();
        l.setColor(Color.parseColor("#33ad34"));
        l1 = new Line();
        l1.setColor(Color.parseColor("#ff4444"));
        final Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH)+1;
        year = c.get(Calendar.YEAR);
        maxMoney = 0;
        try {
            GetDatabase();
            SetupBar(rootView);
            SetupPointLine(rootView);
        }catch (Exception e){
            Toast.makeText(getActivity(),
                    "Some thing wrong",
                    Toast.LENGTH_SHORT)
                    .show();
        }
        return rootView;
    }

    private void SetupBar(View rootView){
        ArrayList<Bar> points = new ArrayList<Bar>();
        Bar d = new Bar();
        d.setColor(Color.parseColor("#ff4444"));
        d.setName("Expense");
        d.setValue(bMoneyExpense);
        Bar d2 = new Bar();
        d2.setColor(Color.parseColor("#33ad34"));
        d2.setName("Income");
        d2.setValue(bMoneyIncome);
        points.add(d);
        points.add(d2);
        BarGraph g = (BarGraph) rootView.findViewById(R.id.charBar);
        g.setBars(points);
    }

    private void SetupPointLine(View rootView){
        ProcessAnalysLineGraph();
        LineGraph li = (LineGraph)rootView.findViewById(R.id.charLine);
        li.addLine(l);
        li.addLine(l1);
        li.setRangeY(0, 11);
        li.setLineToFill(0);
        li.setOnPointClickedListener(new LineGraph.OnPointClickedListener() {
            @Override
            public void onClick(int lineIndex, int pointIndex) {

                if(lineIndex == 0){
                    final String formated = NumberFormat.getInstance().format(lMoneyIncome[pointIndex]);
                    Toast.makeText(getActivity(),
                            formated + ConstantValue.SETTING_CURRENCY,
                            Toast.LENGTH_SHORT)
                            .show();
                }else if (lineIndex == 1){
                    if((int) lMoneyExpense[pointIndex] > 0) {
                        final String formated = NumberFormat.getInstance().format(lMoneyExpense[pointIndex]);
                        Toast.makeText(getActivity(),
                                "- " + formated + ConstantValue.SETTING_CURRENCY, Toast.LENGTH_SHORT)
                                .show();
                    }
                    else {
                        final String formated = NumberFormat.getInstance().format(lMoneyIncome[pointIndex]);
                        Toast.makeText(getActivity(),
                                formated + ConstantValue.SETTING_CURRENCY,
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }

            }
        });
    }

    private LinePoint createNewPoint(int x,int y){
        LinePoint p = new LinePoint();
        p.setX(x);
        p.setY(y);
        return p;
    }

    private void ProcessAnalysLineGraph(){
        //Find max money
        for(int i=0;i< lMoneyExpense.length;i++){
            if(lMoneyExpense[i]>maxMoney){
                maxMoney = lMoneyExpense[i];
            }
            if(lMoneyIncome[i] > maxMoney){
                maxMoney = lMoneyIncome[i];
            }
        }
        //Calculate Line Point
        for(int i=0;i< lMoneyExpense.length;i++){
            lPointMoneyE[i] = (int)((lMoneyExpense[i]/maxMoney) *10);
            l1.addPoint(createNewPoint(i, lPointMoneyE[i]));
            lPointMoneyI[i] = (int)((lMoneyIncome[i]/maxMoney) *10);
            l.addPoint(createNewPoint(i, lPointMoneyI[i]));
        }

    }


    private void GetDatabase() {
        //Get database
        try {
            mDbHelper = new DatabaseHelper(getActivity());
            Cursor cs = mDbHelper.getTransactionInMonth(month);
            cs.moveToFirst();
            bMoneyIncome = 0;
            bMoneyExpense = 0;
            if (cs.getCount() == 1) {
                bMoneyIncome = cs.getFloat(0);
                bMoneyExpense = cs.getFloat(1);
            }
            Cursor cs2 = mDbHelper.getTransactionInYear(year);
            cs2.moveToFirst();
            if (cs2.getCount() == 2) {
                for(int i =0;i<12;i++){
                    lMoneyExpense[i] = cs2.getFloat(i);

                }
                cs2.moveToNext();
                for(int i =0;i<12;i++){
                    lMoneyIncome[i] = cs2.getFloat(i);

                }
            }

        }catch (Exception e){
            Toast.makeText(getActivity(),
                    "Error get Database", Toast.LENGTH_SHORT)
                    .show();
        }
    }



}
