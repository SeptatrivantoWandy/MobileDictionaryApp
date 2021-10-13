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

public class WordFavoriteDetail extends AppCompatActivity {

    Button buttonDelete;
    TextView FavtvWordName;

    String word;
    String definition;
    String type;
    String image;
    int position;

    RecyclerView FavwordDetailRV;

    WordDetailAdapter wdadp;
    WordFavDetailAdapter wdfadp;

    static WordDBHelper WFdb;
    static WordDBHelper WFdefdb;
    static Word obj_WF;
    static Definitions obj_WFdef;

    String wordId;
    int wordIdInt;
    int wordIdDefInt;

    public static List<WordResponse> FavwordsResponse;

    public static Vector<Word> wordsFavList;
    public static Vector<Definitions> wordsFavResponse;
    public static Vector<Definitions> wordsFavResponseAti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_favorite_detail);

        buttonDelete = findViewById(R.id.FavwordDetailButtonDelete);
        FavwordDetailRV = findViewById(R.id.rvFavDetailWord);
        FavtvWordName =  findViewById(R.id.FavdetailTVword);


        Intent intentWF = getIntent();
        WFdb =  new WordDBHelper(WordFavoriteDetail.this);
        WFdefdb = new WordDBHelper(WordFavoriteDetail.this);

//        wordId = intentWF.getStringExtra(WordFavoriteAdapter.SEND_WORD_ID);

        getSupportActionBar().setTitle("Word Definition");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FavwordsResponse = new Vector<>();
        wordsFavList = new Vector<>();
        wordsFavResponse = new Vector<>();
        wordsFavResponseAti = new Vector<>();


        Intent intent = getIntent();
        word = intent.getStringExtra(WordFavoriteAdapter.SEND_WORD);
        position = intent.getIntExtra(WordFavoriteAdapter.SEND_POSITION, -1);
        wordId = intent.getStringExtra(WordFavoriteAdapter.SEND_WORD_ID);

        wordIdInt = Integer.parseInt(wordId);

        FavtvWordName.setText(word);

        storeWFInVector();
        storeWFInDefVector();

        for(int i = 0 ; i < wordsFavResponse.size() ; i++){

            wordIdDefInt = Integer.parseInt(wordsFavResponse.get(i).getWordid());

            if(wordIdInt == wordIdDefInt){

                wordsFavResponseAti.add(new Definitions(i+1+"", wordId, wordsFavResponse.get(i).getImageUrl(), wordsFavResponse.get(i).getDefType(), wordsFavResponse.get(i).getDefDefinition()));
            }
        }
        
        
        FavwordDetailRV.setLayoutManager(new GridLayoutManager(this, 1));
        wdfadp = new WordFavDetailAdapter(this);
        wdfadp.setWordsResponse(wordsFavResponseAti);
        FavwordDetailRV.setAdapter(wdfadp);


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WFdb.deleteWF(Integer.parseInt(wordId));
                WFdefdb.deleteWFdef(wordIdInt);


                Toast.makeText(getApplicationContext(), "word deleted from favorites", Toast.LENGTH_SHORT).show();
                TabFavorite.refreshPage();

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
                wordsFavList.add(obj_WF);
            }
            return 1;
        }
    }

    public int storeWFInDefVector(){
        Cursor cursor =  WFdefdb.readAllWordFDataDef();

        if(cursor.getCount() == 0) {
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