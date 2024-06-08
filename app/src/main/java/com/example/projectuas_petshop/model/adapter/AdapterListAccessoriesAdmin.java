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
import com.example.projectuas_petshop.model.select.selectAccessories.AccessoriesDataSelect;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterListAccessoriesAdmin extends RecyclerView.Adapter<AdapterListAccessoriesAdmin.ViewHolder> {
    private Context context;
    private ArrayList<AccessoriesDataSelect> model;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public AdapterListAccessoriesAdmin(Context context, ArrayList<AccessoriesDataSelect> model) {
        this.context = context;
        this.model = model;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_accessories_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AccessoriesDataSelect data = model.get(position);
        holder.nameAccessories.setText(data.getName());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        int price = data.getPrice();
        holder.priceAccessories.setText(formatRupiah.format((double)price));
        byte[] imageBytes = Base64.decode(data.getImage().substring(data.getImage().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imageAccessories.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position, data.getIdAccessories());
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
        void onItemClick(View view, int position, int idAccessories);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameAccessories, priceAccessories;
        ImageView imageAccessories;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameAccessories = itemView.findViewById(R.id.txtNameAccessoriesAdmin);
            priceAccessories = itemView.findViewById(R.id.txtPriceAccessoriesAdmin);
            imageAccessories = itemView.findViewById(R.id.imgAccessoriesAdmin);
        }
    }
}
