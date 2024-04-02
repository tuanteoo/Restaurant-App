package hou.edu.vn.ngvtuan.admin_foodapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import hou.edu.vn.ngvtuan.admin_foodapp.Model.FoodModel;
import hou.edu.vn.ngvtuan.admin_foodapp.R;

public class FoodAdapter extends FirebaseRecyclerAdapter<FoodModel,FoodAdapter.ViewHolder> {

    public FoodAdapter(@NonNull FirebaseRecyclerOptions<FoodModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull FoodModel foodModel) {
        holder.nameFood.setText(foodModel.getNameFood());

        Glide.with(holder.itemView.getContext())
                .load(foodModel.getImageFood())
                .into(holder.imageFood);

        holder.imageFood.setOnClickListener(view -> {
            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imageFood.getContext())
                    .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.item_fooddetail))
                    .setExpanded(true,1000)
                    .create();

            View view1 = dialogPlus.getHolderView();

            Button btnUpdate = view1.findViewById(R.id.item_btnUpdate);
            ImageView imageFood =view1.findViewById(R.id.item_imageFood);
            EditText nameFood = view1.findViewById(R.id.item_nameFood);
            EditText priceFood = view1.findViewById(R.id.item_priceFood);
            EditText descFood = view1.findViewById(R.id.item_descFood);
            Spinner typeFood = view1.findViewById(R.id.item_typeFood);
            EditText timeCooking = view1.findViewById(R.id.item_timeCooking);

            String[] values = {"Pizza", "Hamburger", "Fries", "Ice Cream", "Sandwich"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, values);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            typeFood.setAdapter(adapter);

            nameFood.setText(foodModel.getNameFood());
            priceFood.setText(String.valueOf(foodModel.getPriceFood()));
            descFood.setText(foodModel.getDescFood());
            timeCooking.setText(foodModel.getTimeCooking());
            Glide.with(holder.itemView.getContext())
                    .load(foodModel.getImageFood())
                    .into(imageFood);

            int selectedIndex = Arrays.asList(values).indexOf(foodModel.getTypeFood());
            typeFood.setSelection(selectedIndex);

            dialogPlus.show();

            //imageFood.setOnClickListener(view2 -> OpenChooseImage());

            btnUpdate.setOnClickListener(view2 -> {
                Map<String,Object> map = new HashMap<>();

                map.put("nameFood",nameFood.getText().toString());
                map.put("priceFood",Integer.parseInt(priceFood.getText().toString()));
                map.put("descFood",descFood.getText().toString());
                map.put("typeFood",typeFood.getSelectedItem().toString());
                map.put("timeCooking",timeCooking.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("Food")
                        .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(holder.nameFood.getContext(), "Food updated successfully", Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(holder.nameFood.getContext(), "Error while Updating", Toast.LENGTH_SHORT).show();
                            dialogPlus.dismiss();
                        });
            });
        });
    }

//    private void OpenChooseImage() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);

        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageFood;
        TextView nameFood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageFood = itemView.findViewById(R.id.cart_imgFood);
            nameFood = itemView.findViewById(R.id.cart_nameFood);
        }
    }
}
