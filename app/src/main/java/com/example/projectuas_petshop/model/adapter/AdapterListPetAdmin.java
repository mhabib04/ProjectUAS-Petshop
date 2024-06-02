package com.example.projectuas_petshop.model.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.model.select.selectPet.PetDataSelect;

import java.util.ArrayList;

public class AdapterListPetAdmin extends RecyclerView.Adapter<AdapterListPetAdmin.ViewHolder> {

    private Context context;
    private ArrayList<PetDataSelect> model;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public AdapterListPetAdmin(Context context, ArrayList<PetDataSelect> model) {
        this.context = context;
        this.model = model;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_pet_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PetDataSelect data = model.get(position);
        holder.id_pet.setText(String.valueOf(data.getIdPet()));
        holder.type.setText(data.getType());
        holder.breed.setText(data.getBreed());
        holder.price.setText(String.valueOf(data.getPrice()));
        holder.age.setText(String.valueOf(data.getAge()));
        if (data.getType().equals("Dog")) {
            holder.img_pet.setImageResource(R.drawable.dog);
        } else if (data.getType().equals("Cat")) {
            holder.img_pet.setImageResource(R.drawable.cat2);
        } else if (data.getType().equals("Bird")) {
            holder.img_pet.setImageResource(R.drawable.robin);
        } else if (data.getType().equals("Fish")) {
            holder.img_pet.setImageResource(R.drawable.fish);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position, data.getIdPet());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, int idPet);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_pet, type, breed, price, age;
        ImageView img_pet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_pet = itemView.findViewById(R.id.txtIdPet);
            type = itemView.findViewById(R.id.txtTypePet);
            breed = itemView.findViewById(R.id.txtBreedPet);
            price = itemView.findViewById(R.id.txtPricePet);
            age = itemView.findViewById(R.id.txtAgePet);
            img_pet = itemView.findViewById(R.id.imgPet);
        }
    }
}
