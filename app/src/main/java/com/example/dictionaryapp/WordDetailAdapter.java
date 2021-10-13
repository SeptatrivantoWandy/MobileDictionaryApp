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

public class WordDetailAdapter extends RecyclerView.Adapter<WordDetailAdapter.MyViewHolder> {

    Context ctx;
    List<WordResponse> wordsResponse;

    public WordDetailAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setWordsResponse(List<WordResponse> wordsResponse) {
        notifyDataSetChanged();
        this.wordsResponse = wordsResponse;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.word_definitions_display_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        WordResponse definitionWord = wordsResponse.get(position);

        String url = definitionWord.imageUrl;

        holder.tvDetailType.setText(definitionWord.type);
        holder.tvDetailDefinition.setText(definitionWord.definition);

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

            imageView = itemView.findViewById(R.id.imageViewDefinition);
            tvDetailType = itemView.findViewById(R.id.tvWordDefType);
            tvDetailDefinition = itemView.findViewById(R.id.textWordDefDefinition);


        }
    }



}
