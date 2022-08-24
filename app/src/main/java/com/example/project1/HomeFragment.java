package com.example.project1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class HomeFragment extends Fragment implements recyclerInterface{

    List<property_model_class> home_data_list;
    RecyclerView home_recyclerview;
    LinearLayoutManager home_reycler_manager;
    property_adapter home_recycler_adapter;
    private HomeFragmentViewModel homeFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        homeFragmentViewModel.initHomeFragmentViewModel(this);
        homeFragmentViewModel.get_home_data_list_vm().observe( getActivity(), new Observer<List<property_model_class>>() {
            @Override
            public void onChanged(List<property_model_class> property_model_classes) {
                home_recycler_adapter.notifyDataSetChanged();
            }
        });
        init_home_recycler();
    }

    void init_home_recycler() {
        if(getView()!=null) {
            home_recyclerview = getView().findViewById(R.id.home_fragment_rv);
            home_reycler_manager = new LinearLayoutManager(getContext());
            home_reycler_manager.setOrientation(RecyclerView.VERTICAL);
            home_recyclerview.setLayoutManager(home_reycler_manager);
            home_recycler_adapter = new property_adapter(homeFragmentViewModel.get_home_data_list_vm().getValue(), getContext(), this);
            home_recyclerview.setAdapter(home_recycler_adapter);
            home_recycler_adapter.notifyDataSetChanged();
        }
    }

    private void init_home_data() {
//        home_data_list = homeFragmentViewModel.get_home_data_list_vm().getValue();
//        init_home_recycler();
//        home_data_list = new ArrayList<>();
//        FirebaseDatabase.getInstance().getReference("property added by all users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                home_data_list.clear();
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
//                    property_model_class property_model_data = dataSnapshot.getValue(property_model_class.class);
//                    home_data_list.add(property_model_data);
//
//                }
//                init_home_recycler();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    public void onItemClick(int position) {

        return;
    }

}