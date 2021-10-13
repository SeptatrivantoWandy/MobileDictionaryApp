package com.example.dictionaryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Vector;

public class WordFavDetailAdapter extends RecyclerView.Adapter<WordFavDetailAdapter.MyViewHolder> {

    Context ctx;
    Vector<Definitions> wordsResponse;

    public WordFavDetailAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setWordsResponse(Vector<Definitions> wordsResponse) {
        this.wordsResponse = wordsResponse;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.word_fav_definitions_display_rv, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Definitions definitionWord = wordsResponse.get(position);

        String url =  definitionWord.getImageUrl();

        holder.tvDetailType.setText(definitionWord.getDefType());
        holder.tvDetailDefinition.setText(definitionWord.getDefDefinition());

        Glide.with(ctx)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return wordsResponse.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDetailType, tvDetailDefinition;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.FavimageViewDefinition);
            tvDetailType = itemView.findViewById(R.id.FavtvWordDefType);
            tvDetailDefinition = itemView.findViewById(R.id.FavtextWordDefDefinition);

        }
    }

}
