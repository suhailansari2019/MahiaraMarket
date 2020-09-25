package com.example.mahiaramarket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRewardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRewardsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyRewardsFragment() {
        // Required empty public constructor
    }
    private RecyclerView rewardsRecyclerView;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyRewardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyRewardsFragment newInstance(String param1, String param2) {
        MyRewardsFragment fragment = new MyRewardsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rewards, container, false);
        rewardsRecyclerView = view.findViewById(R.id.my_rewards_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardsRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel>rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("cashback","till 25th sep 2020","Get 10% back with credit or Debit card payments "));
        rewardModelList.add(new RewardModel("Disscount","till 22th sep 2020","Get 10% Discount on Selected Necklace "));
        rewardModelList.add(new RewardModel("Buy Two at one price","till 20th sep 2020","Buy one get one free at one price "));
        rewardModelList.add(new RewardModel("Bank offer","till 30th sep 2020","Get 10% back with ICICI/SBI credit or Debit card payments. MAX-Cart Value more than Rs.5000/- "));
        rewardModelList.add(new RewardModel("cashback","till 25th sep 2020","Get 10% back with credit or Debit card payments "));
        rewardModelList.add(new RewardModel("Disscount","till 22th sep 2020","Get 10% Discount on Selected Necklace "));
        rewardModelList.add(new RewardModel("Buy Two at one price","till 20th sep 2020","Buy one get one free at one price "));
        rewardModelList.add(new RewardModel("Bank offer","till 30th sep 2020","Get 10% back with ICICI/SBI credit or Debit card payments. MAX-Cart Value more than Rs.5000/- "));
        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList,false);
        rewardsRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        return view;

    }
}