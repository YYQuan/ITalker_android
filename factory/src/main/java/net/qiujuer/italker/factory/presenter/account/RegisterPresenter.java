package net.qiujuer.italker.factory.presenter.account;

import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.common.Common;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.data.helper.AccountHelper;
import net.qiujuer.italker.factory.model.api.account.RegisterModel;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.persistence.Account;
import net.qiujuer.italker.factory.presenter.BasePresenter;

import java.util.regex.Pattern;

/**
 * Created by Yqquan on 2018/6/12.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter,DataSource.Callback<User>{

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }


    @Override
    public boolean checkMobile(String phone) {

        return !TextUtils.isEmpty(phone)&&Pattern.matches(Common.Constance.REGEX_MOBILE,phone);
    }


    @Override
    public void register(String phone, String name, String password) {
            start();
            RegisterContract.View view = getView();

            if(!checkMobile(phone)){
                //提示
                view.showError(R.string.data_account_register_invalid_parameter_mobile);
            }else if(name.length()<2){
                view.showError(R.string.data_account_register_invalid_parameter_name);
            }else if(password.length()<6){
                view.showError(R.string.data_account_register_invalid_parameter_password);
            }else{
                //网络请求
                RegisterModel  model   = new RegisterModel(phone ,password,name, Account.getPushId());
                AccountHelper.register(model,this);
            }

    }

    //当网络请求成功 ，回调
    @Override
    public void onDataLoaded(User user) {

        final RegisterContract.View view = getView();

        if(view ==null){
            return;
        }


        //线程切换
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.registerSuccess();
            }
        });
    }

    //网络 请求失败 回调
    @Override
    public void onDataNotAvailable(final int str) {


        final RegisterContract.View view = getView();

        if(view ==null){
            return;
        }
        //线程切换
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(str);
            }
        });


    }
}
