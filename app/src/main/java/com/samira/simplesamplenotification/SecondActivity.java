package com.samira.simplesamplenotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private AppCompatTextView notificationContentTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        notificationContentTv = this.findViewById(R.id.notificationContentTv);
        onNewIntent(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle bundle = intent.getExtras();
        if (bundle != null){
            String name = bundle.getString("KEY_NAME");
            String email = bundle.getString("KEY_EMAIL");
            String type = bundle.getString("KEY_TYPE");

            String content = ""+name + " \n"+ email +" \n"+ type;

            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
            //set the data to textView
            notificationContentTv.setText(content);
        }



    }
}