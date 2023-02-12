package com.xiang.otcmedicinedelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import com.xiang.otcmedicinedelivery.adapters.PharmacyListAdapter;
import com.xiang.otcmedicinedelivery.model.PharmacyModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import android.util.Log;
import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.*;

public class PharmacyActivity extends AppCompatActivity implements PharmacyListAdapter.RestaurantListClickListener{
    private static final String TAG = "CheckOutActivity";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference pharmacyRef = db.collection("Pharmacy").document("n7U5Q6qQvQAUaSp9K5pp");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pharmacy List");
        List<PharmacyModel> pharmacyModelList =  new ArrayList<>();
//        initRecyclerView(pharmacyModelList);

//        pharmacyRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    List<Object> foo = (List<Object>) documentSnapshot.get("pharmacylist");
//                    Log.d(TAG, "get data success");
//                }else {
//                    Log.d(TAG, "no data exist");
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: " + e.toString());
//            }
//        });

        db.collection("Pharmacy").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                            PharmacyModel pharm = snapshots.toObject(PharmacyModel.class);
                            pharmacyModelList.add(pharm);
                        }
                        initRecyclerView(pharmacyModelList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });



    }

    private void initRecyclerView(List<PharmacyModel> pharmacyModelList) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PharmacyListAdapter adapter = new PharmacyListAdapter(pharmacyModelList, this);
        recyclerView.setAdapter(adapter);
    }

//    private List<PharmacyModel> getPharmacyData() {
//        InputStream is = getResources().openRawResource(R.raw.pharmacy);
//        Writer writer = new StringWriter();
//        char[] buffer = new char[1024];
//        try{
//            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            int n;
//            while(( n = reader.read(buffer)) != -1) {
//                writer.write(buffer, 0,n);
//            }
//        }catch (Exception e) {
//
//        }
//
//        String jsonStr = writer.toString();
//        Gson gson = new Gson();
//        PharmacyModel[] pharmacyModels =  gson.fromJson(jsonStr, PharmacyModel[].class);
//        List<PharmacyModel> restList = Arrays.asList(pharmacyModels);
//
////        //todo: need to be delete
////        for (PharmacyModel foo : restList) {
////            db.collection("Pharmacy")
////                    .add(foo);
////        }
//
////                    .addOnSuccessListener(new OnSuccessListener<Void>() {
////                @Override
////                public void onSuccess(Void aVoid) {
////                    Log.d(TAG, "upload success");
////                }
////            })
////                    .addOnFailureListener(new OnFailureListener() {
////                        @Override
////                        public void onFailure(@NonNull Exception e) {
////
////                            Log.d(TAG, e.toString());
////                        }
////                    });
////        }
//
//        return  restList;
//    }

    @Override
    public void onItemClick(PharmacyModel pharmacyModel) {
        Intent intent = new Intent(PharmacyActivity.this, MenuListActivity.class);
        intent.putExtra("PharmacyModel", pharmacyModel);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Home:
                Intent intent1 = new Intent(PharmacyActivity.this, MenuListActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.Map:
                Intent intent2 = new Intent(PharmacyActivity.this, MapsActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }




}

