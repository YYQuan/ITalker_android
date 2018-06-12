package net.qiujuer.italker.push.frags.account;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.factory.presenter.account.RegisterContract;
import net.qiujuer.italker.factory.presenter.account.RegisterPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 注册的界面
 */
public class RegisterFragment extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View {


    private AccountTrigger mAccountTrigger;


    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassWord;
    @BindView(R.id.edit_name)
    EditText mName;


    @BindView(R.id.loading)
    Loading  mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;


    @OnClick(R.id.btn_submit)
    public void  onSubmitClick(){
        String  phone = mPhone.getText().toString();
        String  name = mName.getText().toString();
        String  password = mPassWord.getText().toString();
        mPresenter.register(phone,name,password);
    }

    @OnClick(R.id.txt_go_login)
    void onShowLoginClick(){
        mAccountTrigger.triggerView();
    }


    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 拿到我们的Activity的引用
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }


    @Override
    public void registerSuccess() {
        //注册成功 ，则账户登录 ，
        // 跳转到MainActivity界面

        MainActivity.show(getContext());
        getActivity().finish();
    }


    @Override
    public void showLoading() {
        super.showLoading();
        mLoading.start();

        mPhone.setEnabled(false);
        mName.setEnabled(false);
        mPassWord.setEnabled(false);

        mSubmit.setEnabled(false);

    }

    @Override
    public void showError(int str) {
        super.showError(str);
        mLoading.stop();

        mPhone.setEnabled(true);
        mName.setEnabled(true);
        mPassWord.setEnabled(true);

        mSubmit.setEnabled(true);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
