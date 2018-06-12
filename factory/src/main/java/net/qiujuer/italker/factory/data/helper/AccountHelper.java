package net.qiujuer.italker.factory.data.helper;

import android.text.style.SuperscriptSpan;

import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.RspModel;
import net.qiujuer.italker.factory.model.api.account.AccountRspModel;
import net.qiujuer.italker.factory.model.api.account.RegisterModel;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.net.NetWork;
import net.qiujuer.italker.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yqquan on 2018/6/12.
 */

public class AccountHelper {




    /**
     * 注册
     * @param model
     * @param callback
     */
    public static void register(RegisterModel model, final DataSource.Callback<User> callback){
        RemoteService service  = NetWork.getRetrofit().create(RemoteService.class);

        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);

        call.enqueue(new Callback<RspModel<AccountRspModel>>(){

            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call,
                                   Response<RspModel<AccountRspModel>> response) {
                RspModel<AccountRspModel> rspModel  = response.body();

                if(rspModel.success()){
                    AccountRspModel model = rspModel.getResult();
                    if(model.isBind()) {
                        User user = model.getUser();
                        //进行数据库写入
                        //注册成功的回调
                        callback.onDataLoaded(user);
                    }else{
                        bindPushID(callback);
                    }
                }else{


                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }


    public static void bindPushID(final DataSource.Callback<User>  callback){

    }
}
