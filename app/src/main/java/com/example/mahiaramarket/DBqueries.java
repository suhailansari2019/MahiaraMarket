package com.example.mahiaramarket;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBqueries {


    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();


    public static List<List<HomePageModel>> list = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();


    public static List<String> wishList = new ArrayList<>();
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();


    public static List<String> myRatedIds = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();

    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();


    public static int selectedAddress = -1;
    public static List<AddressesModel> addressesModelList = new ArrayList<>();

    public static List<RewardModel> rewardModelList = new ArrayList<>();


    /////categories queries///////////
    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context) {
        categoryModelList.clear();

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                            }

                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /////categories queries///////////
    /////
    public static void loadFragmentData(final RecyclerView homePageRecyclerView, final Context context, final int index, String categoryName) {

        ///////firebase Query///////
        firebaseFirestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TOP_DEALS")
                .orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if ((long) documentSnapshot.get("view_type") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                                    for (long x = 1; x < no_of_banners + 1; x++) {
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner_" + x).toString(),
                                                documentSnapshot.get("banner_" + x + "_background").toString()));
                                    }
                                    list.get(index).add(new HomePageModel(0, sliderModelList));
                                } else if ((long) documentSnapshot.get("view_type") == 1) {
                                    list.get(index).add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString(), documentSnapshot.get("background").toString()));

                                } else if ((long) documentSnapshot.get("view_type") == 2) {


                                    List<WishlistModel> viewAllProductList = new ArrayList<>();
                                    List<HorizontalScrollProductModel> horizontalScrollProductModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x < no_of_products + 1; x++) {
                                        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(documentSnapshot.get("product_ID_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()));

                                        viewAllProductList.add(new WishlistModel(documentSnapshot.get("product_ID_" + x).toString(), documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_full_title_" + x).toString()
                                                , (long) documentSnapshot.get("free_coupens_" + x)
                                                , documentSnapshot.get("average_rating_" + x).toString()
                                                , (long) documentSnapshot.get("total_rating_" + x)
                                                , documentSnapshot.get("product_price_" + x).toString()
                                                , documentSnapshot.get("cutted_price_" + x).toString()
                                                , (boolean) documentSnapshot.get("COD_" + x)
                                                , (boolean) documentSnapshot.get("in_stock_" + x)));
                                    }
                                    list.get(index).add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), horizontalScrollProductModelList, viewAllProductList));

                                } else if ((long) documentSnapshot.get("view_type") == 3) {
                                    List<HorizontalScrollProductModel> GridLayoutModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x < no_of_products + 1; x++) {
                                        GridLayoutModelList.add(new HorizontalScrollProductModel(documentSnapshot.get("product_ID_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()));
                                    }
                                    list.get(index).add(new HomePageModel(3, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), GridLayoutModelList));

                                }

                            }
                            HomePageAdapter homePageAdapter = new HomePageAdapter(list.get(index));
                            homePageRecyclerView.setAdapter(homePageAdapter);

                            homePageAdapter.notifyDataSetChanged();
                            Home3Fragment.swipeRefreshLayout.setRefreshing(false);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        ///////firebase Query///////
    }

    /////wishlist  queries///////////
    public static void loadWishlist(final Context context, final Dialog dialog, final boolean loadProductData) {
        wishList.clear();
        firebaseFirestore.collection("USER").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")

                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        wishList.add(task.getResult().get("product_ID_" + x).toString());

                        if (DBqueries.wishList.contains(ProductDetailsActivity.productID)) {
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                            if (ProductDetailsActivity.addToWishlistbtn != null) {
                                ProductDetailsActivity.addToWishlistbtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.btnRed));
                            }
                        } else {
                            if (ProductDetailsActivity.addToWishlistbtn != null) {
                                ProductDetailsActivity.addToWishlistbtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9A9A9A")));
                            }

                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                        }

                        if (loadProductData) {
                            wishlistModelList.clear();
                            final String productid = task.getResult().get("product_ID_" + x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(productid)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        final DocumentSnapshot documentSnapshot = task.getResult();
                                        FirebaseFirestore.getInstance().collection("PRODUCTS").document(productid).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {

                                                            if (task.getResult().getDocuments().size() < (long) documentSnapshot.get("stock_quantity")) {
                                                                wishlistModelList.add(new WishlistModel(productid, documentSnapshot.get("product_image_1").toString()
                                                                        , documentSnapshot.get("product_title").toString()
                                                                        , (long) documentSnapshot.get("free_coupens")
                                                                        , documentSnapshot.get("average_rating").toString()
                                                                        , (long) documentSnapshot.get("total_rating")
                                                                        , documentSnapshot.get("product_price").toString()
                                                                        , documentSnapshot.get("cutted_price").toString()
                                                                        , (boolean) documentSnapshot.get("COD"),
                                                                        true));
                                                            } else {
                                                                wishlistModelList.add(new WishlistModel(productid, documentSnapshot.get("product_image_1").toString()
                                                                        , documentSnapshot.get("product_title").toString()
                                                                        , (long) documentSnapshot.get("free_coupens")
                                                                        , documentSnapshot.get("average_rating").toString()
                                                                        , (long) documentSnapshot.get("total_rating")
                                                                        , documentSnapshot.get("product_price").toString()
                                                                        , documentSnapshot.get("cutted_price").toString()
                                                                        , (boolean) documentSnapshot.get("COD"),
                                                                        false));
                                                            }
                                                            MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                                                        } else {
                                                            ///error handling
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });
    }
    /////wishlist  queries///////////

    ////////////Wishlist item remove method///////////
    public static void removeFromWishlist(final int index, final Context context) {
        final String removeProductId = wishList.get(index);
        wishList.remove(index);
        Map<String, Object> updateWishlist = new HashMap<>();
        for (int x = 0; x < wishList.size(); x++) {
            updateWishlist.put("product_ID_" + x, wishList.get(x));
        }
        updateWishlist.put("list_size", (long) wishList.size());

        firebaseFirestore.collection("USER").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (wishlistModelList.size() != 0) {
                        wishlistModelList.remove(index);
                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                    }
                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                    Toast.makeText(context, "Remove Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    if (ProductDetailsActivity.addToWishlistbtn != null) {
                        ProductDetailsActivity.addToWishlistbtn.setBackgroundTintList(context.getResources().getColorStateList(R.color.btnRed));
                    }
                    wishList.add(index, removeProductId);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
//
                ProductDetailsActivity.running_wishlist_query = false;
            }
        });
    }
