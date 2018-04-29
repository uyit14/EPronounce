package com.example.uytai.epronounce;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddProAActivity extends AppCompatActivity {
    @BindView(R.id.tip_content)
    TextInputLayout tip_content;
    @BindView(R.id.save)
    ImageView btnSave;
    @BindView(R.id.cancle)
    ImageView btnCancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pro_a);
        ButterKnife.bind(this);
        insert();
    }
    //
    private void insert() {
        //save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = tip_content.getEditText().getText().toString();
                if(content.equals("")){
                    Toast.makeText(AddProAActivity.this, "Fill in Name", Toast.LENGTH_LONG).show();
                }else{
                    MainActivity.database.QueryData("INSERT INTO PronounceA VALUES(null, '"+content+"')");
                    startActivity(new Intent(AddProAActivity.this, ResourceActivity.class));
                    finish();
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }
            }
        });
        //cancle
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
