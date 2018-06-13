package net.qiujuer.italker.factory.presenter.account;

import net.qiujuer.italker.factory.presenter.BaseContract;
import net.qiujuer.italker.factory.presenter.BasePresenter;

/**
 * Created by Yqquan on 2018/6/13.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements  LoginContract.Presenter {
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {

    }
}
