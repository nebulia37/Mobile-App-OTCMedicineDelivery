package com.xiang.otcmedicinedelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xiang.otcmedicinedelivery.adapters.PlaceYourOrderAdapter;
import com.xiang.otcmedicinedelivery.model.Menu;
import com.xiang.otcmedicinedelivery.model.PharmacyModel;

import java.util.*;

public class CheckOutActivity extends AppCompatActivity {
    private static final String TAG = "CheckOutActivity";
    private EditText inputName, inputAddress, inputCardNumber;
    private RecyclerView cartItemsRecyclerView;
    private TextView tvTotalAmount, buttonPlaceYourOrder;
    private PlaceYourOrderAdapter placeYourOrderAdapter;
    private float totalAmount = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        PharmacyModel pharmacyModel = getIntent().getParcelableExtra("PharmacyModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(pharmacyModel.getName());
        actionBar.setSubtitle(pharmacyModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);


        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(pharmacyModel);
            }
        });
        initRecyclerView(pharmacyModel);
        calculateTotalAmount(pharmacyModel);
    }

    private void calculateTotalAmount(PharmacyModel pharmacyModel) {
        totalAmount = 0f;
        for(Menu m : pharmacyModel.getMenus()) {
            totalAmount += m.getPrice() * m.getTotalInCart();
        }
        tvTotalAmount.setText("$"+String.format("%.2f", totalAmount));
    }

    private void onPlaceOrderButtonClick(PharmacyModel pharmacyModel) {
        if(TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if(TextUtils.isEmpty(inputAddress.getText().toString())) {
            inputAddress.setError("Please enter address ");
            return;
        }else if( TextUtils.isEmpty(inputCardNumber.getText().toString())) {
            inputCardNumber.setError("Please enter card number ");
            return;
        }
        //start success activity..
        //TODO: upload order data to firestore
        saveData(pharmacyModel);
        Intent i = new Intent(CheckOutActivity.this, OrderSucceessActivity.class);
        i.putExtra("PharmacyModel", pharmacyModel);
        startActivityForResult(i, 1000);
    }

    private void initRecyclerView(PharmacyModel pharmacyModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeYourOrderAdapter = new PlaceYourOrderAdapter(pharmacyModel.getMenus());
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                break;
            case R.id.Home:
                Intent intent1 = new Intent(CheckOutActivity.this, PharmacyActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.Map:
                Intent intent2 = new Intent(CheckOutActivity.this, MapsActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }


    private void saveData(PharmacyModel pharmacyModel) {
        Map<String, Object> order = new HashMap<>();
        order.put("name", inputName.getText().toString());
        order.put("card_num", inputAddress.getText().toString());
        order.put("address", inputCardNumber.getText().toString());
        order.put("totalAmount", totalAmount);
        order.put("items", pharmacyModel.getMenus());
        db.collection("Orders")
                .add(order)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}