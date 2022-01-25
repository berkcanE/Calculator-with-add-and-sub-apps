package com.example.mainapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText ETtextRevert;
    Button BTrevert;
    String txtGet;
    Chronometer chronometer;


    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode()== Activity.RESULT_OK)
                {
                    Intent data = result.getData();
                    if(data!=null)
                        handleResult(data);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETtextRevert = findViewById(R.id.ETtextRevert);
        BTrevert = findViewById(R.id.BTrevert);
        chronometer = findViewById(R.id.chronometer);
        chronometer.start();


       BTrevert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    txtGet = ETtextRevert.getText().toString();
                    Intent i = new Intent(MainActivity.this, RevertTextActivity.class);
                    i.putExtra("toRevert", txtGet);
                    startActivity(i);
                    finish();
            }
        });
    }



    private void handleResult(Intent result)
    {
        TextView operationText = findViewById(R.id.operation);
        TextView resultText = findViewById(R.id.result);
        operationText.setText(result.getStringExtra(Intent.EXTRA_TEXT));
        resultText.setText(String.valueOf(result.getIntExtra("result",0)));

    }

    public void run (View view) {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, gatherData());
        intent.setAction("com.example.mainapp");
        intent.setType("text/plain");
        launcher.launch(intent);
    }


    private int[] gatherData() {
        TextView ETfirst = findViewById(R.id.ETfirstArgument);
        TextView ETsecond = findViewById(R.id.ETsecondArgument);
        return new int[]
                {
                        getNumber(ETfirst),
                        getNumber(ETsecond)
                };
    }



    private int getNumber(TextView tv) {
        return getNumber(tv.getText().toString());
    }

    private int getNumber(String txt) {
        return Integer.parseInt(nullCheck(txt));
    }

    private String nullCheck(String txt)
    {
        return txt.equals("") ? "0" : txt;
    }

}