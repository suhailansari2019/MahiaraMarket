package com.example.mahiaramarket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.ViewHolder> {
private List<RewardModel>rewardModelList;
private Boolean useMiniLayout =false;



    public MyRewardsAdapter(List<RewardModel> rewardModelList,Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(useMiniLayout){
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_rewards_item_layout, parent, false);
        }else {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rwards_item_layout, parent, false);
        }
            return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          String type = rewardModelList.get(position).getType();
        Date validity = rewardModelList.get(position).getTimestamp();
          String body = rewardModelList.get(position).getCoupenBody();
          String lowerLimit = rewardModelList.get(position).getLowerLimit();
        String upperLimit = rewardModelList.get(position).getUpperLimit();
        String discORamt = rewardModelList.get(position).getdiscORamt();

          holder.setData(type,validity,body,upperLimit,lowerLimit,discORamt);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView coupenTitle;
        private TextView coupenExpiryDate;
        private TextView coupenBody;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coupenTitle = itemView.findViewById(R.id.coupen_title);
            coupenExpiryDate = itemView.findViewById(R.id.coupen_validity);
            coupenBody = itemView.findViewById(R.id.coupen_body);
        }
        private void setData(final String type, final Date validity, final String body,String upperLimit,String lowerLimit,String discORamt){
           if(type.equals("Discount")){
               coupenTitle.setText(type);
           }else {
               coupenTitle.setText("Flate Rs. "+discORamt+" OFF");
           }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM YYYY");
           coupenExpiryDate.setText("till " +simpleDateFormat.format(validity));

           coupenBody.setText(body);

            if(useMiniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProductDetailsActivity.coupenTitle.setText(type);
                        ProductDetailsActivity.coupenExpiryDate.setText(simpleDateFormat.format(validity));
                        ProductDetailsActivity.coupenBody.setText(body);
                        ProductDetailsActivity.showDialogRecyclerView();
                    }
                });
            }
        }
    }
}
