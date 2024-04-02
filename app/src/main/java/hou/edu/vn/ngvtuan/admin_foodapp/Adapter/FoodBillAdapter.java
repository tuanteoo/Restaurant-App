package hou.edu.vn.ngvtuan.admin_foodapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hou.edu.vn.ngvtuan.admin_foodapp.Model.CartModel;
import hou.edu.vn.ngvtuan.admin_foodapp.R;

public class FoodBillAdapter extends RecyclerView.Adapter<FoodBillAdapter.ViewHolder> {

    private final ArrayList<CartModel> cartModels;

    public FoodBillAdapter(ArrayList<CartModel> cartModels){
        this.cartModels = cartModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_food,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(cartModels.get(position).getImageFood())
                .into(holder.imageFood);
        holder.nameFood.setText(cartModels.get(position).getNameFood());
    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFood;
        TextView nameFood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageFood = itemView.findViewById(R.id.item_imageFood);
            nameFood = itemView.findViewById(R.id.item_nameFood);
        }
    }
}
