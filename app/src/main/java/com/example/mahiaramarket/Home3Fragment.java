package com.example.mahiaramarket;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private RecyclerView categoryRecylerView;
    private  CategoryAdapter categoryAdapter;
    private RecyclerView testing;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home3, container, false);

        categoryRecylerView = view.findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecylerView.setLayoutManager(layoutManager);

        final List<CategoryModel>categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Persnolized Jewellery"));
        categoryModelList.add(new CategoryModel("link","Necklace"));
        categoryModelList.add(new CategoryModel("link","Pendant"));
        categoryModelList.add(new CategoryModel("link","Bracelate"));
        categoryModelList.add(new CategoryModel("link","Pillar Bar Necklace"));
        categoryModelList.add(new CategoryModel("link","Customised Jewellery"));
        categoryModelList.add(new CategoryModel("link","Rings"));
        categoryModelList.add(new CategoryModel("link","Heart Necklace"));
        categoryModelList.add(new CategoryModel("link","Couple Pendant"));
        categoryModelList.add(new CategoryModel("link","New Products"));
        categoryModelList.add(new CategoryModel("link","Gold Necklace"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecylerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
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
      horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.img_1,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.img_2,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.img_3,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.img_4,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.img_1,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.img_2,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.img_3,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.img_4,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.drawable.img_1,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        /////// Horizontal Product layout
        ////////////////////////////////

        testing = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel>homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.bannerad,"#0063fd"));
        homePageModelList.add(new HomePageModel(2,"Deal's of Today",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(3,"Deal's of Today",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.bannerad,"#0063fd"));
        homePageModelList.add(new HomePageModel(3,"Deal's of Today",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(2,"Deal's of Today",horizontalScrollProductModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.bannerad,"#0063fd"));
        homePageModelList.add(new HomePageModel(0,sliderModelList));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //////////////////////////////
        return view;
    }

}