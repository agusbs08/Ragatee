package com.hackaton.bri.ragatee.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.app.AppPreference;

public class BaseFragment extends Fragment {
    private ProgressDialog mProgressDialog;
    public AppPreference mAppPreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppPreference = new AppPreference(getContext());
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.msg_please_wait));
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void launchActivity(Class<?> cls) {
        Intent i = new Intent(getActivity(), cls);
        startActivity(i);
    }

    public void launchActivity(Bundle bundle, Class<?> cls) {
        Intent i = new Intent(getActivity(), cls);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        startActivity(i);
    }

    public void showProgressDialog() {
        if (mProgressDialog != null) {
            try {
                mProgressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
