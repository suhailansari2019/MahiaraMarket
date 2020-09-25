package com.example.mahiaramarket;

import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    List<HorizontalScrollProductModel>horizontalScrollProductModelList;

    public GridProductLayoutAdapter(List<HorizontalScrollProductModel> horizontalScrollProductModelList) {
        this.horizontalScrollProductModelList = horizontalScrollProductModelList;
    }

    @Override
    public int getCount() {
        return horizontalScrollProductModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View converView, final ViewGroup viewGroup) {
        View view ;
        if(converView == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout, null);
           view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
 view.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         Intent productDetailsIntent = new Intent(viewGroup.getContext(),ProductDetailsActivity.class);
         productDetailsIntent.putExtra("PRODUCT_ID",horizontalScrollProductModelList.get(position).getProductID());
         viewGroup.getContext().startActivity(productDetailsIntent);
     }
 });

            ImageView productImage = view.findViewById(R.id.horizontal_hs_product_image);
            TextView productTitle = view.findViewById(R.id.h_s_product_title);
            TextView productDescription = view.findViewById(R.id.h_s_product_description);
            TextView productPrice = view.findViewById(R.id.h_s_product_price);
///////use glide here//////////
            Glide.with(viewGroup.getContext()).load(horizontalScrollProductModelList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.placeholder_small)).into(productImage);
///////use glide here//////////
            productTitle.setText(horizontalScrollProductModelList.get(position).getProductTitle());
            productDescription.setText(horizontalScrollProductModelList.get(position).getProductDescription());
            productPrice.setText("Rs."+horizontalScrollProductModelList.get(position).getProductPrice()+"/-");

        }else {
       view = converView;
        }
        return view;
    }
}
