package com.example.appbanhang.retrofit;



import com.example.appbanhang.model.List_bg;
import com.example.appbanhang.model.LoaiSP;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("get_loai_sp.php")
    Call<JsonObject> getallProduct();

    @GET("list_bg.php")
    Call<List_bg> getListBackground();
}

