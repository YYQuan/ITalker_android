package net.qiujuer.italker.push.frags.account;


import android.content.Context;
import android.widget.EditText;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.italker.common.app.Fragment;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.factory.presenter.account.LoginContract;
import net.qiujuer.italker.factory.presenter.account.LoginPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录的界面
 */
public class LoginFragment extends PresenterFragment<LoginContract.Presenter> implements  LoginContract.View {
    @BindView(R.id.edit_phone)
    EditText mPhone;
    @BindView(R.id.edit_password)
    EditText mPassWord;



    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;

    private AccountTrigger mAccountTrigger;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 拿到我们的Activity的引用
        mAccountTrigger = (AccountTrigger) context;
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @OnClick(R.id.btn_submit)
    public void  onSubmitClick(){
        String  phone = mPhone.getText().toString();

        String  password = mPassWord.getText().toString();
        mPresenter.login(phone,password);

    }

    @OnClick(R.id.txt_go_register)
    void onShowRegisterClick(){
        mAccountTrigger.triggerView();
    }


    @Override
    public void showLoading() {
        super.showLoading();
        mLoading.start();

        mPhone.setEnabled(false);
        mPassWord.setEnabled(false);

        mSubmit.setEnabled(false);

    }

    @Override
    public void showError(int str) {
        super.showError(str);
        mLoading.stop();

        mPhone.setEnabled(true);
        mPassWord.setEnabled(true);
        mSubmit.setEnabled(true);
    }


    @Override
    public void loginSuccess() {
        MainActivity.show(getContext());
        getActivity().finish();
    }
}
