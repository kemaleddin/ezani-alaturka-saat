package com.kemalettinsargin.mylib.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.kemalettinsargin.mylib.LocaleHelper;
import com.kemalettinsargin.mylib.MyFragmentActivity;
import com.kemalettinsargin.mylib.objects.TypeFaces;
import com.semansoft.ezanisaat.R;


/**
 * Created by "كمال الدّين صارغين" on 06.12.2014.
 * و من الله توفیق
 */
public class MyFragment extends Fragment {
    public static final String KEY_RES_ID = "res_id";
    protected LayoutInflater mInflater;
    private View mRoot;
    private int mRootResId;
    private boolean isLoaded = false, isDataFetching = false;
    private String baseUrl;
    private boolean isEn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mRootResId = getArguments().getInt(KEY_RES_ID);
        isEn = LocaleHelper.isLocaleEn(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        mRoot = inflater.inflate(mRootResId, container, false);
        try {
            createItems();
        } catch (Exception e) {
//            FirebaseCrash.report(e);
            e.printStackTrace();
//            startActivity(new Intent(getActivity(), SplashActivity.class));
//            getActivity().finish();
        }
        return mRoot;
    }

    public void createItems() {

    }

    public void loadItems() {

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        baseUrl = getString(R.string.base_url);
    }

    public boolean isEn() {
        return isEn;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public View getChild(int id) {
        return mRoot.findViewById(id);
    }

    public View getRoot() {
        return mRoot;
    }

    public void setLoadingMessage(String message) {
        ((MyFragmentActivity) getActivity()).setLoadingMessage(message);
    }

    public void showLoading() {
        try {
            ((MyFragmentActivity) getActivity()).showLoading();
        } catch (Exception e) {
        }
    }

    public void dismissLoading() {
        try {
            ((MyFragmentActivity) getActivity()).dismissLoading();
        } catch (Exception e) {
        }
    }

    public String getEditStr(EditText editText) {
        return editText.getText().toString();
    }


    public float getHeight() {
        return ((MyFragmentActivity) getActivity()).getHeight();
    }

    public float getWidth() {
        return ((MyFragmentActivity) getActivity()).getWidth();
    }

    public float getScale() {
        return ((MyFragmentActivity) getActivity()).getScale();
    }

    public TypeFaces getTypeFaces() {
        return ((MyFragmentActivity) getActivity()).getTypeFaces();
    }

    public void showErrorDialog() {
        ((MyFragmentActivity) getActivity()).showErrorDialog();
    }

    public void showErrorDialog(String msg) {
        ((MyFragmentActivity) getActivity()).showErrorDialog(msg);
    }

    public void showErrorDialog(String msg, DialogInterface.OnClickListener listener) {
        ((MyFragmentActivity) getActivity()).showErrorDialog(msg, listener);
    }

    public void showErrorDialogRetry(DialogInterface.OnClickListener listener) {
        ((MyFragmentActivity) getActivity()).showErrorDialogRetry(listener);
    }

    public void showErrorDialogRetry(String msg, String tittle, DialogInterface.OnClickListener listener) {
        ((MyFragmentActivity) getActivity()).showErrorDialogRetry(msg, tittle, listener);
    }

    public void showInfo(String msg) {
        ((MyFragmentActivity) getActivity()).showInfo(msg);
    }

    public void showInfo(String msg, DialogInterface.OnClickListener listener) {
        ((MyFragmentActivity) getActivity()).showInfo(msg, listener);
    }

    public void showInfo1(String msg) {
        ((MyFragmentActivity) getActivity()).showInfo_1(msg, null);
    }

    public void showSuccess(String msg) {
        ((MyFragmentActivity) getActivity()).showSuccess(msg);
    }

    public void showSuccess(String msg, DialogInterface.OnClickListener listener) {
        ((MyFragmentActivity) getActivity()).showSuccess(msg, listener);
    }

    public MyFragmentActivity getAct() {
        return (MyFragmentActivity) getActivity();
    }

    public int getmRootResId() {
        return mRootResId;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public boolean isDataFetching() {
        return isDataFetching;
    }

    public void setDataFetching(boolean dataFetching) {
        isDataFetching = dataFetching;
    }

    public boolean canGoback(){
        return false;
    }

    public Gson getGson(){
        return getAct().getGson();
    }
}
