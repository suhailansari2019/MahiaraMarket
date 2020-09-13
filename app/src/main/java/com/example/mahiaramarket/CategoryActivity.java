package com.example.mahiaramarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView categoryRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_recyclerview);

//////////Banner Slider

        List<SliderModel>sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.mipmap.logo_circle,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.logo_square,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.dummyimage,"#077AE4"));

        sliderModelList.add(new SliderModel(R.mipmap.cart_icon_black,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.mail,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.market_logo,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.banner,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.logo_circle,"#077AE4"));

        sliderModelList.add(new SliderModel(R.mipmap.logo_square,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.dummyimage,"#077AE4"));
        sliderModelList.add(new SliderModel(R.mipmap.cart_icon_black,"#077AE4"));

////////// Banner Slider



        /////// Horizontal Product layout
        List<HorizontalScrollProductModel> horizontalScrollProductModelList = new ArrayList<>();
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.dummyimage,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.logo_square,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.logo_circle,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.add_profile_logo,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.mail,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.cart_icon_black,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.dummyimage,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.heart_icon_black,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.logo_circle,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        /////// Horizontal Product layout
        ////////////////////////////////


        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        List<HomePageModel>homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.bannerad,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deal's of Today",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(3,"Deal's of Today",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.bannerad,"#000000"));
        homePageModelList.add(new HomePageModel(3,"Deal's of Today",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(2,"Deal's of Today",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.bannerad,"#ffff00"));



        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
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
            //todo:serach
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}