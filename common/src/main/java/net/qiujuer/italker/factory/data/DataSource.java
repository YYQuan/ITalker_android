package net.qiujuer.italker.factory.data;

import android.support.annotation.StringRes;

/**
 * Created by Yqquan on 2018/6/12.
 */

public interface DataSource {

    interface  Callback<T> extends SuccessCallback<T>,FailedCallback<T>{

    }


    interface SuccessCallback<T> {
        //网络请求成功
        void onDataLoaded(T t);
    }

    interface FailedCallback<T>{
        //网络请求失败
        void onDataNotAvailable(@StringRes int str);
    }
}
