package com.example.mahiaramarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
private ViewPager productImagesViewPager;
private TabLayout viewPagerIndicator;

private ViewPager productDetailsViewPager;
private TabLayout productDetailsTabLayout;

////////rating layouts /////////
    private LinearLayout rateNowContainer;

////////rating layouts /////////
private static boolean ALREADY_ADDED_TO_WISHLIST = false;
private FloatingActionButton addToWishlistbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewPagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishlistbtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);

        List<Integer>productImages  =new ArrayList<>();
        productImages.add(R.drawable.img_1);
        productImages.add(R.drawable.img_2);
        productImages.add(R.drawable.img_3);
        productImages.add(R.drawable.img_4);

        ProductImagesAdapter productImagesAdapter =new ProductImagesAdapter(productImages);
        productImagesViewPager.setAdapter(productImagesAdapter);

        viewPagerIndicator.setupWithViewPager(productImagesViewPager,true);

        addToWishlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ALREADY_ADDED_TO_WISHLIST){
                    ALREADY_ADDED_TO_WISHLIST = false;
                    addToWishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9A9A9A")));
                }else{
                    ALREADY_ADDED_TO_WISHLIST = true;
                   addToWishlistbtn.setSupportImageTintList(getResources().getColorStateList(R.color.btnRed));
                }
            }
        });
         productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTabLayout.getTabCount()));
         productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
         productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
             @Override
             public void onTabSelected(TabLayout.Tab tab) {
                 productDetailsViewPager.setCurrentItem(tab.getPosition());
             }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

             }
         });

         //////rating layout///////
      rateNowContainer = findViewById(R.id.rate_now_container);
      for(int x=0;x<rateNowContainer.getChildCount();x++){
          final int startPosition = x;
          rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  setRating(startPosition);
              }
          });
      }
        //////rating layout///////
    }

    private void setRating(int startPosition) {
        for(int x=0;x < rateNowContainer.getChildCount();x++){
            ImageView startBtn = (ImageView)rateNowContainer.getChildAt(x);
            startBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x<=startPosition){
                startBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            //todo:serach
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            //todo:notification
            return true;
        } else if (id == R.id.main_cart_icon) {
            //todo:cart
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}