package com.example.mahiaramarket;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel>cartItemModelList;
    private int lastPosition = -1;
    private TextView cartTotalAmount;
    private boolean showDeleteBtn;

    public CartAdapter(List<CartItemModel> cartItemModelList,TextView cartTotalAmount,boolean showDeleteBtn) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;
        this.showDeleteBtn = showDeleteBtn;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()){
            case 0:
                return CartItemModel.CART_ITEM;

            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       switch (viewType){
           case CartItemModel.CART_ITEM:
               View cartItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
               return new cartItemViewHolder(cartItemView);
           case CartItemModel.TOTAL_AMOUNT:
               View cartTotalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
               return new cartTotalAmountViewHolder(cartTotalView);
               default:
               return null;
       }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()){
            case CartItemModel.CART_ITEM:
                String productID = cartItemModelList.get(position).getProductID();
                String resource = cartItemModelList.get(position).getProductImage();
                String title = cartItemModelList.get(position).getProductTitle();
                Long freeCoupens = cartItemModelList.get(position).getFreeCoupens();
                String productPrice = cartItemModelList.get(position).getProductPrice();
                String cuttedPrice = cartItemModelList.get(position).getProductCuttedPrice();
                Long offerApplied = cartItemModelList.get(position).getOffersApplied();
                boolean inStock = cartItemModelList.get(position).isInStock();
                Long productQuantity = cartItemModelList.get(position).getProductQuantity();
                Long maxQuantity = cartItemModelList.get(position).getProductMaxQuantity();
                boolean qtyError = cartItemModelList.get(position).isQtyError();
                List<String> qtyIds = cartItemModelList.get(position).getQtyIDs();
                long stockQty = cartItemModelList.get(position).getStockQuantity();
                boolean COD = cartItemModelList.get(position).isCOD();

                ((cartItemViewHolder)holder).setItemDetails(productID,resource,title,freeCoupens,productPrice,cuttedPrice,offerApplied,position,inStock,String.valueOf(productQuantity),maxQuantity,qtyError,qtyIds,stockQty,COD);
                break;
                case CartItemModel.TOTAL_AMOUNT:
                    int totalItems = 0;
                    int totalItemPrice = 0;
                    String deliveryPrice;
                    int totalAmount;
                    int savedAmount = 0;
                    for(int x= 0;x<cartItemModelList.size();x++){
                        if(cartItemModelList.get(x).getType()== CartItemModel.CART_ITEM && cartItemModelList.get(x).isInStock()){
                            int quantity = Integer.parseInt(String.valueOf(cartItemModelList.get(x).getProductQuantity()));
                            totalItems = totalItems + quantity;
                            if(TextUtils.isEmpty(cartItemModelList.get(x).getSelectedCoupenId())) {
                                //////price total here in cart view/////////
                                totalItemPrice = totalItemPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice())*quantity;
                                //////price total here in cart view/////////
                            }else {
                                totalItemPrice = totalItemPrice + Integer.parseInt(cartItemModelList.get(x).getDiscountedPrice())*quantity;
                            }
                            if(!TextUtils.isEmpty(cartItemModelList.get(x).getProductCuttedPrice())){
                                savedAmount = savedAmount + (Integer.parseInt(cartItemModelList.get(x).getProductCuttedPrice()) - Integer.parseInt(cartItemModelList.get(x).getProductPrice())) * quantity;
                                if(!TextUtils.isEmpty(cartItemModelList.get(x).getSelectedCoupenId())) {
                                    savedAmount = savedAmount + (Integer.parseInt(cartItemModelList.get(x).getProductPrice()) - Integer.parseInt(cartItemModelList.get(x).getDiscountedPrice())) * quantity;

                                }
                            }else {
                                if(!TextUtils.isEmpty(cartItemModelList.get(x).getSelectedCoupenId())) {
                                    savedAmount = savedAmount + (Integer.parseInt(cartItemModelList.get(x).getProductPrice()) - Integer.parseInt(cartItemModelList.get(x).getDiscountedPrice())) * quantity;
                                }
                            }

                        }
                    }
                    if(totalItemPrice > 500){
                        deliveryPrice = "Free";
                        totalAmount = totalItemPrice;
                    }else{
                        deliveryPrice = "50";
                        totalAmount = totalItemPrice + 50 ;
                    }

                    cartItemModelList.get(position).setTotalItems(totalItems);
                    cartItemModelList.get(position).setTotalItemPrice(totalItemPrice);
                    cartItemModelList.get(position).setDeliveryPrice(deliveryPrice);
                    cartItemModelList.get(position).setTotalAmount(totalAmount);
                    cartItemModelList.get(position).setSavedAmount(savedAmount);
                    ((cartTotalAmountViewHolder)holder).setTotalAmount(totalItems,totalItemPrice,deliveryPrice,totalAmount,savedAmount);
                  break;
            default:
                return;
        }
        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }
    class cartItemViewHolder extends RecyclerView.ViewHolder{
          private ImageView productImage;
          private TextView productTitle;
private ImageView freeCoupenIcon;
        private TextView freeCoupens;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView coupensApplied;
        private TextView productQuantity;
        private LinearLayout coupenRedemptionLayout;
        private TextView coupenRedemptionBody;

        private LinearLayout deleteBtn;
        private Button redeemBtn;
        private ImageView codIndicator;

        /////////coupenDialog//////////////
        private TextView coupenTitle;
        private TextView coupenExpiryDate;
        private TextView coupenBody;
        private RecyclerView coupensRecyclerView;
        private LinearLayout selectedCoupen;
        private TextView discountedPrice;
        private TextView originalPrice;
        private LinearLayout applyORremoveBtnContainer;
        private TextView footerText;
        private Button removeCoupenBtn,applyCoupenBtn;
        private String productOriginalPrice;


        /////////coupenDialog//////////////


        public cartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupenIcon = itemView.findViewById(R.id.free_coupen_icon);
            freeCoupens = itemView.findViewById(R.id.tv_free_coupen);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            offersApplied = itemView.findViewById(R.id.offers_applied);
            coupensApplied = itemView.findViewById(R.id.coupens_applied);
            productQuantity = itemView.findViewById(R.id.product_quanitity);
            coupenRedemptionLayout = itemView.findViewById(R.id.coupen_redemption_layout);
            coupenRedemptionBody = itemView.findViewById(R.id.tv_coupen_redeemption);
            codIndicator = itemView.findViewById(R.id.cod_indicator);

            redeemBtn  = itemView.findViewById(R.id.coupen_redeepmtion_btn);
            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
        }
        private   void setItemDetails(final String productID, String resource, String title, Long freeCoupensNo, final String productPriceText, String cuttedPriceText, Long offersAppliedNo, final int position, boolean inStock, final String quantity, final Long maxQuantity, boolean qtyError, final List<String> qtyIds, final long stockQty,boolean COD){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.placeholder_small)).into(productImage);
            productTitle.setText(title);

            final Dialog checkCoupenPriceDialog = new Dialog(itemView.getContext());
            checkCoupenPriceDialog.setContentView(R.layout.coupen_redeem_dialog);
            checkCoupenPriceDialog.setCancelable(false);
            checkCoupenPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if(COD){
                codIndicator.setVisibility(View.VISIBLE);
            }else{
                codIndicator.setVisibility(View.INVISIBLE);
            }

            if(inStock) {
                if(freeCoupensNo>0){
                    freeCoupenIcon.setVisibility(View.VISIBLE);
                    freeCoupens.setVisibility(View.VISIBLE);
                    if(freeCoupensNo == 1) {

                        freeCoupens.setText("free" + freeCoupensNo + " Coupen");
                    }else{
                        freeCoupens.setText("free" + freeCoupensNo + " Coupens");
                    }
                }else{
                    freeCoupenIcon.setVisibility(View.INVISIBLE);
                    freeCoupens.setVisibility(View.INVISIBLE);
                }

                productPrice.setText("Rs."+productPriceText+"/-");
                productPrice.setTextColor(Color.parseColor("#000000"));
                cuttedPrice.setText("Rs."+cuttedPriceText+"/-");
                coupenRedemptionLayout.setVisibility(View.VISIBLE);

                ///////Coupen Dialog /////////////

                ImageView toggleRecyclerView = checkCoupenPriceDialog.findViewById(R.id.toggle_recyclerView);
                coupensRecyclerView = checkCoupenPriceDialog.findViewById(R.id.coupen_recyclerView);
                selectedCoupen = checkCoupenPriceDialog.findViewById(R.id.selected_coupen);
                coupenTitle = checkCoupenPriceDialog.findViewById(R.id.coupen_title);
                coupenExpiryDate = checkCoupenPriceDialog.findViewById(R.id.coupen_validity);
                coupenBody = checkCoupenPriceDialog.findViewById(R.id.coupen_body);
                footerText = checkCoupenPriceDialog.findViewById(R.id.footer_text);
                applyORremoveBtnContainer = checkCoupenPriceDialog.findViewById(R.id.apply_or_remove_btns_container);
                removeCoupenBtn = checkCoupenPriceDialog.findViewById(R.id.remove_btn);
                applyCoupenBtn = checkCoupenPriceDialog.findViewById(R.id.apply_btn);


                footerText.setVisibility(View.GONE);
                applyORremoveBtnContainer.setVisibility(View.VISIBLE);

                originalPrice = checkCoupenPriceDialog.findViewById(R.id.original_price);
                discountedPrice = checkCoupenPriceDialog.findViewById(R.id.dicounted_price);

                LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                coupensRecyclerView.setLayoutManager(layoutManager);

                /////for coupen dialog/////
                originalPrice.setText(productPrice.getText());
                productOriginalPrice = productPriceText;
                MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(position,DBqueries.rewardModelList, true,coupensRecyclerView,selectedCoupen,productOriginalPrice,coupenTitle,coupenExpiryDate,coupenBody,discountedPrice,cartItemModelList);
                coupensRecyclerView.setAdapter(myRewardsAdapter);
                myRewardsAdapter.notifyDataSetChanged();

                applyCoupenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!TextUtils.isEmpty(cartItemModelList.get(position).getSelectedCoupenId())) {
                            for (RewardModel rewardModel : DBqueries.rewardModelList) {
                                if (rewardModel.getCoupenId().equals(cartItemModelList.get(position).getSelectedCoupenId())) {
                                    rewardModel.setAlreadyUsed(true);


                                    coupenRedemptionLayout.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.reward_gradient_background));
                                    coupenRedemptionBody.setText(rewardModel.getCoupenBody());
                                    redeemBtn.setText("Coupen");

                                }
                            }
                            coupensApplied.setVisibility(View.VISIBLE);

                            cartItemModelList.get(position).setDiscountedPrice(discountedPrice.getText().toString().substring( 4,discountedPrice.getText().length() - 2));
                            productPrice.setText(discountedPrice.getText());
                            String offerDiscountedAmt = String.valueOf(Long.valueOf(productPriceText)- Long.valueOf(discountedPrice.getText().toString().substring( 4,discountedPrice.getText().length() - 2)));
                            coupensApplied.setText("Coupen applied -Rs. "+offerDiscountedAmt+"/-");
                            notifyItemChanged(cartItemModelList.size() - 1);
                            checkCoupenPriceDialog.dismiss();
                        }

                    }
                });

                removeCoupenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(RewardModel rewardModel : DBqueries.rewardModelList){
                            if(rewardModel.getCoupenId().equals(cartItemModelList.get(position).getSelectedCoupenId())){
                                rewardModel.setAlreadyUsed(false);
                            }
                        }
                        coupenTitle.setText("Coupen");
                        coupenExpiryDate.setText("validity");
                        coupenBody.setText("Tap the Icon on the top right corner to select your coupen.");
                        coupensApplied.setVisibility(View.INVISIBLE);
                        coupenRedemptionLayout.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.coupenRed));
                        coupenRedemptionBody.setText("Apply Your Coupen Here");
                        redeemBtn.setText("Redeem");
                        cartItemModelList.get(position).setSelectedCoupenId(null);
                        productPrice.setText("Rs." + productPriceText + "/-");
                        notifyItemChanged(cartItemModelList.size() - 1);
                        checkCoupenPriceDialog.dismiss();

                    }
                });
                /////for coupen dialog/////

                toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialogRecyclerView();
                    }
                });



                if(!TextUtils.isEmpty(cartItemModelList.get(position).getSelectedCoupenId())) {
                    for (RewardModel rewardModel : DBqueries.rewardModelList) {
                        if (rewardModel.getCoupenId().equals(cartItemModelList.get(position).getSelectedCoupenId())) {
                            coupenRedemptionLayout.setBackground(itemView.getContext().getResources().getDrawable(R.drawable.reward_gradient_background));
                            coupenRedemptionBody.setText(rewardModel.getCoupenBody());
                            redeemBtn.setText("Coupen");

                coupenBody.setText(rewardModel.getCoupenBody());
                            if(rewardModel.getType().equals("Discount")){
                                coupenTitle.setText(rewardModel.getType());
                            }else {
                                coupenTitle.setText("Flate Rs. "+rewardModel.getdiscORamt()+" OFF");
                            }
                            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM YYYY");
                            coupenExpiryDate.setText("till " +simpleDateFormat.format(rewardModel.getTimestamp()));
                        }
                    }

                    discountedPrice.setText("Rs."+cartItemModelList.get(position).getDiscountedPrice()+"/-");
                    coupensApplied.setVisibility(View.VISIBLE);
                    productPrice.setText("Rs."+cartItemModelList.get(position).getDiscountedPrice()+"/-");
                    String offerDiscountedAmt = String.valueOf(Long.valueOf(productPriceText)- Long.valueOf(cartItemModelList.get(position).getDiscountedPrice()));
                    coupensApplied.setText("Coupen applied -Rs. "+offerDiscountedAmt+"/-");
                }else{
                    coupensApplied.setVisibility(View.INVISIBLE);
                    coupenRedemptionLayout.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.coupenRed));
                    coupenRedemptionBody.setText("Apply Your Coupen Here");
                    redeemBtn.setText("Redeem");
                }
                //////////coupen Dialog end/////////

                productQuantity.setText("Qty:" + quantity);
                if(!showDeleteBtn) {
                    if (qtyError) {
                        productQuantity.setTextColor(itemView.getContext().getResources().getColor(R.color.btnRed));
                        productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.btnRed)));

                    } else {
                        productQuantity.setTextColor(itemView.getContext().getResources().getColor(android.R.color.black));
                        productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(android.R.color.black)));
                    }
                }

                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog quantityDialog = new Dialog(itemView.getContext());
                        quantityDialog.setContentView(R.layout.quantity_dialog);
                        quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        quantityDialog.setCancelable(false);

                        final EditText quantityNo = quantityDialog.findViewById(R.id.quantity_number);
                        Button cancelBtn = quantityDialog.findViewById(R.id.cancel_btn);
                        Button okBtn = quantityDialog.findViewById(R.id.ok_btn);
                        quantityNo.setHint("Max "+ String.valueOf(maxQuantity));

                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                quantityDialog.dismiss();
                            }
                        });
                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(!TextUtils.isEmpty(quantityNo.getText())) {
                                    if (Long.valueOf(quantityNo.getText().toString()) <= maxQuantity && Long.valueOf(quantityNo.getText().toString()) != 0) {
                                        if(itemView.getContext() instanceof HomeActivity2){
                                            cartItemModelList.get(position).setProductQuantity(Long.valueOf(quantityNo.getText().toString()));
                                        }else {

                                            if (DeliveryActivity.fromCart) {
                                                cartItemModelList.get(position).setProductQuantity(Long.valueOf(quantityNo.getText().toString()));
                                            } else {
                                                DeliveryActivity.cartItemModelList.get(position).setProductQuantity(Long.valueOf(quantityNo.getText().toString()));
                                            }
                                        }
                                        productQuantity.setText("Qty:" + quantityNo.getText());
                                        notifyItemChanged(cartItemModelList.size() - 1);


                                        if(!showDeleteBtn){
                                            DeliveryActivity.loadingDialog.show();
                                            DeliveryActivity.cartItemModelList.get(position).setQtyError(false);
                                            final int initialQty = Integer.parseInt(quantity);
                                            final int finalQty = Integer.parseInt(quantityNo.getText().toString());
                                            final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                                            if(finalQty > initialQty) {

                                                for (int y = 0; y < finalQty - initialQty; y++) {
                                                    final String quantityDocumentName = UUID.randomUUID().toString().substring(0, 20);

                                                    Map<String, Object> timestamp = new HashMap<>();
                                                    timestamp.put("time", FieldValue.serverTimestamp());
                                                    final int finalY = y;
                                                    firebaseFirestore.collection("PRODUCTS").document(productID).collection("QUANTITY").document(quantityDocumentName).set(timestamp)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    qtyIds.add(quantityDocumentName);
                                                                    if (finalY + 1 == finalQty - initialQty) {
                                                                        firebaseFirestore.collection("PRODUCTS").document(productID).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).limit(stockQty).get()
                                                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            List<String> serverQuantity = new ArrayList<>();
                                                                                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                                                                                serverQuantity.add(queryDocumentSnapshot.getId());

                                                                                            }
                                                                                            long availableQty = 0;
                                                                                            for (String qtyId : qtyIds) {
                                                                                                if (!serverQuantity.contains(qtyId)) {

                                                                                                        DeliveryActivity.cartItemModelList.get(position).setQtyError(true);
                                                                                                        DeliveryActivity.cartItemModelList.get(position).setProductMaxQuantity(availableQty);
                                                                                                        Toast.makeText(itemView.getContext(), "Sorry! All Products May not be Available in required quantity", Toast.LENGTH_SHORT).show();


                                                                                                } else {
                                                                                                    availableQty++;
                                                                                                }
                                                                                            }
                                                                                            DeliveryActivity.cartAdapter.notifyDataSetChanged();
                                                                                        } else {
                                                                                            String error = task.getException().getMessage();
                                                                                            Toast.makeText(itemView.getContext(), error, Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        DeliveryActivity.loadingDialog.dismiss();
                                                                                    }
                                                                                });
                                                                    }
                                                                }
                                                            });
                                                }
                                            }else if(initialQty > finalQty) {
                                                for (int x= 0;x<initialQty - finalQty;x++) {
                                                    final String qtyId = qtyIds.get(qtyIds.size()- 1 -x);

                                                    final int finalX = x;
                                                    firebaseFirestore.collection("PRODUCTS").document(productID).collection("QUANTITY").document(qtyId).delete()
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                   qtyIds.remove(qtyId);
                                                                    DeliveryActivity.cartAdapter.notifyDataSetChanged();
                                                                    if(finalX+1 == initialQty - finalQty){
                                                                        DeliveryActivity.loadingDialog.dismiss();
                                                                    }
                                                                }
                                                            });
                                                }
                                            }

                                        }

                                    }else {
                                    Toast.makeText(itemView.getContext(), "Max quantity:"+maxQuantity.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                                quantityDialog.dismiss();
                    }
                        });
                        quantityDialog.show();
                    }
                });
                if(offersAppliedNo > 0){
                    offersApplied.setVisibility(View.VISIBLE);
                    String offerDiscountedAmt = String.valueOf(Long.valueOf(cuttedPriceText)- Long.valueOf(productPriceText));
                    offersApplied.setText("offer applied - Rs. "+offerDiscountedAmt+ " /-");
                }else{
                    offersApplied.setVisibility(View.INVISIBLE);
                }


            }else {
                productPrice.setText("OUT OF STOCK");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.btnRed));
                cuttedPrice.setText("");
                coupenRedemptionLayout.setVisibility(View.GONE);
                freeCoupens.setVisibility(View.INVISIBLE);
                productQuantity.setVisibility(View.INVISIBLE);
                coupensApplied.setVisibility(View.GONE);
                offersApplied.setVisibility(View.GONE);
                freeCoupenIcon.setVisibility(View.INVISIBLE);
