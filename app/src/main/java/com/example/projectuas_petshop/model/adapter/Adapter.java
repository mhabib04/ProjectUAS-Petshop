package com.example.projectuas_petshop.model.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.model.DataItem;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private ArrayList<DataItem> model;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public Adapter(Context context, ArrayList<DataItem> model) {
        this.context = context;
        this.model = model;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_animal_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DataItem data = model.get(position);
        holder.id_animal.setText(String.valueOf(data.getIdAnimal()));
        holder.type.setText(data.getType());
        holder.breed.setText(data.getBreed());
        holder.price.setText(String.valueOf(data.getPrice()));
        holder.age.setText(String.valueOf(data.getAge()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
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
        void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_animal, type, breed, price, age;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_animal = itemView.findViewById(R.id.txtIdAnimal);
            type = itemView.findViewById(R.id.txtType);
            breed = itemView.findViewById(R.id.txtBreed);
            price = itemView.findViewById(R.id.txtPrice);
            age = itemView.findViewById(R.id.txtAge);
        }
    }
}
