package com.example.uytai.epronounce;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.applandeo.FilePicker;
import com.applandeo.constants.FileType;
import com.applandeo.listeners.OnSelectFileListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseFileActivity extends AppCompatActivity {
    @BindView(R.id.edt_file_name)
    EditText edtFileName;
    @BindView(R.id.btn_choose_file)
    Button btnChooseFile;
    @BindView(R.id.txtv_file_message)
    TextView txtvFileMessage;
    @BindView(R.id.list_devide_sentences)
    ListView listDevideSentences;
    @BindView(R.id.btn_cancel_file)
    Button btnCancelFile;
    @BindView(R.id.btn_ok_file)
    Button btnOkFile;
    @BindView(R.id.txtv_content)
    TextView txtvContent;

    private static final int REQUEST_CODE = 6384;
    public List<String> listSentences = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_file);

        ButterKnife.bind(this);

        //Event - click functions
        click();

        //save new text file
//        String[] content = "hello.google.goodbye".split(".");
        String content = "hAAllo.google.goodbye";
        Log.e("AAA:",content);
        saveTextFile(content);
    }

    //Event - click
    public void click(){
        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChooseFileActivity.this, "ChooseFile", Toast.LENGTH_SHORT).show();
                //choose file and show
                chooseFile();
            }
        });
        btnCancelFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChooseFileActivity.this, "Cancel File", Toast.LENGTH_SHORT).show();
                ChooseFileActivity.this.finish();
            }
        });
        btnOkFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listSentences.size()<1){
                    Toast.makeText(ChooseFileActivity.this, "Don't have text", Toast.LENGTH_SHORT).show();
                }else{
                    for (String sen:listSentences){
                        String newSen = sen.replace("'","''");
//                        Log.e("FFF",newSen);
                        MainActivity.database.QueryData("INSERT INTO PronounceA VALUES(null, '"+newSen.toString()+"')");
                    }
//                    Log.e("FFF",listSentences.get(0));
                    startActivity(new Intent(ChooseFileActivity.this, ResourceActivity.class));
                    finish();
                    Toast.makeText(getApplicationContext(), "Add file success", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    /**
     * Func - read a text file in android (ex: abc.txt)
     * Ref: https://stackoverflow.com/questions/12421814/how-can-i-read-a-text-file-in-android
     */

    public String readTextFile(String filePath){
//        Toast.makeText(ChooseFileActivity.this, "Path: "+ filePath, Toast.LENGTH_SHORT).show();
        File file = new File(filePath);
        //Read text from file
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
//                    new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return text.toString();
    }

    //Func - save text file
    public void saveTextFile(String content){
        File file = new File(getBaseContext().getFilesDir(),"hello.txt");
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
//            outputStream = openFileOutput("hello.txt", Context.MODE_PRIVATE);
//            for(int i=0;i<content.length;i++){
//                outputStream.write(content[i].getBytes());
//                if(i< content.length-1){
//                    outputStream.write("\n".getBytes());
//                }
//            }
            outputStream.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Material file picker
    private void chooseFile(){
        this.openPicker();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       switch (requestCode){
           case REQUEST_CODE:

               break;
           default:
               break;
       }
    }

    //
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && requestCode == FilePicker.STORAGE_PERMISSIONS) {
            openPicker();
        }
    }

    private void openPicker() {
        new FilePicker.Builder(this, new OnSelectFileListener() {
            @Override
            public void onSelect(File file) {
                //handle
                edtFileName.setText(file.getPath());

                //get text from file
                String text = readTextFile(file.getPath());

                //split text
                String[] sentences = text.split("\\.");
                Log.e("DDD",text);
                Log.e("EEE",sentences[0].trim().toString());
                String text2 = "";
                listSentences.clear();
                for(int i=0;i<sentences.length;i++){
                    text2+="["+i+"]: "+sentences[i].trim()+"\n";
                    listSentences.add(sentences[i].trim().toString());
                }
                //Set the text
//                txtvContent.setText(text);

                //set on list view
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_2,android.R.id.text1,listSentences);
                listDevideSentences.setAdapter(adapter);
            }
        })
                //this method let you decide how far user can go up in the directories tree
                .mainDirectory(Environment.getExternalStorageDirectory().getPath())
                //this method let you choose what types of files user will see in the picker
                .fileType(FileType.TEXT)
                //this method let you hide files, only directories will be visible for user
                .hideFiles(false)
                //this method let you decide which directory user will see after picker opening
                .directory(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS)
                .show();
    }


    /**
     *
     */

}
