package net.qiujuer.italker.factory.presenter.user;

import android.text.TextUtils;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.R;
import net.qiujuer.italker.factory.data.DataSource;
import net.qiujuer.italker.factory.data.helper.UserHelper;
import net.qiujuer.italker.factory.model.api.user.UserUpdateModel;
import net.qiujuer.italker.factory.model.card.UserCard;
import net.qiujuer.italker.factory.model.db.User;
import net.qiujuer.italker.factory.net.UploadHelper;
import net.qiujuer.italker.factory.presenter.BaseContract;
import net.qiujuer.italker.factory.presenter.BasePresenter;
import net.qiujuer.italker.factory.presenter.account.LoginContract;

/**
 * Created by Yqquan on 2018/6/14.
 */

public class UpdateInfoPresenter extends BasePresenter<UpdateInfoContract.View>
        implements UpdateInfoContract.Presenter, DataSource.Callback<UserCard>{
    public UpdateInfoPresenter(UpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void update(final String photoFilePath, final String desc, final boolean isMan) {
        start();
        final UpdateInfoContract.View view = getView();

        if(TextUtils.isEmpty(photoFilePath)||TextUtils.isEmpty(desc)){
            view.showError(R.string.data_account_update_invalid_parameter);
        }else{
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    String url = UploadHelper.uploadPortrait(photoFilePath);
                    if(TextUtils.isEmpty(url)){
                        view.showError(R.string.data_upload_error);
                    }else{
                        UserUpdateModel model  = new UserUpdateModel("",url,desc,isMan? User.SEX_MAN:User.SEX_WOMAN);
                        UserHelper.update(model,UpdateInfoPresenter.this);
                    }
                }
            });
        }


    }

    @Override
    public void onDataLoaded(UserCard userCard) {
        final UpdateInfoContract.View view = getView();

        if(view ==null){
            return;
        }


        //线程切换
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.updateSucceed();
            }
        });

    }

    @Override
    public void onDataNotAvailable(final int str) {
        final UpdateInfoContract.View view = getView();

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
