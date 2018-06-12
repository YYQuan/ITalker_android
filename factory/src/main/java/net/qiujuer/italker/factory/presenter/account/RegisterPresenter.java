package net.qiujuer.italker.factory.presenter.account;

import android.text.TextUtils;

import net.qiujuer.italker.common.Common;
import net.qiujuer.italker.factory.data.helper.AccountHelper;
import net.qiujuer.italker.factory.model.api.RegisterModel;
import net.qiujuer.italker.factory.presenter.BasePresenter;

import java.util.regex.Pattern;

/**
 * Created by Yqquan on 2018/6/12.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

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

            if(!checkMobile(phone)){
                //提示
            }else if(name.length()<2){

            }else if(password.length()<6){

            }else{
                //网络请求
                RegisterModel  model   = new RegisterModel(phone ,password,name);
                AccountHelper.register(model);
            }

    }
}
