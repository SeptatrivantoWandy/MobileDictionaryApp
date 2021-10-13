package com.example.dictionaryapp;

public class Word {
    private String wordId;
    private String word;


    public Word(String wordId, String word) {
        this.wordId = wordId;
        this.word = word;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
