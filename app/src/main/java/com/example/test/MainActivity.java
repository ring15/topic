package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.test.AtEditText.CODE_PERSON;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AtEditText atEdittext;
    private Button sa;

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        atEdittext = findViewById(R.id.list_item);
        tvResult = findViewById(R.id.tv_result);
        sa = findViewById(R.id.sa);
        sa.setOnClickListener(this);

        atEdittext.setOnJumpListener(new AtEditText.OnJumpListener() {
            @Override
            public void goToChooseContact(int requestCode) {
                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                MainActivity.this.startActivityForResult(intent, CODE_PERSON);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sa:
                tvResult.setText(atEdittext.getPublishContent().getContent());
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("LHD", "onActivityResult = " + data);

        atEdittext.handleResult(requestCode, resultCode, data);
    }

}