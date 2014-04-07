package com.xianglanqi.losesleep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class HelloActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hello);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
                startActivity(new Intent(HelloActivity.this, MainActivity.class));
            }

        }, 300);
    }

}
