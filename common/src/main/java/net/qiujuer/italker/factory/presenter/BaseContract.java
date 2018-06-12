package net.qiujuer.italker.factory.presenter;

import android.support.annotation.StringRes;

/**
 *
 * MVP 模式中公共的contract
 * Created by Yqquan on 2018/6/12.
 */

public interface BaseContract {
    interface  View<T extends  Presenter> {

        void  showError(int str);

        void showLoading();

        void setPresenter(T presenter);

    }

    interface  Presenter{



        //公用的部分
        void start();

        void destory();
    }
}
