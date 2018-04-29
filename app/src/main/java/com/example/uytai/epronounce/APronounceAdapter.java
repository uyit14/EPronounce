package com.example.uytai.epronounce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uytai on 4/28/2018.
 */

public class APronounceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<EPronounce> list;

    public APronounceAdapter(Context context, ArrayList<EPronounce> list) {
        this.context = context;
        this.list = list;
        this.notifyDataSetChanged();
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
        TextView tvContent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_pro_a, null);
            viewHolder.tvContent = convertView.findViewById(R.id.tvContent);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final EPronounce cv = list.get(position);
        viewHolder.tvContent.setText(cv.getContent());
        return convertView;
    }
}
