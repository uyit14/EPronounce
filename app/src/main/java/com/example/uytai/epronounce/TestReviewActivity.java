package com.example.uytai.epronounce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestReviewActivity extends AppCompatActivity {
    @BindView(R.id.list_words_correct)
    ListView list_words_correct;
    @BindView(R.id.list_words_wrong)
    ListView list_words_wrong;
    //
    ArrayList<String> arrList_correct;
    ArrayList<String> arrList_wrong;
    //
    WordsCorrectAdapter wordsCorrectAdapter;
    WordsWrongAdapter wordsWrongAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_review);
        //
        ButterKnife.bind(this);
        //
        arrList_correct = new ArrayList<>();
        arrList_wrong = new ArrayList<>();
        //
        getresult();
        init();
    }

    private void init() {
        wordsCorrectAdapter = new WordsCorrectAdapter(getApplicationContext(), arrList_correct);
        wordsWrongAdapter = new WordsWrongAdapter(getApplicationContext(), arrList_wrong);
        list_words_correct.setAdapter(wordsCorrectAdapter);
        list_words_wrong.setAdapter(wordsWrongAdapter);
    }

    //
    private void getresult() {
        Bundle bundle = getIntent().getBundleExtra(Constant.KEY_PUT_BUNDLE);
        if(bundle!=null){
            arrList_correct = (ArrayList<String>) bundle.getSerializable(Constant.KEY_PUT_ARRAYLIST_CORRECT);
            arrList_wrong = (ArrayList<String>) bundle.getSerializable(Constant.KEY_PUT_ARRAYLIST_wrong);
        }
    }
}
