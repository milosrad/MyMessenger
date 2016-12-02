package com.example.user.mymessenger.activity;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.mymessenger.R;
import com.example.user.mymessenger.R;

/**
 * Created by User on 13.6.2016.
 */
public class ShowMessageActivity extends Activity {

    private EditText mSenderEditText,mNewMessageEditText,mResponseEditText;
    private Button mButtonSend;
    private SmsManager sms = SmsManager.getDefault();
    private int numOfClicks=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_message_activity);
        initComponents();
        addListeners();

    }


    private void initComponents(){

        mSenderEditText=(EditText)findViewById(R.id.editTextsendermessage);
        mNewMessageEditText=(EditText)findViewById(R.id.editTextNewMessage);
        mResponseEditText=(EditText)findViewById(R.id.editTextNewMessage);
        mButtonSend=(Button)findViewById(R.id.buttonsend);
        mSenderEditText.setText(getIntent().getStringExtra("sendersmessage"));



    }

    private void addListeners(){

        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numOfClicks++;
                sms.sendTextMessage(getIntent().getStringExtra("senderNum"),null,mResponseEditText.getText().toString(),null,null);
                mNewMessageEditText.setText("");

                finish();


            }
        });

        mNewMessageEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewMessageEditText.setText("");
            }
        });

    }
}
