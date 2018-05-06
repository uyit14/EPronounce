package com.example.uytai.epronounce;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
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

    ArrayList<EPronounce> arrayList;
    ArrayList<EPronounce> arrayList2;
    //
    ArrayList<String> arrList_correct;
    ArrayList<String> arrList_wrong;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    public int i =0;
    String content = "";
    int progress = 0;
    int num1, num2;
    int result=0;
    //audio
    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronouncing_a);
        ButterKnife.bind(this);
        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        arrList_correct = new ArrayList<>();
        arrList_wrong = new ArrayList<>();
        click();
        sql();
        //custom time
        CustomTime();
    }

    private void audioclock() {
        mediaPlayer2 = MediaPlayer.create(PronouncingAActivity.this, R.raw.clock_ticking);
        mediaPlayer2.start();
    }


    private void audiowhencorrecr() {
        mediaPlayer = MediaPlayer.create(PronouncingAActivity.this, R.raw.correct);
        mediaPlayer.start();
    }

    private void CustomTime() {
        final Dialog dialog = new Dialog(PronouncingAActivity.this);
        dialog.setContentView(R.layout.dialog_custom_time);
        dialog.setTitle("Choose time!");
        ImageButton dialogBtn_OK = dialog.findViewById(R.id.btn_ok);
        ImageButton dialogBtn_NOT = dialog.findViewById(R.id.btn_not);
        final TextInputLayout tip_time = dialog.findViewById(R.id.tip_time);
        dialogBtn_NOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
        //
        dialogBtn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeStr = tip_time.getEditText().getText().toString();
                int timeInt = Integer.parseInt(timeStr);
                CountDownTimer(timeInt*1000, 1000);
                dialog.dismiss();
            }
        });
        dialog.show();
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

    boolean flag=false;
    //CountDownTimer: hàm để cho client đọc và kiểm tra đúng hay không?
    private void CountDownTimer(final int duration, final long tick){
        //text khi uer đọc
        tvSpeech_input.setText("");
        //set dữ liệu ra ngoài cho client đọc theo
        tvcontent_read.setText(arrayList2.get(i).getContent());
        //
        audioclock();
        new CountDownTimer(duration, tick) {
            public void onTick(long millisUntilFinished) {
                //thời gian giảm dần
                tvTime.setText(millisUntilFinished/1000+"");
                    // nếu đọc đúng hết câu
                content = content.toLowerCase();
                if(arrayList2.get(i).getContent().equals(content)){
                    audiowhencorrecr();
                    flag=true;
                    //set chữ xanh
                    tvSpeech_input.setTextColor(getResources().getColor(R.color.colorGreen));
                    i++;
                    CountDownTimer(duration, tick);
                }else{
                    tvSpeech_input.setTextColor(getResources().getColor(R.color.colorBlack));
                    flag=false;
                }
            }
            public void onFinish() {
                mediaPlayer2.stop();
                tvSpeech_input.setText("");
                if(i==arrayList2.size()-1){
                    if(flag){
                        result++;
                        arrList_correct.add(arrayList2.get(i).getContent());
                    }else{
                        arrList_wrong.add(arrayList2.get(i).getContent());
                    }
                }
                if(i<arrayList2.size()-1){
                    if(flag){
                        arrList_correct.add(arrayList2.get(i).getContent());
                        //biến để đếm số câu đúng
                        result++;
                    }else{
                        arrList_wrong.add(arrayList2.get(i).getContent());
                    }
                        progress+=10;
                        progressBar.setProgress(progress);
                        tv_count.setText((progress/10) + "/10");
                    i++;
                    CountDownTimer(duration, tick);
                }else{
                    tvTime.setText("Time out!");
                    Intent intent = new Intent(PronouncingAActivity.this, TestResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.KEY_PUT_ARRAYLIST_CORRECT, arrList_correct);
                    bundle.putSerializable(Constant.KEY_PUT_ARRAYLIST_wrong, arrList_wrong);
                    bundle.putInt(Constant.KEY_PUT_RESULT, result);
                    intent.putExtra(Constant.KEY_PUT_BUNDLE,bundle);
                    startActivity(intent);
                    finish();
                }
                flag=false;
            }
        }.start();
    }
}
