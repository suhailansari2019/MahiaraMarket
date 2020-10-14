package com.example.mahiaramarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.mahiaramarket.DBqueries.list;
import static com.example.mahiaramarket.DBqueries.loadFragmentData;
import static com.example.mahiaramarket.DBqueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView categoryRecyclerView;
    private List<HomePageModel>homePageModelFakeList = new ArrayList<>();
    private HomePageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /////home page fake list//////
        List<SliderModel>sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));

        List<HorizontalScrollProductModel> horizontalScrollProductModelFakeList = new ArrayList<>();
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("","","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("","","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("","","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("","","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("","","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("","","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("","","","",""));
        horizontalScrollProductModelFakeList.add(new HorizontalScrollProductModel("","","","",""));

        homePageModelFakeList.add(new HomePageModel(0,sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1,"","#ffffff"));
        homePageModelFakeList.add(new HomePageModel(2,"","#ffffff",horizontalScrollProductModelFakeList,new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new HomePageModel(3,"","#ffffff",horizontalScrollProductModelFakeList));
        /////home page fake list//////


        ///////////////////////////
        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        adapter = new HomePageAdapter(homePageModelFakeList);


        int listPosition = 0;
        for(int x = 0; x < loadedCategoriesNames.size();x++){
            if(loadedCategoriesNames.get(x).equals(title.toUpperCase())){
                listPosition = x;
            }
        }
        if(listPosition == 0){
            loadedCategoriesNames.add(title.toUpperCase());
            list.add(new ArrayList<HomePageModel>());
            loadFragmentData(categoryRecyclerView,this,loadedCategoriesNames.size() - 1,title);
        }else{
            adapter = new HomePageAdapter(list.get(listPosition));
        }
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //////////////////////////////

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.main_search_icon) {
            Intent searchIntent = new Intent(this,SearchActivity.class);
            startActivity(searchIntent);
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}