//                productQuantity.setText("Qty:" + 0);
//                productQuantity.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#70000000")));
//                productQuantity.setTextColor(Color.parseColor("#70000000"));
//                productQuantity.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#70000000")));
            }

            if(showDeleteBtn){
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);
            }


            redeemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(RewardModel rewardModel : DBqueries.rewardModelList){
                        if(rewardModel.getCoupenId().equals(cartItemModelList.get(position).getSelectedCoupenId())){
                            rewardModel.setAlreadyUsed(false);
                        }
                    }
                    checkCoupenPriceDialog.show();
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!TextUtils.isEmpty(cartItemModelList.get(position).getSelectedCoupenId())) {
                        for(RewardModel rewardModel : DBqueries.rewardModelList){
                            if(rewardModel.getCoupenId().equals(cartItemModelList.get(position).getSelectedCoupenId())){
                                rewardModel.setAlreadyUsed(false);
                            }
                        }
                    }

                    if(!ProductDetailsActivity.running_cart_query){
                        ProductDetailsActivity.running_cart_query = true;
                        DBqueries.removeFromCart(position,itemView.getContext(),cartTotalAmount);
                    }
                }
            });

        }
        private void showDialogRecyclerView() {
            if (coupensRecyclerView.getVisibility() == View.GONE) {
                coupensRecyclerView.setVisibility(View.VISIBLE);
                selectedCoupen.setVisibility(View.GONE);
            } else {
                coupensRecyclerView.setVisibility(View.GONE);
                selectedCoupen.setVisibility(View.VISIBLE);
            }
        }
    }
    class cartTotalAmountViewHolder extends RecyclerView.ViewHolder{

        private TextView totalItems;
        private TextView totalItemsPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public cartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemsPrice = itemView.findViewById(R.id.total_items_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.saved_amount);
        }
        private void setTotalAmount(int totalItemText,int totalItemPriceText,String deliveryPriceText,int totalAmountText,int savedAmountText){
            totalItems.setText("Price("+totalItemText+")items)");
            totalItemsPrice.setText("Rs."+totalItemPriceText+"/-");
            if(deliveryPriceText.equals("Free")) {
                deliveryPrice.setText(deliveryPriceText);
            }else{
                deliveryPrice.setText("Rs."+deliveryPriceText+"/-");
            }
            totalAmount.setText("Rs."+totalAmountText+"/-");
            cartTotalAmount.setText("Rs."+totalAmountText+"/-");
            savedAmount.setText("You Saved Rs."+savedAmountText+"/- on This order");

            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
            if(totalItemPriceText == 0){
                if(DeliveryActivity.fromCart) {
                    cartItemModelList.remove(cartItemModelList.size() - 1);
                    DeliveryActivity.cartItemModelList.remove(cartItemModelList.size() - 1);
                }
                if(showDeleteBtn){
                    cartItemModelList.remove(cartItemModelList.size() - 1);
                }
               parent.setVisibility(View.GONE);
            }else {
                parent.setVisibility(View.VISIBLE);
            }
        }
    }
}
