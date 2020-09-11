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

     ///////// Banner Slider
    private ViewPager bannersliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;

    ///////// Banner Slider
    //////// Strip ad layout
private ImageView stripAdImage;
private ConstraintLayout stripAdContainer;
    //////// Strip ad layout
    ////////Horizontal product layout
     private TextView horizontalLayoutTitle;
     private Button horizontalViewAllBtn;
     private RecyclerView horizontalRecyclerView;

    ////////Horizontal product layout

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

        List<CategoryModel>categoryModelList = new ArrayList<CategoryModel>();
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
        bannersliderViewPager = view.findViewById(R.id.banner_slider_view_pager);
        sliderModelList = new ArrayList<SliderModel>();

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


        SliderAdapter sliderAdapter =new SliderAdapter(sliderModelList);
        bannersliderViewPager.setAdapter(sliderAdapter);
        bannersliderViewPager.setClipToPadding(false);
        bannersliderViewPager.setPageMargin(20);

        bannersliderViewPager.setCurrentItem(currentPage);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
               if(state == ViewPager.SCROLL_STATE_IDLE){
                   pageLooper();
               }
            }
        };
        bannersliderViewPager.addOnPageChangeListener(onPageChangeListener);

        startbannerslideshow();

        bannersliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pageLooper();
                stopbannerslideshow();
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    startbannerslideshow();
                }
                return false;
            }
        });

////////// Banner Slider

        //////// Strip ad layout
        stripAdImage = view.findViewById(R.id.strip_ad_image);
        stripAdContainer = view.findViewById(R.id.strip_ad_container);

        stripAdImage.setImageResource(R.mipmap.bannerad);
        stripAdContainer.setBackgroundColor(Color.parseColor("#077AE4"));

        //////// Strip ad layout
        /////// Horizontal Product layout
      horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
      horizontalViewAllBtn = view.findViewById(R.id.horizontal_scroll_view_all_layout_btn);
      horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recyclerview);

      List<HorizontalScrollProductModel> horizontalScrollProductModelList = new ArrayList<>();
      horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.dummyimage,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.logo_square,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.logo_circle,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.add_profile_logo,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.mail,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.cart_icon_black,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.dummyimage,"Persnolized Necklace","Gold,Black,RoseGold","999"));
        horizontalScrollProductModelList.add(new HorizontalScrollProductModel(R.mipmap.heart_icon_black,"Persnolized Necklace","Gold,Black,RoseGold","999"));

      HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalScrollProductModelList);
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
      linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
      horizontalRecyclerView.setLayoutManager(linearLayoutManager);

      horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
      horizontalProductScrollAdapter.notifyDataSetChanged();


        /////// Horizontal Product layout
        ////////////////////////////////

        RecyclerView testing = view.findViewById(R.id.testing);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel>homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.bannerad,"#000000"));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.bannerad,"#ffff00"));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.mipmap.bannerad,"#ff0000"));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //////////////////////////////


        //////Grid Product Layout
       TextView gridLayoutTitle = view.findViewById(R.id.grid_product_layout_title);
       Button gridlayoutViewAllBtn = view.findViewById(R.id.grid_product_layout_view_all_btn);
        GridView gridView = view.findViewById(R.id.grid_product_layout_grid_view);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalScrollProductModelList));

        //////Grid Product Layout
        return view;
    }

    ////////// Banner Slider
private  void pageLooper(){
        if(currentPage == sliderModelList.size()-2){
            currentPage = 2;
            bannersliderViewPager.setCurrentItem(currentPage,false);
        }
    if(currentPage == 1){
        currentPage = sliderModelList.size() - 3;
        bannersliderViewPager.setCurrentItem(currentPage,false);
    }
}

private void startbannerslideshow(){
    final Handler handler = new Handler();
    final Runnable update = new Runnable() {
        @Override
        public void run() {
            if(currentPage >= sliderModelList.size()){
                currentPage = 1;
            }
            bannersliderViewPager.setCurrentItem(currentPage++,true);
        }
    };
    timer = new Timer();
    timer.schedule(new TimerTask() {
        @Override
        public void run() {
            handler.post(update);
        }
    },DELAY_TIME,PERIOD_TIME);
}

private void stopbannerslideshow(){
        timer.cancel();

}
////////// Banner Slider


}