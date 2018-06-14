package net.qiujuer.italker.factory.presenter.user;

import net.qiujuer.italker.factory.presenter.BaseContract;

/**
 * Created by Yqquan on 2018/6/14.
 */

public interface UpdateInfoContract {
     interface Presenter extends BaseContract.Presenter{
        void update(String photoFilePath,String desc, boolean isMan);

    }


     interface  View  extends BaseContract.View<UpdateInfoContract.Presenter>{
        void updateSucceed();
    }
}
