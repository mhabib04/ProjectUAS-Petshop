package com.example.projectuas_petshop.model.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectuas_petshop.R;
import com.example.projectuas_petshop.model.select.selectPetByType.PetDataSelectByType;


import java.util.ArrayList;

public class AdapterListPetUser extends RecyclerView.Adapter<AdapterListPetUser.ViewHolder> {
    private Context context;
    private ArrayList<PetDataSelectByType> model;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public AdapterListPetUser(Context context, ArrayList<PetDataSelectByType> model) {
        this.context = context;
        this.model = model;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_pet_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PetDataSelectByType data = model.get(position);
        holder.txtPetUser.setText(data.getBreed());
        byte[] imageBytes = Base64.decode(data.getImage().substring(data.getImage().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imgPetUser.setImageBitmap(bitmap);

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
        TextView txtPetUser;
        ImageView imgPetUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPetUser = itemView.findViewById(R.id.txtPetUser);
            imgPetUser = itemView.findViewById(R.id.imgPetUser);
        }
    }
}
