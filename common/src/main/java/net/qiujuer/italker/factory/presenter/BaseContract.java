package net.qiujuer.italker.factory.presenter;

import net.qiujuer.italker.common.widget.recycler.RecyclerAdapter;

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



        void destroy();
    }

    // 基本的一个列表的View的职责
    interface RecyclerView<T extends Presenter, ViewMode> extends View<T> {
        // 界面端只能刷新整个数据集合，不能精确到每一条数据更新
        // void onDone(List<User> users);

        // 拿到一个适配器，然后自己自主的进行刷新
        RecyclerAdapter<ViewMode> getRecyclerAdapter();

        // 当适配器数据更改了的时候触发
        void onAdapterDataChanged();
    }
}
