package com.xiang.otcmedicinedelivery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.xiang.otcmedicinedelivery.adapters.MenuListAdapter;
import com.xiang.otcmedicinedelivery.model.Menu;
import com.xiang.otcmedicinedelivery.model.PharmacyModel;

import java.util.ArrayList;
import java.util.List;

public class MenuListActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener{

    private List<Menu> menuList = null;
    private MenuListAdapter menuListAdapter;
    private List<Menu> itemsInCartList;
    private int totalItemInCart = 0;
    private TextView buttonCheckout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        PharmacyModel pharmacyModel = getIntent().getParcelableExtra("PharmacyModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(pharmacyModel.getName());
        actionBar.setSubtitle(pharmacyModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);


        menuList = pharmacyModel.getMenus();
        initRecyclerView();


        buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsInCartList != null && itemsInCartList.size() <= 0) {
                    Toast.makeText(MenuListActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                    return;
                }
                pharmacyModel.setMenus(itemsInCartList);
                Intent i = new Intent(MenuListActivity.this, CheckOutActivity.class);
                i.putExtra("PharmacyModel", pharmacyModel);
//                startActivityForResult(i, 1000);
                startActivity(i);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        menuListAdapter = new MenuListAdapter(menuList, this);
        recyclerView.setAdapter(menuListAdapter);
    }

    @Override
    public void onAddToCartClick(Menu menu) {
        if(itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(menu);
        totalItemInCart = 0;

        for(Menu m : itemsInCartList) {
            totalItemInCart = totalItemInCart + m.getTotalInCart();
        }
        buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
    }

    @Override
    public void onUpdateCartClick(Menu menu) {
        if(itemsInCartList.contains(menu)) {
            int index = itemsInCartList.indexOf(menu);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, menu);

            totalItemInCart = 0;

            for(Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if(itemsInCartList.contains(menu)) {
            itemsInCartList.remove(menu);
            totalItemInCart = 0;

            for(Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
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
                Intent intent1 = new Intent(MenuListActivity.this, PharmacyActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.Map:
                Intent intent2 = new Intent(MenuListActivity.this, MapsActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            //
            finish();
        }
    }


}