package com.example.social;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView text_at_source,text_title,text_description;
    ImageView img;
    CardView cardView;



    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        text_at_source= itemView.findViewById(R.id.text_at_source);
        text_title= itemView.findViewById(R.id.text_title);
        text_description= itemView.findViewById(R.id.text_description);
        img= itemView.findViewById(R.id.img_headline);
        cardView= itemView.findViewById(R.id.main_container);

    }
}
