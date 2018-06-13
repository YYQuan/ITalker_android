package net.qiujuer.italker.factory.net;

import android.text.TextUtils;

import net.qiujuer.italker.common.Common;
import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.persistence.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求封装
 * Created by Yqquan on 2018/6/12.
 */

public class Network {
    private static Network instance ;
    private Retrofit retrofit;

    static {

        instance = new Network();
    }

    private Network() {
    }

    public static Retrofit getRetrofit(){
        if(instance.retrofit!=null){
            return instance.retrofit;
        }

        OkHttpClient client  = new OkHttpClient.Builder()
                //给所有的请求添加一个拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original =  chain.request();

                        Request.Builder  builder = original.newBuilder();
                        if(!TextUtils.isEmpty(Account.getToken())){
                            builder.addHeader("token",Account.getToken());
                        }
                        //不是必须的 retrofit已经自动加上了
                        builder.addHeader("Content-Type","application/json");
                        Request  newRequest = builder.build();

                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();

        instance.retrofit= builder.baseUrl(Common.Constance.API_URL)
                //设置client
                .client(client)
                //设置Json解析器
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
        return instance.retrofit;
    }


    /**
     * 返回一个代理
     * @return
     */
    public static  RemoteService remote(){
        return Network.getRetrofit().create(RemoteService.class);
    }
}
