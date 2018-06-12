package net.qiujuer.italker.factory.presenter.account;

import net.qiujuer.italker.factory.presenter.BaseContract;

import java.util.BitSet;

/**
 * Created by Yqquan on 2018/6/12.
 */

public class RegisterContract {
    public interface  View extends BaseContract.View<RegisterContract.Presenter>{
        //注册成功
        void registerSuccess();


    }

    public interface  Presenter extends BaseContract.Presenter {
        boolean checkMobile(String phone);
        //发起注册
        void register(String phone ,String name ,String password);
    }
}
