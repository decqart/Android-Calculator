package com.deckart.calculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    int op_count = 0;

    enum Equations {
        ADDITION,
        SUBTRACT,
        MULTIPLICATION,
        DIVISION,
        PERCENT,
        DOT,
        EQUAL
    }

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
        findViewById(R.id.button10).setOnClickListener(view -> equation(Equations.DIVISION));
        findViewById(R.id.button11).setOnClickListener(view -> equation(Equations.MULTIPLICATION));
        findViewById(R.id.button12).setOnClickListener(view -> equation(Equations.SUBTRACT));
        findViewById(R.id.button13).setOnClickListener(view -> equation(Equations.DOT));
        findViewById(R.id.button14).setOnClickListener(view -> txt.setText(txt.getText().toString() + "0"));
        findViewById(R.id.button15).setOnClickListener(view -> equation(Equations.EQUAL));
        findViewById(R.id.button16).setOnClickListener(view -> equation(Equations.ADDITION));
        findViewById(R.id.button17).setOnClickListener(view -> Toast.makeText(context, "Not supported", Toast.LENGTH_LONG).show());
        findViewById(R.id.button18).setOnClickListener(view -> equation(Equations.PERCENT));
        findViewById(R.id.button19).setOnClickListener(view -> Toast.makeText(context, "Not supported", Toast.LENGTH_LONG).show());
        findViewById(R.id.button20).setOnClickListener(view -> {
            txt.setText("");
            op_count = 0;
        });
    }

    @SuppressLint("SetTextI18n")
    void equation(Equations eq) {
        String calc = txt.getText().toString();

        boolean plusI = !calc.endsWith("+");
        boolean subI = !calc.endsWith("-");
        boolean multiI = !calc.endsWith("x");
        boolean divI = !calc.endsWith("/");
        int calcL = calc.length();

        boolean calcArgs = plusI && subI && divI && multiI && !calc.equals("");
        switch (eq) {
            case DIVISION:
                if (op_count > 39) break;
                if (calcArgs) {
                    txt.setText(calc + "/");
                    op_count++;
                } else if (calcL > 1) {
                    txt.setText(calc.substring(0, calcL - 1) + "/");
                }
                break;
            case MULTIPLICATION:
                if (op_count > 39) break;
                if (calcArgs) {
                    txt.setText(calc + "x");
                    op_count++;
                } else if (calcL > 1) {
                    txt.setText(calc.substring(0, calcL - 1) + "x");
                }
                break;
            case SUBTRACT:
                if (op_count > 39) break;
                if (plusI && subI && divI && multiI) {
                    txt.setText(calc + "-");
                    op_count++;
                } else {
                    txt.setText(calc.substring(0, calcL - 1) + "-");
                }
                break;
            case ADDITION:
                if (op_count > 39) break;
                if (calcArgs) {
                    txt.setText(calc + "+");
                    op_count++;
                } else if (calcL > 1) {
                    txt.setText(calc.substring(0, calcL - 1) + "+");
                }
                break;
            case DOT:
                int dotCount = 0;
                for (int i = 0; i < calcL; i++) {
                    if (calc.toCharArray()[i] == '.')
                        dotCount++;
                }
                if (dotCount-1 < op_count) {
                    if (calcArgs)
                        txt.setText(calc + ".");
                    else
                        txt.setText(calc + "0.");
                }
                break;
            case PERCENT:
                if (calcArgs) {
                    double per = Double.parseDouble(calc) / 100;
                    BigDecimal BPer = new BigDecimal(per).setScale(4,RoundingMode.HALF_UP);
                    String perEndS = BPer.toString();
                    perEndS = perEndS.contains(".") ? perEndS.replaceAll("0*$","").replaceAll("\\.$","") : perEndS;
                    txt.setText(perEndS);
                }
                break;
            case EQUAL:
                if (calc.endsWith("+") || calc.endsWith("-") || calc.endsWith("/") || calc.endsWith("x") ||
                    calc.isEmpty()) {
                    break;
                }
                // use this to parse string
                char[] operators = new char[45];
                double[] values = new double[45];

                boolean negative = calc.startsWith("-");

                if (negative)
                    calc = calc.substring(1);

                String[] sCalc = calc.split("[x/+\\-]");
                for (int j = 0; j < sCalc.length; j++)
                    values[j] = Double.parseDouble(sCalc[j]);

                if (negative) values[0] = -values[0];

                char[] cCalc = calc.toCharArray();
                for (int j = 0, a = 0; j < cCalc.length; j++) {
                    char ch = cCalc[j];
                    if (!Character.isDigit(ch) && ch != '.') {
                        operators[a] = ch;
                        a++;
                    }
                }

                //first iteration
                for (int j = 0; j < sCalc.length-1; ++j) {
                    if (operators[j] == 'x') {
                        values[j] = values[j] * values[j+1];
                        System.arraycopy(values, j + 2, values, j + 1, sCalc.length - j);
                        System.arraycopy(operators, j + 1, operators, j, sCalc.length - j);
                        j--;
                    } else if (operators[j] == '/') {
                        values[j] = values[j] / values[j+1];
                        System.arraycopy(values, j + 2, values, j + 1, sCalc.length - j);
                        System.arraycopy(operators, j + 1, operators, j, sCalc.length - j);
                        j--;
                    }
                }

                //second iteration
                for (int j = 0; j < sCalc.length-1; ++j) {
                    if (operators[j] == '+') {
                        values[j] = values[j] + values[j+1];
                        System.arraycopy(values, j + 2, values, j + 1, sCalc.length - j);
                        System.arraycopy(operators, j + 1, operators, j, sCalc.length - j);
                        j--;
                    } else if (operators[j] == '-') {
                        values[j] = values[j] - values[j+1];
                        System.arraycopy(values, j + 2, values, j + 1, sCalc.length - j);
                        System.arraycopy(operators, j + 1, operators, j, sCalc.length - j);
                        j--;
                    }
                }
                double result = values[0];
                BigDecimal bigResult = new BigDecimal(result).setScale(4, RoundingMode.HALF_UP);
                String endResult = bigResult.toString();
                endResult = endResult.contains(".") ? endResult.replaceAll("0*$","").replaceAll("\\.$","") : endResult;
                txt.setText(endResult);
                op_count = 0;
                break;
        }
    }
}
