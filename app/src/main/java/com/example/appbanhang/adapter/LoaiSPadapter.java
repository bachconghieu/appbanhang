package com.example.appbanhang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appbanhang.MainActivity;
import com.example.appbanhang.R;
import com.example.appbanhang.model.LoaiSP;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoaiSPadapter extends BaseAdapter {

    private ArrayList<LoaiSP> loaiSPList;
    private Context context;
    ApiBanHang apiBanHang;


    public LoaiSPadapter(Context context, ArrayList<LoaiSP> loaiSPList) {
        this.context = context;
        this.loaiSPList = loaiSPList;
    }

    @Override
    public int getCount() {
        return loaiSPList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiSPList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView textensp;
        ImageView imghinhanh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_sanpham, null);
            viewHolder.textensp = view.findViewById(R.id.item_tensp);
            viewHolder.imghinhanh = view.findViewById(R.id.item_image);


            view.setTag(viewHolder); // Gán ViewHolder làm tag cho view
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        LoaiSP loaiSP = loaiSPList.get(i);
        viewHolder.textensp.setText(loaiSP.getName());
        Glide.with(context).load(loaiSP.getImage()).into(viewHolder.imghinhanh);





        return view;
    }




}
