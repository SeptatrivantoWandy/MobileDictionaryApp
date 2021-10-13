package com.example.dictionaryapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.MyViewHolder> {

    public static final String SEND_WORD = "com.example.dictionaryapp.SEND_WORD";
    public static final String SEND_POSITION = "com.example.dictionaryapp.SEND_POSITION";

    Context ctx;
    public static List<WordListResponse> words;

    public static int pos;

    public WordAdapter(Context ctx) {
        this.ctx = ctx;
        this.words = new ArrayList<>();
    }

    public void setWords(List<WordListResponse> words){
        this.words = words;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.explore_display_rv, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        WordListResponse word = words.get(position);
        holder.tvWord.setText(word.word);


        WordDetail.wordsList = new Vector<>();
        WordDetail.wordsResponse = new Vector<>();

        WordDetail.WFdb =  new WordDBHelper(ctx);
        WordDetail.WFdefdb = new WordDBHelper(ctx);

        holder.buttonSave.setOnClickListener((view) -> {
            notifyDataSetChanged();

            WordDetail.obj_WF = new Word("1", word.word);
            WordDetail.WFdb.insertWordF(WordDetail.obj_WF);

            WordDetail.wordsList.clear();
            WordDetail.storeWFInVector();


            getWordsResponse(position);

            for (int i = 0 ; i < word.getDefinitions().size() ; i++){


                if(word.getDefinitions().get(i).imageUrl == null){
                    WordDetail.obj_WFdef = new Definitions("1", WordDetail.wordsList.get(WordDetail.wordsList.size()-1).getWordId()+"", "no image", word.getDefinitions().get(i).type, word.getDefinitions().get(i).definition);
                    WordDetail.WFdefdb.insertWordFdef(WordDetail.obj_WFdef);
                } else{
                    WordDetail.obj_WFdef = new Definitions("1", WordDetail.wordsList.get(WordDetail.wordsList.size()-1).getWordId()+"", word.getDefinitions().get(i).imageUrl, word.getDefinitions().get(i).type, word.getDefinitions().get(i).definition);
                    WordDetail.WFdefdb.insertWordFdef(WordDetail.obj_WFdef);
                }

            }

            Toast.makeText(ctx, "word saved to favorites", Toast.LENGTH_SHORT).show();

        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx, WordDetail.class);

                intent.putExtra(SEND_WORD, word.word);
                intent.putExtra(SEND_POSITION, position);

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
        Button buttonSave;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWord = itemView.findViewById(R.id.tvExploreNameWord);
            buttonSave = itemView.findViewById(R.id.buttonSave);

        }

    }

    public void filtered(ArrayList<WordListResponse> filtered){
        words = filtered;
        notifyDataSetChanged();
    }

    public static int getWordsResponse(int pos){
        for(int i = 0 ; i < words.get(pos).getDefinitions().size() ; i++){
            WordDetail.definition = words.get(pos).getDefinitions().get(i).getDefinition();
            WordDetail.type = words.get(pos).getDefinitions().get(i).getType();
            WordDetail.image = words.get(pos).getDefinitions().get(i).imageUrl;

            WordDetail.wordsResponse.add(new WordResponse(WordDetail.image, WordDetail.type, WordDetail.definition));

        }
        return 0;
    }

}
