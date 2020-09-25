package com.example.mahiaramarket;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.mahiaramarket.DBqueries.categoryModelList;
import static com.example.mahiaramarket.DBqueries.firebaseFirestore;

import static com.example.mahiaramarket.DBqueries.list;
import static com.example.mahiaramarket.DBqueries.loadCategories;
import static com.example.mahiaramarket.DBqueries.loadFragmentData;
import static com.example.mahiaramarket.DBqueries.loadedCategoriesNames;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home3Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home3Fragment() {
        // Required empty public constructor
    }
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView categoryRecylerView;
    private List<CategoryModel>categoryModelFakeList = new ArrayList<>();
    private  CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private List<HomePageModel>homePageModelFakeList = new ArrayList<>();
    private HomePageAdapter adapter;
private ImageView noInternetConnection;
private Button retryBtn;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Home3Fragment newInstance(String param1, String param2) {
        Home3Fragment fragment = new Home3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home3, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);
        categoryRecylerView = view.findViewById(R.id.category_recycler_view);
        homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);
        retryBtn = view.findViewById(R.id.retry_btn);

        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary),getContext().getResources().getColor(R.color.colorPrimary));


        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecylerView.setLayoutManager(layoutManager);


        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);

//////category  fake list////////////////////////
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel(" ",""));
        categoryModelFakeList.add(new CategoryModel(" ",""));
        categoryModelFakeList.add(new CategoryModel(" ",""));
        categoryModelFakeList.add(new CategoryModel(" ",""));
        categoryModelFakeList.add(new CategoryModel(" ",""));
        categoryModelFakeList.add(new CategoryModel(" ",""));
        categoryModelFakeList.add(new CategoryModel(" ",""));
        categoryModelFakeList.add(new CategoryModel(" ",""));


//////category  fake list////////////////////////

        /////home page fake list//////
        List<SliderModel>sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null","#dfdfdf"));

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
        homePageModelFakeList.add(new HomePageModel(1,"","#dfdfdf"));
        homePageModelFakeList.add(new HomePageModel(2,"","#dfdfdf",horizontalScrollProductModelFakeList,new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new HomePageModel(3,"","#dfdfdf",horizontalScrollProductModelFakeList));
        /////home page fake list//////

        categoryAdapter = new CategoryAdapter(categoryModelFakeList);


        adapter = new HomePageAdapter(homePageModelFakeList);


        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() == true) {
            HomeActivity2.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecylerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            if(categoryModelList.size() == 0){
                loadCategories(categoryRecylerView,getContext());

            }else{
                categoryAdapter = new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecylerView.setAdapter(categoryAdapter);
            if(list.size() == 0){
                loadedCategoriesNames.add("HOME");
                list.add(new ArrayList<HomePageModel>());

                loadFragmentData(homePageRecyclerView,getContext(),0,"Home");

            }else{
                adapter = new HomePageAdapter(list.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);
        }else{
            HomeActivity2.drawer.setDrawerLockMode(1);
            categoryRecylerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_inernet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);

        }

        //////////////////////////////refresh layout/////////////////////
swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
       reloadPage();
    }
});
        //////////////////////////////refresh layout/////////////////////

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reloadPage();

            }
        });
        return view;
    }

    @SuppressLint("WrongConstant")
    private void reloadPage(){
        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoryModelList.clear();
        list.clear();
        loadedCategoriesNames.clear();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            HomeActivity2.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecylerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            ///////fake adapter/////////
            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            adapter = new HomePageAdapter(homePageModelFakeList);
            ///////fake adapter/////////
            categoryRecylerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(adapter);

            loadCategories(categoryRecylerView, getContext());

            loadedCategoriesNames.add("HOME");
            list.add(new ArrayList<HomePageModel>());
            loadFragmentData(homePageRecyclerView, getContext(), 0, "Home");



        } else {
            HomeActivity2.drawer.setDrawerLockMode(1);
            Toast.makeText(getContext(),"No Internet Connection Found!",Toast.LENGTH_SHORT).show();
            categoryRecylerView.setVisibility(View.INVISIBLE);
            homePageRecyclerView.setVisibility(View.INVISIBLE);
            Glide.with(getContext()).load(R.drawable.no_inernet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}