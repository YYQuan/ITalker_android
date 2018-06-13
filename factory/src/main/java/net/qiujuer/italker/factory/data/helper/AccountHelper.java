package net.qiujuer.italker.factory.data.helper;

import android.text.TextUtils;

import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.RspModel;
import net.qiujuer.italker.factory.model.api.account.AccountRspModel;
import net.qiujuer.italker.factory.model.api.account.LoginModel;
import net.qiujuer.italker.factory.model.api.account.RegisterModel;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.net.Network;
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
        RemoteService service  = Network.remote();

        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);

        call.enqueue(new AccountRspCallback(callback));
    }


    public static void login(final LoginModel model, final DataSource.Callback<User> callback){
        RemoteService service  = Network.remote();

        Call<RspModel<AccountRspModel>> call = service.accountLogin(model);

        call.enqueue(new AccountRspCallback(callback));
    }

    public static void bindPush(final DataSource.Callback<User>  callback){
        String pushID = Account.getPushId();
        if(TextUtils.isEmpty(pushID)){
            return;
        }

        RemoteService service = Network.remote();
        Call<RspModel<AccountRspModel>> call  = service.accountBind(pushID);
        call.enqueue(new AccountRspCallback(callback));

//        Account.setBind(true);
    }

    private static class AccountRspCallback implements  Callback<RspModel<AccountRspModel>> {
        DataSource.Callback callback;

        public AccountRspCallback(DataSource.Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call,
                               Response<RspModel<AccountRspModel>> response) {
            RspModel<AccountRspModel> rspModel = response.body();

            if (rspModel.success()) {
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

                if (model.isBind()) {
                    Account.setBind(true);
                    if (callback != null) {

                        //注册成功的回调
                        callback.onDataLoaded(user);
                    }
                } else {
                    bindPush(callback);
                }
            } else {
                Factory.decodeRspCode(rspModel, callback);


            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            if (callback != null) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        }
    }
}
