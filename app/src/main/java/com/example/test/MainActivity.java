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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MyMainActivity";
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
                MainActivity.this.startActivityForResult(intent, requestCode);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("test", "test");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("test", "test");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("test", "test");
                atEdittext.textChanged(s);
            }
        };

        atEdittext.addTextChangedListener(textWatcher);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sa:
                if (atEdittext.getPublishContent() != null) {
                    if (atEdittext.getPublishContent().getPersonListAt() != null) {
                        for (Person person: atEdittext.getPublishContent().getPersonListAt()) {
                            Log.i(TAG, "at:" + person.getName());
                        }
                    }
//                    if (atEdittext.getPublishContent().getPersonListTopic() != null) {
//                        for (Person person: atEdittext.getPublishContent().getPersonListTopic()) {
//                            Log.i(TAG, "topic:" + person.getName());
//                        }
//                    }
                    if (atEdittext.getPublishContent().getTopicList() != null) {
                        for (String topic: atEdittext.getPublishContent().getTopicList()) {
                            Log.i(TAG, "topic:" + topic);
                        }
                    }
                    tvResult.setText(atEdittext.getPublishContent().getContent());
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult = " + data);

        atEdittext.handleResult(requestCode, resultCode, data);
    }

}