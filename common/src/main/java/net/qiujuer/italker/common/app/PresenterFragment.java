package net.qiujuer.italker.common.app;

import android.content.Context;

import net.qiujuer.italker.factory.presenter.BaseContract;

/**
 * Created by Yqquan on 2018/6/12.
 */

public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends  Fragment implements BaseContract.View<Presenter> {


    protected  Presenter  mPresenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化Presenter
        initPresenter();
    }

    protected abstract Presenter  initPresenter();

    @Override
    public void showError(int str) {
        if(mPlaceHolderView != null){
            mPlaceHolderView. triggerError(str);
        }else{
            Application.showToast(str);
        }

    }

    @Override
    public void showLoading() {
        if(mPlaceHolderView!= null){
            mPlaceHolderView.triggerLoading();
        }
    }


    @Override
    public void setPresenter(Presenter presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!= null){
            mPresenter.destroy();
        }
    }
}
