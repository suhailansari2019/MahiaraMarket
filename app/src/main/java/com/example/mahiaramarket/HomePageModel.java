package com.example.mahiaramarket;

import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLIDER = 0;
    public  static  final int STRIP_BANNER =1;
    public static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_VIEW = 3;

    private  int type;
    private String backgroundColor;

    ///////////banner slider
    private List<SliderModel> sliderModelList;
    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    ///////////banner slider

    //////////////strip Ad////////////////
    private String resource;


    public HomePageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public String  getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    //////////////strip Ad////////////////


  private  String title;
    private List<HorizontalScrollProductModel> horizontalScrollProductModelList;

    /////// Horizontal Product layout ///////////////
    private List<WishlistModel> viewAllProductList;

    public HomePageModel(int type, String title,String backgroundColor, List<HorizontalScrollProductModel> horizontalScrollProductModelList,List<WishlistModel>viewAllProductList) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
        this.viewAllProductList = viewAllProductList;
    }

    public List<WishlistModel> getViewAllProductList() {
        return viewAllProductList;
    }




    public void setViewAllProductList(List<WishlistModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }
    /////// Horizontal Product layout/////////////////////


    /////// Grid Product layout ///////////////
    public HomePageModel(int type, String title, String backgroundColor, List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    /////// Grid Product layout ///////////////
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<HorizontalScrollProductModel> getHorizontalScrollProductModelList() {
        return horizontalScrollProductModelList;
    }
    public void setHorizontalScrollProductModelList(List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }


}
