package com.example.augmentedreality3d;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ModelAdapter extends RecyclerView.Adapter {
    String[] items;
    Context context;
    private TextView textViewTitle;

    public ModelAdapter(String[] items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.custom_row_item_model, parent, false);
        textViewTitle =  row.findViewById(R.id.textViewTitle);
        return new ItemHolder(row);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position == 0) {
            Picasso.get().load(R.drawable.bedroom2).resize(900, 300).into(((ItemHolder) holder).imageViewThumbnail);
            textViewTitle.setText("1-Bedroom Apartment");
        } else if(position == 1){
            Picasso.get().load(R.drawable.bedroom1).resize(900, 300).into(((ItemHolder) holder).imageViewThumbnail);
            textViewTitle.setText("2-Bedroom Apartment");
        } else if(position == 2){
            Picasso.get().load(R.drawable.bedroom3).resize(900, 300).into(((ItemHolder) holder).imageViewThumbnail);
            textViewTitle.setText("3-Bedroom Apartment");
        } else if(position == 3){
            Picasso.get().load(R.drawable.bedroom2).resize(900, 300).into(((ItemHolder) holder).imageViewThumbnail);
            textViewTitle.setText("Studio Apartment");
        }

    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription;
        ImageView imageViewThumbnail;

        public ItemHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageViewThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
        }
    }
}
