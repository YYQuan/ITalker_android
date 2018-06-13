package net.qiujuer.italker.factory.data.helper;

import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.RspModel;
import net.qiujuer.italker.factory.model.api.account.AccountRspModel;
import net.qiujuer.italker.factory.model.api.account.RegisterModel;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.net.NetWork;
import net.qiujuer.italker.factory.net.RemoteService;
import net.qiujuer.italker.factory.persistence.Account;

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
    public static void register(final RegisterModel model, final DataSource.Callback<User> callback){
        RemoteService service  = NetWork.getRetrofit().create(RemoteService.class);

        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);

        call.enqueue(new Callback<RspModel<AccountRspModel>>(){

            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call,
                                   Response<RspModel<AccountRspModel>> response) {
                RspModel<AccountRspModel> rspModel  = response.body();

                if(rspModel.success()){
                    AccountRspModel model = rspModel.getResult();
                    //获取我的信息
                    final User user = model.getUser();
                    //进行数据库写入  有多种存储方式
                    //1.直接保存  轻量级的这样保存没问题
                    user.save();
//                        //2
//                        FlowManager.getModelAdapter(User.class).save(user);
//                        //3
//                        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
//                        definition.beginTransactionAsync(new ITransaction() {
//                            @Override
//                            public void execute(DatabaseWrapper databaseWrapper) {
//                                FlowManager.getModelAdapter(User.class).save(user);
//                            }
//                        }).build();

                    //同步到XML的持久化当中
                    Account.login(model);

                    if(model.isBind()) {
                        //注册成功的回调
                        callback.onDataLoaded(user);
                    }else{
                        bindPush(callback);
                    }
                }else{
                    Factory.decodeRspCode(rspModel,callback);


                }
            }

            @Override
            public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }


    public static void bindPush(final DataSource.Callback<User>  callback){
        Account.setBind(true);
    }
}
