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
import com.example.projectuas_petshop.model.select.selectPet.PetDataSelect;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PetDataSelect data = model.get(position);
        holder.typePet.setText(data.getType());
        holder.breedPet.setText(data.getBreed());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        int price = data.getPrice();
        holder.pricePet.setText(formatRupiah.format((double)price));
        holder.agePet.setText(data.getAge() + " " + context.getResources().getString(R.string.month));
        byte[] imageBytes = Base64.decode(data.getImage().substring(data.getImage().indexOf(",") + 1), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imagePet.setImageBitmap(bitmap);

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
        TextView typePet, breedPet, pricePet, agePet;
        ImageView imagePet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typePet = itemView.findViewById(R.id.txtTypePetAdmin);
            breedPet = itemView.findViewById(R.id.txtBreedPetAdmin);
            pricePet = itemView.findViewById(R.id.txtPricePetAdmin);
            agePet = itemView.findViewById(R.id.txtAgePetAdmin);
            imagePet = itemView.findViewById(R.id.imgPetAdmin);
        }
    }
}
