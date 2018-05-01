package com.example.uytai.epronounce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by uytai on 5/1/2018.
 */

public class WordsWrongAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public WordsWrongAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //
    private class ViewHolder{
        TextView tvWords;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WordsWrongAdapter.ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new WordsWrongAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_words_wrong, null);
            viewHolder.tvWords = convertView.findViewById(R.id.tv_words_wrong);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (WordsWrongAdapter.ViewHolder) convertView.getTag();
        }
        String words_wrong = list.get(position);
        viewHolder.tvWords.setText(words_wrong);
        return convertView;
    }
}
