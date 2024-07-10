package com.example.appbanhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Utils.Utils;
import com.example.appbanhang.adapter.LoaiSPadapter;
import com.example.appbanhang.model.List_bg;
import com.example.appbanhang.model.LoaiSP;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ArrayList<LoaiSP> productRequest;
    ListView listViewManHinhChinh, listView;
    DrawerLayout drawerLayout;
    LoaiSPadapter loaiSPadapter;
    ArrayList<LoaiSP> mamloaisp;
    ArrayList<List_bg> list_bgs;
    ApiBanHang apiBanHang;
    Retrofit retrofit;
    Gson gson;
    TextView spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mamloaisp = new ArrayList<>();

        // Thực hiện ánh xạ các thành phần giao diện
        anhxa();
        ClickItem();

        // Khai báo list và khởi tạo adapter
//        mamloaisp = new ArrayList<>();
//        mamloaisp.add(new LoaiSP("" + 1, "jjjj","jjjj"));
//        loaiSPadapter = new LoaiSPadapter(this, mamloaisp);
//        Log.d("TAG", "ddddd: " + mamloaisp.size());
//        listView.setAdapter(loaiSPadapter);

        spm = findViewById(R.id.tv_spm);
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiBanHang = retrofit.create(ApiBanHang.class);


        ActionBar();
        if (isConnected(this)) {
            Toast.makeText(getApplicationContext(), "Ket noi thanh cong", Toast.LENGTH_SHORT).show();
            ActionViewFlipeper();
            getLoaiSanPham();
        } else {
            Toast.makeText(getApplicationContext(), "Ket noi khong thanh cong", Toast.LENGTH_SHORT).show();
        }

    }
    private void getLoaiSanPham() {
        Call<JsonObject> objgetBrands = apiBanHang.getallProduct();
        // Thực hiện gọi
        objgetBrands.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (jsonObject != null) {
                        JsonArray jsonArray = jsonObject.getAsJsonArray("result");
                        ArrayList<LoaiSP> ListLloaisp = new ArrayList<>();
                        Gson gson = new Gson();
                        for (JsonElement element : jsonArray) {
                            LoaiSP LoaiSP = gson.fromJson(element, LoaiSP.class);
                            ListLloaisp.add(LoaiSP);
                        }

                        if (!ListLloaisp.isEmpty()) {

                            for (int i = 0; i < ListLloaisp.size(); i++) {
                                String aa = ListLloaisp.get(i).getId();
                                String bb = ListLloaisp.get(i).getName();
                                String cc = ListLloaisp.get(i).getImage();

                            mamloaisp.add(new LoaiSP(aa,bb,cc));
                            }
                            loaiSPadapter = new LoaiSPadapter(getApplicationContext(), mamloaisp);
                            listView.setAdapter(loaiSPadapter);

                            Log.d("aaa", "lll" + mamloaisp);
                        } else {
                            Log.d("aaa", "Danh sách ListLloaisp trống");
                        }
                        // Xử lý danh sách ListLloaisp ở đây
//                        loaiSPadapter.updateData(mamloaisp);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("TAG", "Không thể kết nối đến máy chủ: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });



//        compositeDisposable.add(apiBanHang.getLoaiSp()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        loaiSpModel -> {
//                            if (loaiSpModel.isSuccess()) {
//                                Toast.makeText(getApplicationContext(), loaiSpModel.getResult().get(0).getTensp(), Toast.LENGTH_SHORT).show();
//                            }
//                        },
//                        throwable -> {
//                            if (throwable instanceof com.google.gson.stream.MalformedJsonException) {
//                                // Xử lý lỗi JSON không hợp lệ
//                                Log.e("MyApp", "Lỗi JSON không hợp lệ", throwable);
//                                Toast.makeText(getApplicationContext(), "Lỗi JSON không hợp lệ", Toast.LENGTH_SHORT).show();
//                            } else {
//                                // Xử lý các trường hợp ngoại lệ khác
//                                Log.e("MyApp", "Lỗi khác xảy ra", throwable);
//                                Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//              ));


    }

    private void ActionViewFlipeper(){
        Call<List_bg> callList = apiBanHang.getListBackground();

        callList.enqueue(new Callback<List_bg>() {
            @Override
            public void onResponse(Call<List_bg> call, Response<List_bg> response) {
                if (response.isSuccessful()) {
                    List_bg list_bg = response.body();
                    if (list_bg != null) {
                        ArrayList<List_bg> list = list_bg.getResult();
                        if (list != null) {
                            // Xử lý list ở đây
                            list_bgs = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                String id = list.get(i).getId();
                                String anh = list.get(i).getImages_bg();
                                list_bgs.add(new List_bg(id, anh));
                                Log.d("TAG", "ketqua:  " + list_bgs.size());
                                ImageView imageView = new ImageView(getApplicationContext());
                                Glide.with(getApplicationContext()).load(list_bgs.get(i).getImages_bg()).into(imageView);
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                viewFlipper.addView(imageView);
                            }

                            viewFlipper.setFlipInterval(5000);
                            viewFlipper.setAutoStart(true);
                            Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
                            Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
                            viewFlipper.setInAnimation(slide_in);
                            viewFlipper.setInAnimation(slide_out);

                        } else {
                            Log.e("TAG", "Phản hồi data rỗng");
                            Toast.makeText(MainActivity.this, "Dữ liệu trả về rỗng", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("TAG", "Phản hồi rỗng");
                        Toast.makeText(MainActivity.this, "Dữ liệu trả về rỗng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("TAG", "Phản hồi không thành công: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List_bg> call, Throwable t) {
                Log.e("TAG", "Không thể kết nối đến máy chủ: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }
    private void anhxa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewfplipper);
        listView = findViewById(R.id.lv_sp);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navgationview);
        drawerLayout = findViewById(R.id.drawer);


    }
    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); //them quyen access...
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifi != null && wifi.isConnected() || mobile != null && mobile.isConnected()){
            return true;
        }else{
            return false;
        }

    }

    private void ClickItem(){
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Toast.makeText(MainActivity.this, "ĐT thứ " + i, Toast.LENGTH_SHORT).show();
           }
       });
    }


}