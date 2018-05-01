package com.example.uytai.epronounce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestResultActivity extends AppCompatActivity {
    @BindView(R.id.result)
    Button btn_result;
    @BindView(R.id.btn_again)
    ImageView btn_again;
    @BindView(R.id.btn_review)
    ImageView btn_review;
    @BindView(R.id.btn_home)
    ImageView btn_home;
    int result;
    //
    ArrayList<String> arrList_correct;
    ArrayList<String> arrList_wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        ButterKnife.bind(this);
        //
        arrList_correct = new ArrayList<>();
        arrList_wrong = new ArrayList<>();
        getresult();
        //
        click();
    }

    private void click() {
        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestResultActivity.this, PronouncingAActivity.class));
                finish();
            }
        });
        //
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestResultActivity.this, MainActivity.class));
                finish();
            }
        });
        //
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestResultActivity.this, TestReviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_PUT_ARRAYLIST_CORRECT, arrList_correct);
                bundle.putSerializable(Constant.KEY_PUT_ARRAYLIST_wrong, arrList_wrong);
                intent.putExtra(Constant.KEY_PUT_BUNDLE, bundle);
                startActivity(intent);
            }
        });
    }

    private void getresult() {
        Bundle bundle = getIntent().getBundleExtra(Constant.KEY_PUT_BUNDLE);
        if(bundle!=null){
            result = bundle.getInt(Constant.KEY_PUT_RESULT, -1);
            arrList_correct = (ArrayList<String>) bundle.getSerializable(Constant.KEY_PUT_ARRAYLIST_CORRECT);
            arrList_wrong = (ArrayList<String>) bundle.getSerializable(Constant.KEY_PUT_ARRAYLIST_wrong);
            btn_result.setText(result + "/10");
        }
    }
}
