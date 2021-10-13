package com.example.dictionaryapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFavorite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFavorite extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabFavorite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFavorite.
     */
    // TODO: Rename and change types and number of parameters
    public static TabFavorite newInstance(String param1, String param2) {
        TabFavorite fragment = new TabFavorite();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView favoriteRV;
    public static WordFavoriteAdapter wfadp;


    public static Vector<Word> wordsList;
    public static Vector<Definitions> wordsListqweq;

    public static WordDBHelper WFdb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_tab_favorite, container, false);


        favoriteRV = v.findViewById(R.id.rvFavorite);

        WFdb =  new WordDBHelper(v.getContext());

        wordsList = new Vector<>();
        wordsListqweq = new Vector<>();

        wordsList.clear();
        storeWFInVector();


        favoriteRV.setLayoutManager(new GridLayoutManager(v.getContext(), 1));
        wfadp = new WordFavoriteAdapter(v.getContext());
        wfadp.setWords(wordsList);
        favoriteRV.setAdapter(wfadp);


        return v;
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

    public static void refreshPage(){
        if(wordsList != null){
            wordsList.clear();
            storeWFInVector();
            wfadp.notifyDataSetChanged();
        }
    }

}