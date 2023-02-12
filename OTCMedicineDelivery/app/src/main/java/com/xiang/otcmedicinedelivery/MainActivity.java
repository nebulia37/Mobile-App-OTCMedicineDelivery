package com.xiang.otcmedicinedelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.os.Handler;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    public String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView1);
        imageView.animate().alpha(0f).setDuration(0);

        imageView = (ImageView)findViewById(R.id.imageView2);
        imageView.animate().alpha(1f).setDuration(800);
        Log.i(TAG,"onCreate");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "onResume");

    }



}
