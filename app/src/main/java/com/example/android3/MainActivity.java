package com.example.android3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        hideSystemUI();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @SuppressLint("SetTextI18n")
    public void buttonClick(View view) {
        String text = (String)((Button)view).getText();
        TextView textView = findViewById(R.id.result);

        if (textView.length() > 0) {
            if (textView.getText().charAt(textView.length() - 1) == '0') {
                Pattern pattern = Pattern.compile("[-+*/%]0$");
                Matcher matcher = pattern.matcher(textView.getText());
                if (matcher.find() || textView.length() == 1) {
                    clearOneClick(view);
                }
            }
        }
        textView.setText(textView.getText() + text);
    }

    @SuppressLint("SetTextI18n")
    public void zeroClick(View view) {
        String text = (String)((Button)view).getText();
        TextView textView = findViewById(R.id.result);

        if (textView.length() > 0) {
            Pattern pattern = Pattern.compile("[-+*/%]0$");
            Matcher matcher = pattern.matcher(textView.getText());
            if (matcher.find()) {
                return;
            }
        }
        textView.setText(textView.getText() + text);
    }

    public void clearOneClick(View view) {
        TextView textView = findViewById(R.id.result);
        int end = textView.length() - 1;
        if (end >= 0) {
            textView.setText(textView.getText().subSequence(0, end));
        }
    }

    public void clearClick(View view) {
        TextView textView = findViewById(R.id.result);
        textView.setText("");
    }

    public void resultClick(View view) {
        try {
            TextView textView = findViewById(R.id.result);
            Expression expression = new ExpressionBuilder(textView.getText().toString()).build();
            double rDouble = expression.evaluate();
            int rInt = (int) rDouble;
            String result = Double.toString(rDouble);
            if ((double) rInt == rDouble) {
                result = Integer.toString(rInt);
            }
            textView.setText(result);
        }
        catch (Exception ignored) {}
    }

    @SuppressLint("SetTextI18n")
    public void changeClick(View view) {
        TextView textView = findViewById(R.id.result);
        if (textView.length() == 0) {
            return;
        }
        char last = textView.getText().charAt(textView.length() - 1);
        if("+-*/".contains(Character.toString(last))) {
            clearOneClick(view);
            switch (last) {
                case '+': {
                    textView.setText(textView.getText() + "-");
                    break;
                }
                case '-': {
                    textView.setText(textView.getText() + "+");
                    break;
                }
                case '*': {
                    textView.setText(textView.getText() + "/");
                    break;
                }
                case '/': {
                    textView.setText(textView.getText() + "*");
                    break;
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void operationClick(View view) {
        String text = (String)((Button)view).getText();
        TextView textView = findViewById(R.id.result);
        if("+-*/%".contains(text)) {
            if (textView.length() == 0) {
                return;
            }
            Pattern pattern = Pattern.compile("[-+*/%.]$");
            Matcher matcher = pattern.matcher(textView.getText());
            if (matcher.find()) {
                clearOneClick(view);
            }
        }
        textView.setText(textView.getText() + text);
    }

    @SuppressLint("SetTextI18n")
    public void dotClick(View view) {
        String text = (String)((Button)view).getText();
        TextView textView = findViewById(R.id.result);

        if (textView.length() > 0) {
            Pattern pattern = Pattern.compile("[-+*/%.]$|[0-9]+[.][0-9]+$");
            Matcher matcher = pattern.matcher(textView.getText());
            if (matcher.find()) {
                return;
            }
            textView.setText(textView.getText() + text);
        }
    }
}