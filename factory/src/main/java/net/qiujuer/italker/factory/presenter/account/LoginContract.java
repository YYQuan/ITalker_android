package net.qiujuer.italker.factory.presenter.account;

import net.qiujuer.italker.factory.presenter.BaseContract;

/**
 * Created by Yqquan on 2018/6/12.
 */

public interface LoginContract {
    interface  View extends BaseContract.View<LoginContract.Presenter>{
        //注册成功
        void loginSuccess();


    }

    interface  Presenter extends  BaseContract.Presenter{
        //发起登录
        void login(String phone, String name, String password);

    }
}
