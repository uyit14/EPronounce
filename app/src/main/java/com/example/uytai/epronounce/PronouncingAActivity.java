package com.example.uytai.epronounce;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

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
    @BindView(R.id.tv_count)
    TextView tv_count;

    public static ArrayList<EPronounce> arrayList;
    public static ArrayList<EPronounce> arrayList2;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    public int i =0;
    String content = "";
    int progress = 0;
    int num1, num2;
    int result=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronouncing_a);
        ButterKnife.bind(this);
        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        click();
        sql();
        //tg đọc là 10s
        CountDownTimer(10000, 1000);
    }

    //truy van csdl và random số câu sẽ lấy ra cho người dùng đọc
    private void sql() {
        //truy vấn lấy tất cả dữ liệu đổ vào mảng arr1
        Cursor data = MainActivity.database.getData("SELECT * FROM PronounceA");
        while (data.moveToNext()){
            int id = data.getInt(0);
            String content = data.getString(1);
            arrayList.add(new EPronounce(id, content));
        }
        //random để lấy 2 số bất kỳ cách nhau 10 ký tự nằm trong khoảng 1 - arr1.size
        Random r = new Random();
        num1 = r.nextInt(arrayList.size()) + 1;
        if((num1+10)>arrayList.size()){
            num2 = num1-10;
        }else if((num1+10)<arrayList.size()){
            num2 = num1+10;
        }
        //lấy ra 10 câu bất kỳ trong mảng arr1 gán vào arr2
        if(num1>num2){
            for(int i=num2;i<num1;i++){
                arrayList2.add(arrayList.get(i));
            }
        }else if(num1<num2){
            for(int i=num1;i<num2;i++){
                arrayList2.add(arrayList.get(i));
            }
        }
    }

    //các sự kiện click
    private void click() {
        btn_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    //custom cái layout của cái mic khi mở bật lên
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

    //hàm bật mic
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

    //boolean flag=false;
    //CountDownTimer: hàm để cho client đọc và kiểm tra đúng hay không?
    private void CountDownTimer(final int duration, final long tick){
        new CountDownTimer(duration, tick) {
            public void onTick(long millisUntilFinished) {
                //thời gian giảm dần
                tvTime.setText(millisUntilFinished/1000+"");
                //set dữ liệu ra ngoài cho client đọc theo
                    tvcontent_read.setText(arrayList2.get(i).getContent());
                    // nếu đọc đúng hết câu
                if(arrayList2.get(i).getContent().equals(content)){
                    //set chữ xanh
                    tvSpeech_input.setTextColor(getResources().getColor(R.color.colorGreen));
                    //biến để đếm số câu đúng
                    result++;
                }
            }
            public void onFinish() {
                if(i<arrayList2.size()-1){
                        progress+=10;
                        progressBar.setProgress(progress);
                        tv_count.setText(progress/10 + "/10");
                    i++;
                    CountDownTimer(duration, tick);
                }else{
                    tvTime.setText("Time out!");
                    Intent intent = new Intent(PronouncingAActivity.this, TestResultActivity.class);
                    intent.putExtra(Constant.KEY_PUT_RESULT, result);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }
}
