package com.deckart.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    TextView txt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.editTxt);

        Context context = getApplicationContext();

        findViewById(R.id.button).setOnClickListener(view -> txt.setText(txt.getText().toString() + "4"));
        findViewById(R.id.button2).setOnClickListener(view -> txt.setText(txt.getText().toString() + "5"));
        findViewById(R.id.button3).setOnClickListener(view -> txt.setText(txt.getText().toString() + "1"));
        findViewById(R.id.button4).setOnClickListener(view -> txt.setText(txt.getText().toString() + "2"));
        findViewById(R.id.button5).setOnClickListener(view -> txt.setText(txt.getText().toString() + "3"));
        findViewById(R.id.button6).setOnClickListener(view -> txt.setText(txt.getText().toString() + "6"));
        findViewById(R.id.button7).setOnClickListener(view -> txt.setText(txt.getText().toString() + "7"));
        findViewById(R.id.button8).setOnClickListener(view -> txt.setText(txt.getText().toString() + "8"));
        findViewById(R.id.button9).setOnClickListener(view -> txt.setText(txt.getText().toString() + "9"));
        findViewById(R.id.button10).setOnClickListener(view -> equation(1));
        findViewById(R.id.button11).setOnClickListener(view -> equation(2));
        findViewById(R.id.button12).setOnClickListener(view -> equation(3));
        findViewById(R.id.button13).setOnClickListener(view -> equation(5));
        findViewById(R.id.button14).setOnClickListener(view -> txt.setText(txt.getText().toString() + "0"));
        findViewById(R.id.button15).setOnClickListener(view -> equation(7));
        findViewById(R.id.button16).setOnClickListener(view -> equation(4));
        findViewById(R.id.button17).setOnClickListener(view -> Toast.makeText(context,"Not supported", Toast.LENGTH_LONG).show());
        findViewById(R.id.button18).setOnClickListener(view -> equation(6));
        findViewById(R.id.button19).setOnClickListener(view -> Toast.makeText(context,"Not supported",Toast.LENGTH_LONG).show());
        findViewById(R.id.button20).setOnClickListener(view -> txt.setText(""));
    }

    @SuppressLint("SetTextI18n")
    void equation(int i) {
        String calc = txt.getText().toString();

        int plusI = calc.indexOf("+");
        int subI = calc.indexOf("-",1);
        int multiI = calc.indexOf("x");
        int divI = calc.indexOf("/");
        int calcL = calc.length();

        boolean CalcArgs = (plusI & subI & divI & multiI) < 0 & !calc.equals("") & !calc.equals("-");
        switch (i) {
            case 1:
                if (CalcArgs) {
                    txt.setText(calc + "/");
                }
                break;
            case 2:
                if (CalcArgs) {
                    txt.setText(calc + "x");
                }
                break;
            case 3:
                if ((plusI & subI & divI & multiI) < 0 & !calc.equals("-")) {
                    txt.setText(calc + "-");
                }
                break;
            case 4:
                if (CalcArgs) {
                    txt.setText(calc + "+");
                }
                break;
            case 5:
                if (!calc.contains(".") && !calc.contains("+") && !calc.contains("-") && !calc.contains("/") && !calc.contains("x")) {
                    txt.setText(calc + ".");
                }
                if (calc.contains("+") && !calc.substring(plusI+1, calcL).contains(".")) {
                    txt.setText(calc + ".");
                } else if (calc.contains("-") && !calc.substring(subI+1, calcL).contains(".")) {
                    txt.setText(calc + ".");
                } else if (calc.contains("/") && !calc.substring(divI+1, calcL).contains(".")) {
                    txt.setText(calc + ".");
                } else if (calc.contains("x") && !calc.substring(multiI+1, calcL).contains(".")) {
                    txt.setText(calc + ".");
                }
                break;
            case 6:
                if (CalcArgs) {
                    double per = Double.parseDouble(calc);
                    double perEnd = per / 100;
                    BigDecimal BPer = new BigDecimal(perEnd).setScale(4,RoundingMode.HALF_UP);
                    String perEndS = BPer.toString();
                    String perEndS2 = perEndS.contains(".") ? perEndS.replaceAll("0*$","").replaceAll("\\.$","") : perEndS;
                    txt.setText(perEndS2);
                }
                break;
            case 7:
                if (calc.endsWith("+") | calc.endsWith("-") | calc.endsWith("/") | calc.endsWith("x")) {
                    break;
                }
                if (plusI > 0) {
                    String plus1 = calc.substring(0, plusI);
                    double p1 = Double.parseDouble(plus1);
                    String plus2 = calc.substring(plusI+1, calcL);
                    double p2 = Double.parseDouble(plus2);
                    double pEnd = p1 + p2;
                    BigDecimal bPlus = new BigDecimal(pEnd).setScale(4,RoundingMode.HALF_UP);
                    String pEndS = bPlus.toString();
                    String pEndS2 = pEndS.contains(".") ? pEndS.replaceAll("0*$","").replaceAll("\\.$","") : pEndS;
                    txt.setText(pEndS2);
                } else if (subI > 0) {
                    String sub1 = calc.substring(0, subI);
                    double s1 = Double.parseDouble(sub1);
                    String sub2 = calc.substring(subI+1, calcL);
                    double s2 = Double.parseDouble(sub2);
                    double sEnd = s1 - s2;
                    BigDecimal bSub = new BigDecimal(sEnd).setScale(4,RoundingMode.HALF_UP);
                    String sEndS = bSub.toString();
                    String sEnds2 = sEndS.contains(".") ? sEndS.replaceAll("0*$","").replaceAll("\\.$","") : sEndS;
                    txt.setText(sEnds2);
                } else if (multiI > 0) {
                    String multi1 = calc.substring(0, multiI);
                    double m1 = Double.parseDouble(multi1);
                    String multi2 = calc.substring(multiI+1, calcL);
                    double m2 = Double.parseDouble(multi2);
                    double mEnd = m1 * m2;
                    BigDecimal bMulti = new BigDecimal(mEnd).setScale(4, RoundingMode.HALF_UP);
                    String mEndS = bMulti.toString();
                    String mEndS2 = mEndS.contains(".") ? mEndS.replaceAll("0*$","").replaceAll("\\.$","") : mEndS;
                    txt.setText(mEndS2);
                } else if (divI > 0) {
                    String div1 = calc.substring(0, divI);
                    double d1 = Double.parseDouble(div1);
                    String div2 = calc.substring(divI+1, calcL);
                    double d2 = Double.parseDouble(div2);
                    double dEnd = d1 / d2;
                    BigDecimal bDiv = new BigDecimal(dEnd).setScale(4, RoundingMode.HALF_UP);
                    String dEndS = bDiv.toString();
                    String dEndS2 = dEndS.contains(".") ? dEndS.replaceAll("0*$","").replaceAll("\\.$","") : dEndS;
                    txt.setText(dEndS2);
                }
                break;
        }
    }
}
