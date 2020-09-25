package com.example.mahiaramarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.example.mahiaramarket.DeliveryActivity.SELECT_ADDRESS;


public class MyAddressesActivity extends AppCompatActivity {
private RecyclerView myAddressesRecyclerView;
private Button deliverHereBtn;
private static AddressesAdapter addressesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAddressesRecyclerView = findViewById(R.id.addresses_recycler_view);
        deliverHereBtn = findViewById(R.id.delivered_here_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);

        List<AddressesModel>addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("Mahiara design","Your addresses here full addressess fill here","110001",true));
        addressesModelList.add(new AddressesModel("test design","Your addresses here full addressess fill here","110033",false));
        addressesModelList.add(new AddressesModel("test mahiara","Your addresses here full addressess fill here","110032",false));
        addressesModelList.add(new AddressesModel("demo design","Your addresses here full addressess fill here","110021",false));
        addressesModelList.add(new AddressesModel("just design","Your addresses here full addressess fill here","110023",false));
        addressesModelList.add(new AddressesModel("check design","Your addresses here full addressess fill here","110030",false));
        addressesModelList.add(new AddressesModel("Mahiara","Your addresses here full addressess fill here","110054",false));
        addressesModelList.add(new AddressesModel("Mahiara test","Your addresses here full addressess fill here","110092",false));
int mode = getIntent().getIntExtra("MODE",-1);
if(mode == SELECT_ADDRESS){
    deliverHereBtn.setVisibility(View.VISIBLE);
}else{
    deliverHereBtn.setVisibility(View.GONE);
}
        addressesAdapter = new AddressesAdapter(addressesModelList,mode);
        myAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();


    }
    public static void refreshItem(int deselect,int select){
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
       return true;
        }
        return super.onOptionsItemSelected(item);
    }
}