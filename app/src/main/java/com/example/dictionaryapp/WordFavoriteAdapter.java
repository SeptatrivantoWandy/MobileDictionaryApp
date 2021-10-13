package com.example.dictionaryapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Vector;

public class WordFavoriteAdapter extends RecyclerView.Adapter<WordFavoriteAdapter.MyViewHolder> {

    public static final String SEND_WORD = "com.example.dictionaryapp.SEND_WORD";
    public static final String SEND_POSITION = "com.example.dictionaryapp.SEND_POSITION";
    public static final String SEND_WORD_ID = "com.example.dictionaryapp.SEND_POSITION_WORD_ID";

    Context ctx;
    Vector<Word> words;
    Vector<Definitions> wordsFavResponse;

    int wordIdInt;

    WordDBHelper WFdb;
    WordDBHelper WFdefdb;

    public WordFavoriteAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setWords(Vector<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.favorite_display_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Word word = words.get(position);


        wordsFavResponse =  new Vector<>();

        holder.tvWord.setText(word.getWord());

        WFdb =  new WordDBHelper(ctx);
        WFdefdb = new WordDBHelper(ctx);

        wordIdInt = Integer.parseInt(word.getWordId());


        holder.buttonDelete.setOnClickListener((view) -> {
            notifyDataSetChanged();

            wordIdInt = Integer.parseInt(word.getWordId());

            WFdb.deleteWF(wordIdInt);


            wordsFavResponse.clear();
            storeWFInDefVector();

            WFdefdb.deleteWFdef(wordIdInt);

            Toast.makeText(ctx, "word deleted from favorites", Toast.LENGTH_SHORT).show();
            TabFavorite.refreshPage();
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, WordFavoriteDetail.class);

                intent.putExtra(SEND_WORD, word.getWord());
                intent.putExtra(SEND_POSITION, position);
                intent.putExtra(SEND_WORD_ID, word.getWordId());

                ctx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvWord;
        Button buttonDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWord = itemView.findViewById(R.id.tvFavoriteNameWord);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);

        }
    }

    public int storeWFInDefVector() {
        Cursor cursor = WFdefdb.readAllWordFDataDef();

        if (cursor.getCount() == 0) {
            return -1;
        } else {
            while (cursor.moveToNext()) {
                Definitions obj_WFdef = new Definitions(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                wordsFavResponse.add(obj_WFdef);
            }
            return 1;
        }
    }
}
