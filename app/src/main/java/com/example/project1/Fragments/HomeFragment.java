package com.example.project1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.HomeFragmentViewModel;
import com.example.project1.Property_model_class;
import com.example.project1.R;
import com.example.project1.RecyclerAdapters.property_adapter;
import com.example.project1.ListenerInterfaces.recyclerInterface;
import com.squareup.picasso.Picasso;

import java.util.List;


public class HomeFragment extends Fragment implements recyclerInterface {

    List<Property_model_class> home_data_list;
    RecyclerView home_recyclerview;
    LinearLayoutManager home_reycler_manager;
    property_adapter home_recycler_adapter;
    private HomeFragmentViewModel homeFragmentViewModel;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getView());
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        homeFragmentViewModel.initHomeFragmentViewModel();
        init_home_recycler();
        homeFragmentViewModel.get_home_data_list_vm().observe( getActivity(), new Observer<List<Property_model_class>>() {
            @Override
            public void onChanged(List<Property_model_class> Property_model_classes) {
                home_recycler_adapter.notifyDataSetChanged();
            }
        });

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
//                    Property_model_class property_model_data = dataSnapshot.getValue(Property_model_class.class);
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
        Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
        List<Property_model_class> data = homeFragmentViewModel.get_home_data_list_vm().getValue();
//        NavDirections action = HomeFragmentDirections.actionHomeFragmentToShowLocationFragment(data.get(position).getLat(),data.get(position).getLng());
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToMapsFragment(data.get(position).getLat(),data.get(position).getLng(),"HomeFragment");

        navController.navigate(action);
    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView property_image, String URL){
        Picasso.get().load(URL).into(property_image);
    }

}