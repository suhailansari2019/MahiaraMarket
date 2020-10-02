package com.example.mahiaramarket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.mahiaramarket.DeliveryActivity.SELECT_ADDRESS;
import static com.example.mahiaramarket.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.mahiaramarket.MyAddressesActivity.refreshItem;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {
    private List<AddressesModel> addressesModelList;
    private int MODE;
    private int preSelectedPosition;


    public AddressesAdapter(List<AddressesModel> addressesModelList, int MODE) {
        this.addressesModelList = addressesModelList;
        this.MODE = MODE;
        preSelectedPosition = DBqueries.selectedAddress;
    }

    @NonNull
    @Override
    public AddressesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.ViewHolder holder, int position) {

          String name = addressesModelList.get(position).getFullname();
        String mobileNo = addressesModelList.get(position).getMobileNo();
          String address = addressesModelList.get(position).getAddress();
        String pincode = addressesModelList.get(position).getPincode();
        Boolean selected = addressesModelList.get(position).getSelected();
        holder.setData(name,address,pincode,selected,position,mobileNo);
    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fullname;
        private TextView address;
        private TextView pincode;
        private ImageView icon;
        private LinearLayout optionContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pincode);
            optionContainer = itemView.findViewById(R.id.option_container);
icon = itemView.findViewById(R.id.icon_view);
        }
        private void setData(String username, String useraddress, String userpincode, Boolean selected, final int postition,String mobileNo){
            fullname.setText(username+" - "+mobileNo);
            address.setText(useraddress);
            pincode.setText(userpincode);
            if(MODE == SELECT_ADDRESS){
                icon.setImageResource(R.mipmap.check);
                if(selected){
                    icon.setVisibility(View.VISIBLE);
                    preSelectedPosition = postition;
                }else {
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(preSelectedPosition != postition) {
                            addressesModelList.get(postition).setSelected(true);
                            addressesModelList.get(preSelectedPosition).setSelected(false);
                            refreshItem(preSelectedPosition, postition);
                            preSelectedPosition = postition;
                            DBqueries.selectedAddress = postition;
                        }
                    }
                });

            }else if(MODE == MANAGE_ADDRESS){
                 optionContainer.setVisibility(View.GONE);
                 icon.setImageResource(R.mipmap.vertical_dot);
                 icon.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         optionContainer.setVisibility(View.VISIBLE);
                         refreshItem(preSelectedPosition,preSelectedPosition);
                         preSelectedPosition = postition;
                     }
                 });
                 itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         refreshItem(preSelectedPosition,preSelectedPosition);
                         preSelectedPosition = -1;
                     }
                 });
            }

        }
    }
}
