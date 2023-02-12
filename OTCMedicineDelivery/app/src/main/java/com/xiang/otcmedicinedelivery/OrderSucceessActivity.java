package com.xiang.otcmedicinedelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.xiang.otcmedicinedelivery.model.PharmacyModel;

public class OrderSucceessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_succeess);


        PharmacyModel PharmacyModel = getIntent().getParcelableExtra("PharmacyModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(PharmacyModel.getName());
        actionBar.setSubtitle(PharmacyModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(false);


        TextView buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSucceessActivity.this, PharmacyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(android.view.Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.Home:
//                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.Profile:
//                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.Order:
//                Toast.makeText(this, "Order selected", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                //do nothing
//        }
//        return super.onOptionsItemSelected(item);
//    }
}