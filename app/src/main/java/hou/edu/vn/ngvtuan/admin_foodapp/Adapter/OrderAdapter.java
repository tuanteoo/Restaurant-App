package hou.edu.vn.ngvtuan.admin_foodapp.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import hou.edu.vn.ngvtuan.admin_foodapp.Model.BillModel;
import hou.edu.vn.ngvtuan.admin_foodapp.R;

public class OrderAdapter extends FirebaseRecyclerAdapter<BillModel,OrderAdapter.ViewHolder> {

    private DatabaseReference databaseReference;
    public OrderAdapter(@NonNull FirebaseRecyclerOptions<BillModel> options) {
        super(options);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position, @NonNull BillModel model) {
        holder.ibBill.setText(model.getBillKey());
        holder.dateBill.setText(model.getDateBill());
        holder.address.setText(model.getAddress());
        holder.username.setText(model.getUsername());
        holder.phonenumber.setText(model.getPhonenumber());
        holder.totalPrice.setText(model.getTotalPrice());
        holder.quantity.setText(model.getQuantity());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Bill");

        FoodBillAdapter foodBillAdapter = new FoodBillAdapter(model.getCartModels());
        holder.listFood.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),RecyclerView.HORIZONTAL,false));
        holder.listFood.setAdapter(foodBillAdapter);
        foodBillAdapter.notifyDataSetChanged();

        holder.btnCancel.setOnClickListener(view -> {
            Map<String, Object> map = new HashMap<>();

            map.put("statusBill", "Đã huỷ");
            databaseReference.child(model.getBillKey()).updateChildren(map);
        });

        holder.btnAccept.setOnClickListener(view -> {
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnCancel.setVisibility(View.GONE);
            holder.btnDelivery.setVisibility(View.VISIBLE);
        });

        holder.btnDelivery.setOnClickListener(view -> {
            Map<String, Object> map = new HashMap<>();

            map.put("statusBill", "Hoàn thành");
            databaseReference.child(model.getBillKey()).updateChildren(map);
        });
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ibBill,dateBill,address,username,phonenumber,totalPrice,quantity;
        RecyclerView listFood;
        Button btnAccept,btnCancel,btnDelivery;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ibBill = itemView.findViewById(R.id.idBill);
            dateBill = itemView.findViewById(R.id.dateofBill);
            address = itemView.findViewById(R.id.address_deli1);
            username = itemView.findViewById(R.id.order_username);
            phonenumber = itemView.findViewById(R.id.order_phonenumber);
            totalPrice = itemView.findViewById(R.id.itemBill_totalPrice);
            quantity = itemView.findViewById(R.id.item_bill_quantity);
            listFood = itemView.findViewById(R.id.rec_hor_listFoodBill);
            btnAccept = itemView.findViewById(R.id.btn_accept_Bill);
            btnCancel = itemView.findViewById(R.id.btn_cancel_bill);
            btnDelivery = itemView.findViewById(R.id.btn_delivery_order);

        }
    }
}
