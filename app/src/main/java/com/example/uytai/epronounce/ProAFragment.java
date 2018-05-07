package com.example.uytai.epronounce;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProAFragment extends Fragment {

    @BindView(R.id.list_pro_a)
    ListView list_pro_a;
    @BindView(R.id.btn_add_sentence)
    FloatingActionButton btn_add_sentence;
    APronounceAdapter adapter;
    public static ArrayList<EPronounce> arrayList;

    public ProAFragment() {
        // Required empty public constructor
    }

    //


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_a, container, false);
        ButterKnife.bind(this, view);
        click();
        sql();
        return view;
    }

    //các sự kiện click
    private void click() {
        //add
        btn_add_sentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSelectWayA();
//                startActivity(new Intent(getActivity().getApplicationContext(), AddProAActivity.class));
//                getActivity().finish();
            }
        });

        //khi long click item trên listview thì show dialog hỏi có xóa không?
        list_pro_a.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogXoa(arrayList.get(position).getID(), arrayList.get(position).getContent());
                return true;
            }
        });

        //khi click item trên list view thì show screen cho đọc
        list_pro_a.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    //
    public void init() {
        arrayList = new ArrayList<>();
        adapter = new APronounceAdapter(getActivity().getApplicationContext(), arrayList);
        list_pro_a.setAdapter(adapter);
    }

    //show dialog khi xóa
    public void DialogXoa(final int id , String content){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Delete");
        dialog.setMessage("Do you want to delete "+content+"?");
        //
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.database.QueryData("DELETE FROM PronounceA WHERE Id='"+id+"'");
                sql();
            }
        });

        //
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //Show dialog select 2 way to add resource
    public void dialogSelectWayA(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_select_way);
        dialog.setTitle("Choose way");
        //
        Button dialogBtnOne = dialog.findViewById(R.id.btn_add_one);
        Button dialogBtnFile = dialog.findViewById(R.id.btn_add_file);

        dialogBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), AddProAActivity.class));
                dialog.dismiss();
                getActivity().finish();

            }
        });
        dialogBtnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add resource A by file
                Toast.makeText(getActivity(), "Choose file", Toast.LENGTH_SHORT).show();
                //
                dialog.dismiss();
                startActivity(new Intent(getActivity().getApplicationContext(), ChooseFileActivity.class));

            }
        });
        dialog.show();
    }

    //
    private void sql() {
        //database.QueryData("INSERT INTO PronounceA VALUES(null, 'My name is Uy')");
        init();
        //
        Cursor data = MainActivity.database.getData("SELECT * FROM PronounceA");
        while (data.moveToNext()){
            int id = data.getInt(0);
            String content = data.getString(1);
            arrayList.add(new EPronounce(id, content));
        }
        adapter.notifyDataSetChanged();
    }
}
