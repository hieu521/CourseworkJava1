package com.example.nagivationbutton;

import android.annotation.SuppressLint;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private boolean shouldrefreshonresume = false;
    RecyclerView recyclerView;
    List<Hiking> dataList;
    CustomHikingAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    public void onStop() {
        super.onStop();
        shouldrefreshonresume = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        // check should we need to refresh the fragment
        if(shouldrefreshonresume){
            Log.d("HomeFragment", "fuckfdsafd");
            refreshData();
        }
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Khởi tạo RecyclerView ở đây
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        dbHelper = new DatabaseHelper(getActivity());

        // seeding your data
        //dbHelper.insertHikeDetails("fdsafsdf", "Sample Location", "2023-10-16", "Yes", "5 km", "Easy", "Sample description");
        dataList = dbHelper.getAllHikes();

        adapter = new CustomHikingAdapter(getActivity(), dataList, dbHelper, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @SuppressLint("Range")
    public void refreshData() {
        dataList.clear(); // Xóa dữ liệu hiện tại
        dataList = dbHelper.getAllHikes();
        adapter = new CustomHikingAdapter(getActivity(), dataList, dbHelper, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }
}