package com.example.uytai.epronounce;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PronouncingAActivity extends AppCompatActivity {
    @BindView(R.id.tvtime)
    TextView tvTime;
    @BindView(R.id.btn_speech)
    ImageView btn_speech;
    @BindView(R.id.tvspeech_input)
    TextView tvSpeech_input;
    @BindView(R.id.tvcontent_read)
    TextView tvcontent_read;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public static ArrayList<EPronounce> arrayList;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    public int i =0;
    String content = "";
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronouncing_a);
        ButterKnife.bind(this);
        arrayList = new ArrayList<>();
        click();
        sql();
        CountDownTimer(10000, 1000);
    }
    //truy van csdl
    private void sql() {
        Cursor data = MainActivity.database.getData("SELECT * FROM PronounceA");
        while (data.moveToNext()){
            int id = data.getInt(0);
            String content = data.getString(1);
            arrayList.add(new EPronounce(id, content));
        }
    }

    private void click() {
        btn_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }


    //speech to text
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    //


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tvSpeech_input.setText(result.get(0));
                    content = result.get(0);
                }
                break;
            }

        }
    }

    //CountDownTimer
    private void CountDownTimer(final int duration, final long tick){
        new CountDownTimer(duration, tick) {
            public void onTick(long millisUntilFinished) {
                tvTime.setText(millisUntilFinished/1000+"");
                    tvcontent_read.setText(arrayList.get(i).getContent());
                   // if(TextUtils.equals(arrayList.get(i).toString(), content)){
                        progress+=10;
                        tvSpeech_input.setTextColor(getResources().getColor(R.color.colorGreen));
                       // i++;
                    //}
            }

            public void onFinish() {
                if(i<arrayList.size()-1){
                    i++;
                    CountDownTimer(duration, tick);
                }else{
                    tvTime.setText("Time out!");
                    progressBar.setProgress(progress);
                }
            }
        }.start();
    }

    //

}
