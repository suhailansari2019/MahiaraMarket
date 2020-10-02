package com.example.mahiaramarket;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mahiaramarket.ui.home.HomeFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.mahiaramarket.R.id.imageView;
import static com.example.mahiaramarket.R.id.nav_my_mall;
import static com.example.mahiaramarket.RegisterActivity.setSignUpFragment;

public class HomeActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private static  final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT =2;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int REWARDS_FRAGMENT = 4;
    private static final int ACCOUNT_FRAGMENT = 5;
    public static Boolean showCart=false;
    public static Activity homeActivity2;
    public static boolean resetHomeActivity2 = false;


    private FrameLayout frameLayout;

    private ImageView actionBarLogo;
    private int currentFragment = -1;
    private NavigationView navigationView;

    private Window window;
    private Toolbar toolbar;
    private Dialog signInDialog;
    private FirebaseUser currentUser;
    private TextView badgeCount;
    private int scrollFlags;
    private AppBarLayout.LayoutParams params;

    public static DrawerLayout drawer;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarLogo = findViewById(R.id.action_bar_logo);

window = getWindow();
window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
scrollFlags = params.getScrollFlags();


         drawer =(DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout = findViewById(R.id.main_framelayout);

        if (showCart) {
            homeActivity2 = this;
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_white);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            gotoFragment("My Cart", new MyCartFragment(), -2);
        } else {

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new Home3Fragment(), HOME_FRAGMENT);

        }

        signInDialog = new Dialog(HomeActivity2.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);

        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.sign_up_btn);
        final Intent registerIntent = new Intent(HomeActivity2.this,RegisterActivity.class);

        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);

        }else{
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);
        }
        if(resetHomeActivity2){
            resetHomeActivity2 = false;
            actionBarLogo.setVisibility(View.VISIBLE);
            setFragment(new Home3Fragment(),HOME_FRAGMENT);
            navigationView.getMenu().getItem(0).setChecked(true);
        }
        invalidateOptionsMenu();
    }

    @Override
public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            if(currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();
            }else{
                if(showCart){
                    homeActivity2 =null;
                    showCart = false;
                    finish();

                }else {
                    actionBarLogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new Home3Fragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);

                }
            }
        }
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(currentFragment == HOME_FRAGMENT){
            navigationView.getMenu().getItem(0).setChecked(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.home_activity2, menu);

            MenuItem cartItem = menu.findItem(R.id.main_cart_icon);

                cartItem.setActionView(R.layout.badge_layout);
                ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
                badgeIcon.setImageResource(R.mipmap.cart);
                badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);
                if(currentUser != null){
                    if (DBqueries.cartList.size() == 0) {
                        DBqueries.loadCartList(HomeActivity2.this, new Dialog(HomeActivity2.this), false,badgeCount,new TextView(HomeActivity2.this));
                    }else {
                            badgeCount.setVisibility(View.VISIBLE);
                        if(DBqueries.cartList.size()<99) {
                            badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                        }else{
                            badgeCount.setText("99");
                        }
                    }
                }

                cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(currentUser == null) {
                            signInDialog.show();
                        }else {
                            gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
                        }
                    }
                });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.main_search_icon) {
            //todo:serach
            return true;
        } else if (id == R.id.main_notification_icon) {
            //todo:notification
            return true;
        } else if (id == R.id.main_cart_icon) {
            if(currentUser == null) {
                signInDialog.show();
            }else {
                gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
            }
             return true;
        }else if (id == android.R.id.home){
            if(showCart){
                homeActivity2 = null;
                showCart =false;
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoFragment(String title,Fragment fragment,int fragmentNo) {

        actionBarLogo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment,fragmentNo);
        if(fragmentNo == CART_FRAGMENT || showCart) {
            navigationView.getMenu().getItem(3).setChecked(true);
            params.setScrollFlags(0);

        }else{
            params.setScrollFlags(scrollFlags);
        }
//        else if(fragmentNo ==  ORDERS_FRAGMENT){
//            navigationView.getMenu().getItem(1).setChecked(true);
//        }else if(fragmentNo == WISHLIST_FRAGMENT){
//            navigationView.getMenu().getItem(4).setChecked(true);
//        }else if(fragmentNo == REWARDS_FRAGMENT){
//            navigationView.getMenu().getItem(2).setChecked(true);
//        }else if(fragmentNo == ACCOUNT_FRAGMENT){
//            navigationView.getMenu().getItem(5).setChecked(true);
//        }

        }
MenuItem menuItem;
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        menuItem = item;

        if(currentUser != null ) {

            drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    int id = menuItem.getItemId();
                    if (id == R.id.nav_my_mall) {
                        actionBarLogo.setVisibility(View.VISIBLE);
                        invalidateOptionsMenu();
                        setFragment(new Home3Fragment(), HOME_FRAGMENT);
                    } else if (id == R.id.nav_my_orders) {

                        gotoFragment("My Orders", new MyOrderFragment(), ORDERS_FRAGMENT);
                    } else if (id == R.id.nav_my_rewards) {
                        gotoFragment("My Rewards", new MyRewardsFragment(), REWARDS_FRAGMENT);
                    } else if (id == R.id.nav_my_cart) {
                        gotoFragment("MyCart", new MyCartFragment(), CART_FRAGMENT);
                    } else if (id == R.id.nav_my_wishlist) {
                        gotoFragment("My Wishlist", new MyWishlistFragment(), WISHLIST_FRAGMENT);

                    } else if (id == R.id.nav_my_account) {
                        gotoFragment("My Account", new MyAccountFragment(), ACCOUNT_FRAGMENT);
                    } else if (id == R.id.nav_sign_out) {
                        FirebaseAuth.getInstance().signOut();
                        DBqueries.clearData();
                        Intent registerIntent = new Intent(HomeActivity2.this,RegisterActivity.class);
                        startActivity(registerIntent);
                        finish();

                    }
                }
            });


            return true;
        }else{

            signInDialog.show();
            return false;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setFragment(Fragment fragment,int FragmentNo){
        if(FragmentNo != currentFragment) {
            if(FragmentNo == REWARDS_FRAGMENT){
                window.setStatusBarColor(Color.parseColor("#5B04B1"));
                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));
            }else{
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            }

            currentFragment = FragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//            drawer.closeDrawer(GravityCompat.START);

        }
    }
}