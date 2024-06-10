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
import com.example.projectuas_petshop.model.select.selectFood.FoodDataSelect;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterListFoodAdmin extends RecyclerView.Adapter<AdapterListFoodAdmin.ViewHolder> {

    private Context context;
    private ArrayList<FoodDataSelect> model;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public AdapterListFoodAdmin(Context context, ArrayList<FoodDataSelect> model) {
        this.context = context;
        this.model = model;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_food_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FoodDataSelect data = model.get(position);
        String type = data.getType();
        switch (type) {
            case "Cat":
                holder.typeFood.setText(context.getString(R.string.cat));
                break;
            case "Dog":
                holder.typeFood.setText(context.getString(R.string.dog));
                break;
            case "Bird":
                holder.typeFood.setText(context.getString(R.string.bird));
                break;
            case "Fish":
                holder.typeFood.setText(context.getString(R.string.fish));
                break;
        }
        holder.nameFood.setText(data.getName());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        int price = data.getPrice();
        holder.priceFood.setText(formatRupiah.format((double)price));
        byte[] imageBytes = Base64.decode(data.getImage().substring(data.getImage().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imageFood.setImageBitmap(bitmap);

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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, int idFood);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeFood, nameFood, priceFood;
        ImageView imageFood;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeFood = itemView.findViewById(R.id.txtTypeFoodAdmin);
            nameFood = itemView.findViewById(R.id.txtNameFoodAdmin);
            priceFood = itemView.findViewById(R.id.txtPriceFoodAdmin);
            imageFood = itemView.findViewById(R.id.imgFoodAdmin);
        }
    }
}
