package net.qiujuer.italker.factory.presenter;

/**
 * Created by Yqquan on 2018/6/12.
 */

public class BasePresenter<T extends   BaseContract.View> implements BaseContract.Presenter {
    private T mView;

    public BasePresenter (T view){
        setView(view);

    }


    @SuppressWarnings("unchecked")
    protected void setView(T view){
        this.mView = view;
        mView.setPresenter(this);
    }

    protected final T getView(){
        return mView;
    }


    @Override
    public void start() {
        T view =  mView;
        if(view !=null){
            view.showLoading();

        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void destroy() {
        T view =  mView;
        mView = null;
        if(view !=null){
            view.setPresenter(null);
        }

    }
}