////////////Wishlist item remove method///////////

    ////ratings method//////
    public static void loadRatingList(final Context context) {
        if (ProductDetailsActivity.running_rating_query) {
            ProductDetailsActivity.running_rating_query = true;

            myRatedIds.clear();
            myRating.clear();
            firebaseFirestore.collection("USER").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_RATINGS")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                            myRatedIds.add(task.getResult().get("product_ID_" + x).toString());
                            myRating.add((long) task.getResult().get("rating_" + x));
                            if (task.getResult().get("product_ID_" + x).toString().equals(ProductDetailsActivity.productID)) {
                                ProductDetailsActivity.initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;
                                if (ProductDetailsActivity.rateNowContainer != null) {
                                    ProductDetailsActivity.setRating(ProductDetailsActivity.initialRating);
                                }
                            }
                        }


                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                    ProductDetailsActivity.running_rating_query = false;
                }
            });
        }

    }
    //////ratings method//////////


    /////////////////Add to cart method/////////////////////
    public static void loadCartList(final Context context, final Dialog dialog, final boolean loadProductData, final TextView badgeCount, final TextView cartTotalAmount) {
        cartList.clear();
        firebaseFirestore.collection("USER").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")

                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        cartList.add(task.getResult().get("product_ID_" + x).toString());

                        if (DBqueries.cartList.contains(ProductDetailsActivity.productID)) {
                            ProductDetailsActivity.ALREADY_ADDED_TO_CARTLIST = true;
                        } else {
                            ProductDetailsActivity.ALREADY_ADDED_TO_CARTLIST = false;
                        }

                        if (loadProductData) {
                            cartItemModelList.clear();
                            final String productid = task.getResult().get("product_ID_" + x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(productid)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        ////new Query/////
                                        final DocumentSnapshot documentSnapshot = task.getResult();
                                        FirebaseFirestore.getInstance().collection("PRODUCTS").document(productid).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            int index = 0;
                                                            if (cartList.size() >= 2) {
                                                                index = cartList.size() - 2;
                                                            }


                                                            if (task.getResult().getDocuments().size() < (long) documentSnapshot.get("stock_quantity")) {
                                                                cartItemModelList.add(index, new CartItemModel(CartItemModel.CART_ITEM, productid, documentSnapshot.get("product_image_1").toString()
                                                                        , documentSnapshot.get("product_title").toString()
                                                                        , (long) documentSnapshot.get("free_coupens")
                                                                        , documentSnapshot.get("product_price").toString()
                                                                        , documentSnapshot.get("cutted_price").toString()
                                                                        , (long) 1
                                                                        , (long) 0
                                                                        , (long) 0
                                                                        , true
                                                                        , (Long) documentSnapshot.get("max-quantity")
                                                                        , (Long) documentSnapshot.get("stock_quantity")));
                                                            } else {
                                                                cartItemModelList.add(index, new CartItemModel(CartItemModel.CART_ITEM, productid, documentSnapshot.get("product_image_1").toString()
                                                                        , documentSnapshot.get("product_title").toString()
                                                                        , (long) documentSnapshot.get("free_coupens")
                                                                        , documentSnapshot.get("product_price").toString()
                                                                        , documentSnapshot.get("cutted_price").toString()
                                                                        , (long) 1
                                                                        , (long) 0
                                                                        , (long) 0
                                                                        , false
                                                                        , (Long) documentSnapshot.get("max-quantity")
                                                                        , (Long) documentSnapshot.get("stock_quantity")));
                                                            }
                                                            if (cartList.size() == 1) {
                                                                cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                                                LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
                                                                parent.setVisibility(View.VISIBLE);
                                                            }
                                                            if (cartList.size() == 0) {
                                                                cartItemModelList.clear();
                                                            }

                                                            MyCartFragment.cartAdapter.notifyDataSetChanged();
                                                        } else {
                                                            ///error handling
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                        ///////new query//////

                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    if (cartList.size() != 0) {
                        badgeCount.setVisibility(View.VISIBLE);
                    } else {
                        badgeCount.setVisibility(View.INVISIBLE);
                    }
                    if (DBqueries.cartList.size() < 99) {
                        badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                    } else {
                        badgeCount.setText("99");
                    }

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });


    }
    /////////////////Add to cart method/////////////////////


    /////////////////Remove from cart method/////////////////////
    public static void removeFromCart(final int index, final Context context, final TextView cartTotalAmount) {
        final String removeProductId = cartList.get(index);
        cartList.remove(index);
        Map<String, Object> updateCartList = new HashMap<>();
        for (int x = 0; x < cartList.size(); x++) {
            updateCartList.put("product_ID_" + x, cartList.get(x));
        }
        updateCartList.put("list_size", (long) cartList.size());

        firebaseFirestore.collection("USER").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_CART")
                .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (cartItemModelList.size() != 0) {
                        cartItemModelList.remove(index);
                        MyCartFragment.cartAdapter.notifyDataSetChanged();
                    }
                    if (cartList.size() == 0) {
                        LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
                        parent.setVisibility(View.GONE);
                        cartItemModelList.clear();
                    }
                    Toast.makeText(context, "Remove Successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    cartList.add(index, removeProductId);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

                ProductDetailsActivity.running_cart_query = false;
            }
        });
    }

    /////////////////Remove from cart method/////////////////////
    //////////////Addressess add//////////////////
    public static void loadAddresses(final Context context, final Dialog loadingDialog) {

        addressesModelList.clear();
        firebaseFirestore.collection("USER").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESSES")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Intent deliveryIntent;
                    if ((long) task.getResult().get("list_size") == 0) {
                        deliveryIntent = new Intent(context, AddAddressActivity.class);
                        deliveryIntent.putExtra("INTENT", "deliveryIntent");
                    } else {
                        for (long x = 1; x < (long) task.getResult().get("list_size") + 1; x++) {
                            addressesModelList.add(new AddressesModel(task.getResult().get("fullname_" + x).toString(),
                                    task.getResult().get("address_" + x).toString(),
                                    task.getResult().get("pincode_" + x).toString(),
                                    (boolean) task.getResult().get("selected_" + x),
                                    task.getResult().get("mobile_no_" + x).toString()));
                            if ((boolean) task.getResult().get("selected_" + x)) {
                                selectedAddress = Integer.parseInt(String.valueOf(x - 1));
                            }
                        }
                        deliveryIntent = new Intent(context, DeliveryActivity.class);
                    }
                    context.startActivity(deliveryIntent);
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                loadingDialog.dismiss();
            }
        });
    }
    //////////////Addressess add//////////////////

    /////////////Rewards Methods///////////

    public static void loadRewards(final Context context, final Dialog loadingDialog) {
        rewardModelList.clear();
        firebaseFirestore.collection("USER").document(FirebaseAuth.getInstance().getUid()).collection("USER_REWARDS").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (documentSnapshot.get("type").toString().equals("Discount")) {
                                    rewardModelList.add(new RewardModel(documentSnapshot.get("type").toString()
                                            , documentSnapshot.get("lower_limit").toString()
                                            , documentSnapshot.get("upper_limit").toString()
                                            , documentSnapshot.get("percentage").toString()
                                            , documentSnapshot.get("body").toString()
//                                            , (Date) documentSnapshot.get("validity")
                                            ,documentSnapshot.getTimestamp("validity").toDate()
                                    ));
                                } else {
                                    rewardModelList.add(new RewardModel(documentSnapshot.get("type").toString()
                                            , documentSnapshot.get("lower_limit").toString()
                                            , documentSnapshot.get("upper_limit").toString()
                                            , documentSnapshot.get("amount").toString()
                                            , documentSnapshot.get("body").toString()
//                                            , (Date) documentSnapshot.get("validity")
                                            ,documentSnapshot.getTimestamp("validity").toDate()
                                    ));
                                }
                            }
                            MyRewardsFragment.myRewardsAdapter.notifyDataSetChanged();

                        } else {
                            ///error handling
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();

                    }
                });
    }

    /////////////Rewards Methods///////////


    ////////Clear Data Method/////////
    public static void clearData() {
        categoryModelList.clear();
        list.clear();
        loadedCategoriesNames.clear();
        wishList.clear();
        wishlistModelList.clear();
        cartList.clear();
        cartItemModelList.clear();
        myRatedIds.clear();
        myRating.clear();
        addressesModelList.clear();
        rewardModelList.clear();
    }
    ////////Clear data  Method/////////


}
