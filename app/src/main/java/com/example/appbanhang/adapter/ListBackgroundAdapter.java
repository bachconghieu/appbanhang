package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.List_bg;

import java.util.ArrayList;

public class ListBackgroundAdapter extends BaseAdapter {
    private ArrayList<List_bg> list_bgs;
    private Context context;


    public ListBackgroundAdapter(Context context, ArrayList<List_bg> list){
        this.context = context;
        this.list_bgs = list;
    }



    @Override
    public int getCount() {
        return list_bgs.size();
    }

    @Override
    public Object getItem(int i) {
        return list_bgs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        ImageView imgAnhBg;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_sanpham, null);


        }
        return view;
    }



}
