package hou.edu.vn.ngvtuan.admin_foodapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import hou.edu.vn.ngvtuan.admin_foodapp.Adapter.FoodAdapter;
import hou.edu.vn.ngvtuan.admin_foodapp.Model.FoodModel;
import hou.edu.vn.ngvtuan.admin_foodapp.R;

public class FoodFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    public FoodFragment() {
        // Required empty public constructor
    }
    ImageView img_food;
    Button btnAddFood;
    EditText nameFood,priceFood,descFood,timeCook;

    @SuppressLint({"WrongThread", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        img_food = view.findViewById(R.id.image_food);
        btnAddFood = view.findViewById(R.id.btn_AddFood);
        nameFood = view.findViewById(R.id.name_food);
        priceFood = view.findViewById(R.id.price_food);
        descFood = view.findViewById(R.id.descrip_food);
        timeCook = view.findViewById(R.id.time_cooking);

        recyclerView = view.findViewById(R.id.listFood_restaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Firebase
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // Fetch data from Firebase and populate foodList
        databaseReference = FirebaseDatabase.getInstance().getReference("Food");
        FirebaseRecyclerOptions<FoodModel> options =
                new FirebaseRecyclerOptions.Builder<FoodModel>()
                        .setQuery(databaseReference,FoodModel.class)
                        .build();

        foodAdapter = new FoodAdapter(options);
        recyclerView.setAdapter(foodAdapter);

        String[] values = {"Pizza", "Hamburger", "Fries", "Ice Cream", "Sandwich"};
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Spinner spinner = view.findViewById(R.id.type_food);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.requireActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        //Choose Image from Storage Phone
        img_food.setOnClickListener(view1 -> OpenChooseImage());

        btnAddFood.setOnClickListener(view1 -> {
            String name = nameFood.getText().toString().trim();
            int price = Integer.parseInt(priceFood.getText().toString().trim());
            String desc = descFood.getText().toString().trim();
            String type = spinner.getSelectedItem().toString();
            String time = timeCook.getText().toString().trim();

            if (imageUri != null) {
                // Generate unique ID for the image
                String imageId = UUID.randomUUID().toString();
                StorageReference fileReference = storageReference.child("images/" + imageId);

                fileReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();
                                String foodModelId = databaseReference.push().getKey();
                                FoodModel foodModel = new FoodModel( imageUrl, name, desc, type,time, price,0);
                                assert foodModelId != null;
                                databaseReference.child(foodModelId).setValue(foodModel);

                                Toast.makeText(getActivity(), "Food added successfully", Toast.LENGTH_SHORT).show();

                                nameFood.setText("");
                                priceFood.setText("");
                                descFood.setText("");
                                img_food.setImageResource(R.drawable.food);
                                timeCook.setText("");
                            });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getActivity(), "Failed to add food", Toast.LENGTH_SHORT).show();
                        });
            } else if (nameFood.getText().toString().equals("") || priceFood.getText().toString().equals("")||timeCook.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Please fill in all data field", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }

    private void OpenChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            img_food.setImageURI(imageUri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        foodAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        foodAdapter.stopListening();
    }
}