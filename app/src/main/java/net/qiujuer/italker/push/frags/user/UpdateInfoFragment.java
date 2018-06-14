package net.qiujuer.italker.push.frags.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.EditText;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.italker.common.app.Application;
import net.qiujuer.italker.common.app.PresenterFragment;
import net.qiujuer.italker.common.widget.PortraitView;
import net.qiujuer.italker.factory.Factory;
import net.qiujuer.italker.factory.net.UploadHelper;
import net.qiujuer.italker.factory.presenter.user.UpdateInfoContract;
import net.qiujuer.italker.factory.presenter.user.UpdateInfoPresenter;
import net.qiujuer.italker.push.R;
import net.qiujuer.italker.push.activities.MainActivity;
import net.qiujuer.italker.push.frags.media.GalleryFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * 用户更新信息的节目
 */
public class UpdateInfoFragment extends PresenterFragment<UpdateInfoContract.Presenter> implements UpdateInfoContract.View {

    @BindView(R.id.im_portraitView)
    PortraitView mPortrait;

    @BindView(R.id.im_sex)
    ImageView imSex;
    @BindView(R.id.edit_desc)
    EditText editDesc;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mSubmit;



    private String mPortraitPath;
    private boolean isMan = true;

    public UpdateInfoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }

    @OnClick(R.id.im_portraitView)
    void onPortraitClick() {
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectedListener() {
                    @Override
                    public void onSelectedImage(String path) {
                        UCrop.Options options = new UCrop.Options();
                        // 设置图片处理的格式JPEG
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        // 设置压缩后的图片精度
                        options.setCompressionQuality(96);

                        // 得到头像的缓存地址
                        File dPath = Application.getPortraitTmpFile();

                        // 发起剪切
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                                .withAspectRatio(1, 1) // 1比1比例
                                .withMaxResultSize(520, 520) // 返回最大的尺寸
                                .withOptions(options) // 相关参数
                                .start(getActivity());
                    }
                })
                // show 的时候建议使用getChildFragmentManager，
                // tag GalleryFragment class 名
                .show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    @OnClick(R.id.im_sex)
    void onSexClick(){
        isMan  = !isMan;
        Drawable drawable  = getResources().getDrawable(isMan?R.drawable.ic_sex_man:R.drawable.ic_sex_woman);
        imSex.setImageDrawable(drawable);
        imSex.getBackground().setLevel(isMan?0:1);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            Application.showToast(R.string.data_rsp_error_unknown);
        }
    }


    /**
     * 加载Uri到当前的头像中
     *
     * @param uri Uri
     */
    private void loadPortrait(Uri uri) {
        mPortraitPath = uri.getPath();
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public void showLoading() {
        super.showLoading();
        mLoading.start();
        editDesc.setEnabled(false);
        mPortrait.setEnabled(false);
        imSex.setEnabled(false);
        mSubmit.setEnabled(false);
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        mLoading.stop();
        editDesc.setEnabled(true);
        mPortrait.setEnabled(true);
        imSex.setEnabled(true);
        mSubmit.setEnabled(true);
    }



    @Override
    public void updateSucceed() {
        MainActivity.show(getContext());
        getActivity().finish();
    }

    @Override
    protected UpdateInfoContract.Presenter initPresenter() {
        return new UpdateInfoPresenter(this);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick(){
        String  desc = editDesc.getText().toString();


        mPresenter.update(mPortraitPath,desc,isMan);

    }
}
