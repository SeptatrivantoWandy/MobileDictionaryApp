package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

public class WordDetail extends AppCompatActivity {


    Button buttonSave;
    TextView tvWordName;

    String word;
    static String definition;
    static String type;
    static String image;
    static int position;

    RecyclerView wordDetailRV;
    WordDetailAdapter wdadp;

    static WordDBHelper WFdb;
    static WordDBHelper WFdefdb;
    static Word obj_WF;
    static Definitions obj_WFdef;

    public static List<WordResponse> wordsResponse;
    public static Vector<Word> wordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        getSupportActionBar().setTitle("Word Definition");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WFdb =  new WordDBHelper(WordDetail.this);
        WFdefdb = new WordDBHelper(WordDetail.this);

        wordsResponse = new Vector<>();
        wordsList = new Vector<>();

        buttonSave = findViewById(R.id.wordDetailButtonSave);
        tvWordName = findViewById(R.id.detailTVword);

        wordDetailRV = findViewById(R.id.rvDetailWord);


        Intent intent = getIntent();
        word = intent.getStringExtra(WordAdapter.SEND_WORD);
        position = intent.getIntExtra(WordAdapter.SEND_POSITION, -1);

        wordDetailRV.setLayoutManager(new GridLayoutManager(this, 1));
        wdadp = new WordDetailAdapter(this);
        wdadp.setWordsResponse(wordsResponse);
        wordDetailRV.setAdapter(wdadp);


        getWordsResponse();

        tvWordName.setText(word);


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj_WF = new Word("1", word);
                WFdb.insertWordF(obj_WF);

                wordsList.clear();
                storeWFInVector();

                for (int i = 0 ; i < wordsResponse.size() ; i++){

                    if(wordsResponse.get(i).imageUrl == null){
                        obj_WFdef = new Definitions("1", wordsList.get(wordsList.size()-1).getWordId()+"", "no image", wordsResponse.get(i).type, wordsResponse.get(i).definition);
                        WFdefdb.insertWordFdef(obj_WFdef);
                    } else{
                        obj_WFdef = new Definitions("1", wordsList.get(wordsList.size()-1).getWordId()+"", wordsResponse.get(i).imageUrl, wordsResponse.get(i).type, wordsResponse.get(i).definition);
                        WFdefdb.insertWordFdef(obj_WFdef);
                    }

                }

                Toast.makeText(getApplicationContext(), "word saved to favorites", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }

    public static int storeWFInVector(){
        Cursor cursor =  WFdb.readAllWordFData();

        if(cursor.getCount() == 0) {
            return -1;
        } else {
            while (cursor.moveToNext()) {
                Word obj_WF = new Word(cursor.getString(0), cursor.getString(1));
                wordsList.add(obj_WF);
            }
            return 1;
        }
    }

    public static void getWordsResponse(){
        for(int i = 0 ; i < WordAdapter.words.get(position).getDefinitions().size() ; i++){
            definition = WordAdapter.words.get(position).getDefinitions().get(i).getDefinition();
            type = WordAdapter.words.get(position).getDefinitions().get(i).getType();
            image = WordAdapter.words.get(position).getDefinitions().get(i).imageUrl;

            wordsResponse.add(new WordResponse(image, type, definition));


        }
    }


}