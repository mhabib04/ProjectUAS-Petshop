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
import com.example.projectuas_petshop.model.select.selectFoodByType.FoodDataSelectByType;
import com.example.projectuas_petshop.model.select.selectPetByType.PetDataSelectByType;

import java.util.ArrayList;

public class AdapterListFoodUser extends RecyclerView.Adapter<AdapterListFoodUser.ViewHolder>{
    private Context context;
    private ArrayList<FoodDataSelectByType> model;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    public AdapterListFoodUser(Context context, ArrayList<FoodDataSelectByType> model) {
        this.context = context;
        this.model = model;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_food_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FoodDataSelectByType data = model.get(position);
        holder.txtFoodUser.setText(data.getName());
        byte[] imageBytes = Base64.decode(data.getImage().substring(data.getImage().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imgFoodUser.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position, data.getIdFood());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public void setOnItemClickListener(AdapterListFoodUser.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, int idPet);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFoodUser;
        ImageView imgFoodUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFoodUser = itemView.findViewById(R.id.txtFoodUser);
            imgFoodUser = itemView.findViewById(R.id.imgFoodUser);
        }
    }

}
