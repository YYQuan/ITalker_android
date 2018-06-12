package net.qiujuer.italker.factory.net;

import net.qiujuer.italker.common.Common;
import net.qiujuer.italker.factory.Factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求封装
 * Created by Yqquan on 2018/6/12.
 */

public class NetWork {
    public static Retrofit getRetrofit(){
        OkHttpClient client  = new OkHttpClient.Builder().build();

        Retrofit.Builder builder = new Retrofit.Builder();

        return builder.baseUrl(Common.Constance.API_URL)
                //设置client
                .client(client)
                //设置Json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
    }
}
