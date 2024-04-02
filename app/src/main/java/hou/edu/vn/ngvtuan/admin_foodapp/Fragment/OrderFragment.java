package hou.edu.vn.ngvtuan.admin_foodapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import hou.edu.vn.ngvtuan.admin_foodapp.Adapter.OrderAdapter;
import hou.edu.vn.ngvtuan.admin_foodapp.Model.BillModel;
import hou.edu.vn.ngvtuan.admin_foodapp.Model.FoodModel;
import hou.edu.vn.ngvtuan.admin_foodapp.R;

public class OrderFragment extends Fragment {

    RecyclerView listOrder;
    OrderAdapter orderAdapter;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        listOrder = view.findViewById(R.id.rec_order);
        listOrder.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Bill");

        FirebaseRecyclerOptions<BillModel> options =
                new FirebaseRecyclerOptions.Builder<BillModel>()
                        .setQuery(databaseReference.orderByChild("statusBill").equalTo("Coming"),BillModel.class)
                        .build();
        orderAdapter = new OrderAdapter(options);
        listOrder.setAdapter(orderAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        orderAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        orderAdapter.stopListening();
    }
}