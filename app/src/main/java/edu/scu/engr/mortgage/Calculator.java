package edu.scu.engr.mortgage;

import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import static java.lang.Math.pow;


public class Calculator extends Activity implements View.OnClickListener {

    int interestRate;
    Double amountBorrowed;
    int loanTerm;
    boolean includeTaxes;
    Double monthlyPymt;

    Button calculateButton;
    CheckBox includeTaxesCheckBox;
    RadioGroup loanTermRadioGroup;
    RadioButton seven;
    RadioButton fifteen;
    RadioButton thirty;
    SeekBar interestRateBar;
    TextView monthlyPymtTextView;
    TextView interestRateLabel;
    EditText amountBorrowedEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        includeTaxes = false;
        loanTerm = 7;
        interestRate = 5;

        calculateButton = (Button) findViewById(R.id.button);
        interestRateBar = (SeekBar) findViewById(R.id.seekBar);
        loanTermRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        seven = (RadioButton) findViewById(R.id.radioButton);
        fifteen = (RadioButton) findViewById(R.id.radioButton2);
        thirty = (RadioButton) findViewById(R.id.radioButton3);
        includeTaxesCheckBox = (CheckBox) findViewById(R.id.checkBox);
        monthlyPymtTextView = (TextView) findViewById(R.id.result);
        amountBorrowedEditText = (EditText) findViewById(R.id.editText);
        monthlyPymtTextView = (TextView) findViewById(R.id.result);
        interestRateLabel = (TextView) findViewById(R.id.textView);


        calculateButton.setOnClickListener(this);

        interestRateBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                interestRate = progress;
                interestRateLabel.setText("Interest Rate ("+progress+ "%)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        loanTermRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == seven.getId())
                    loanTerm = 7;
                else if(checkedId == fifteen.getId())
                    loanTerm = 15;
                else if (checkedId == thirty.getId())
                    loanTerm = 30;
            }
        });

        includeTaxesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                includeTaxes = isChecked;
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == calculateButton.getId()) {
        amountBorrowed = Double.parseDouble(amountBorrowedEditText.getText().toString());
            DecimalFormat df = new DecimalFormat("#.00");
            monthlyPymtTextView.setText(
                df.format(
                        calculateMonthly(amountBorrowed, interestRate, loanTerm, includeTaxes)));
        }
    }

    private Double calculateMonthly (Double amountBorrowed, int interestRate, int loanTerm, boolean taxes) {
        Double monthlyPymt;
        Double irate = Double.valueOf(interestRate)/1200;

        // Monthly Payment Calculation
        if (irate != 0.0)
            monthlyPymt = amountBorrowed * (irate/(1-pow((1 + irate), (double)-(loanTerm))));
        else
            monthlyPymt = amountBorrowed / loanTerm;

        // Including taxes
        if (taxes) monthlyPymt += amountBorrowed*0.01;

        return monthlyPymt;
    }
}
