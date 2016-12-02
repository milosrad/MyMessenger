package com.example.user.mymessenger.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.mymessenger.R;
import com.example.user.mymessenger.R;

public class MainActivity extends AppCompatActivity {

    private Button mButton;


    public static final String CUSTOM_INTENT="nastava.cubes.primer.NOTIFICATION_SMS_BROADCASTRECEIVER";

    private Intent mCustomIntent= new Intent(CUSTOM_INTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        initComp();


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ShowMessageActivity.class);
                intent.putExtra("senderNum",getIntent().getStringExtra("senderNum"));
                intent.putExtra("sendersmessage",getIntent().getStringExtra("sendersmessage"));
                startActivity(intent);
                finish();

            }
        });


    }


    private void initComp(){


        mButton=(Button)findViewById(R.id.button);


    }
}

