package net.qiujuer.italker.factory.data.helper;



import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.model.api.RspModel;
import net.qiujuer.italker.factory.model.api.account.AccountRspModel;
import net.qiujuer.italker.factory.model.api.user.UserUpdateModel;
import net.qiujuer.italker.factory.model.card.UserCard;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.net.Network;
import net.qiujuer.italker.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yqquan on 2018/6/14.
 */

public class UserHelper {
    //更新用户信息
    public static void update (UserUpdateModel model , final DataSource.Callback<UserCard>  callback){
        RemoteService service = Network.remote();
        Call<RspModel<UserCard>> call  = service.userUpdate(model);

        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard>  rspModel = response.body();
                if(rspModel.success()){
                    UserCard  userCard = rspModel.getResult();
                    User user =  userCard.build();
                    user.save();
                    callback.onDataLoaded(userCard);
                }else{
                    Factory.decodeRspCode(rspModel,callback);
                }

            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {

                callback.onDataNotAvailable(R.string.data_network_error);

            }
        });
    }


}
