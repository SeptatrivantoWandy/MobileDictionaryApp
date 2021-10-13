package com.example.dictionaryapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabExplore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabExplore extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabExplore() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabExplore.
     */
    // TODO: Rename and change types and number of parameters
    public static TabExplore newInstance(String param1, String param2) {
        TabExplore fragment = new TabExplore();
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

    EditText searchText;

    RecyclerView exploreRV;
    public static WordAdapter wadp;

    public static List<WordListResponse> wordsList;

    WordDBHelper WFdb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_explore, container, false);


        exploreRV = v.findViewById(R.id.rvExplore);
        searchText = v.findViewById(R.id.editTextSearch);

        exploreRV.setHasFixedSize(true);

        WFdb =  new WordDBHelper(v.getContext());

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    closeKeyboard();
                }
            }
        });

        exploreRV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closeKeyboard();
                return false;
            }
        });


        wadp = new WordAdapter(v.getContext());

        exploreRV.setAdapter(wadp);
        exploreRV.setLayoutManager(new GridLayoutManager(v.getContext(), 1));



        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("https://myawesomedictionary.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        herokuappService service = retrofit.create(herokuappService.class);

        Call<List<WordListResponse>> call = service.getWords();

        call.enqueue(new Callback<List<WordListResponse>>() {
            @Override
            public void onResponse(Call<List<WordListResponse>> call, Response<List<WordListResponse>> response) {
                if(!response.isSuccessful()){

                    return;
                }

                wordsList = response.body();

                wadp.setWords(wordsList);

            }

            @Override
            public void onFailure(Call<List<WordListResponse>> call, Throwable t) {

            }
        });

        return v;
    }

    private void filter(String text) {
        ArrayList<WordListResponse> filtered = new ArrayList<>();

        for(WordListResponse WList : wordsList){
            if(WList.word.toLowerCase().startsWith(text.toLowerCase())){
                filtered.add(WList);
            }
        }

        wadp.filtered(filtered);

    }

    private void closeKeyboard() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.closeKeyboard();

    }

    public static void refreshPage(){
        wadp.notifyDataSetChanged();
    }

}