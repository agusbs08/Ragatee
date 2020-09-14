package com.hackaton.bri.ragatee.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseActivity;
import com.hackaton.bri.ragatee.model.Transaksi;
import com.hackaton.bri.ragatee.model.User;
import com.hackaton.bri.ragatee.model.request.Login;
import com.hackaton.bri.ragatee.ui.home.HomeActivity;
import com.hackaton.bri.ragatee.ui.home.fragmentcart.CartFragment;
import com.hackaton.bri.ragatee.ui.home.fragmentprofile.ProfileFragment;
import com.hackaton.bri.ragatee.ui.home.fragmenttransaksi.TransaksiFragment;
import com.hackaton.bri.ragatee.ui.register.RegisterActivity;

public class LoginActivity extends BaseActivity implements LoginView {

    private final int RC_SIGN_IN = 234;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    private LoginPresenter presenter;
    private String fromPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initComponent();
    }

    private void initData() {
        this.fromPage = getIntent().getStringExtra("from_page");
    }

    private void initComponent(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        presenter = new LoginPresenter(this);
    }

    public void linearLoginOnClickListener(View view){
        startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    public void tvCloseOnClickListener(View view){
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.firebaseAuthWithGoogle(account, FirebaseAuth.getInstance(), this);
            } catch (ApiException e) {
                onMessage(e.getMessage());
            }
        }
    }

    @Override
    public void onSuccessGoogleAuth(FirebaseUser user) {
        //real
        Login login = new Login(user.getPhotoUrl().toString(), user.getDisplayName(), "INVESTOR", user.getEmail());
        //this is dummy
//        Login login = new Login("https://lh3.googleusercontent.com/a-/AAuE7mBcJhksNwN46BBC5WF_xBCm1il7k4fHG3LyeEgZ", "Ahok", "INVESTOR", "inv@gmail.com");
//        Log.d("Foto User", login.getName());
        presenter.authentication(login);
    }

    @Override
    public void onSuccessAuthentication(User user) {
                Log.d("Foto User", new Gson().toJson(user));
        mAppPreference.setUserLogin(user);
        mAppPreference.setUserLoginState(true);
        Bundle bundle = new Bundle();
        if(fromPage.equals(ProfileFragment.class.getName())) {
            bundle.putInt("state", 1);
        } else if(fromPage.equals(TransaksiFragment.class.getName())) {
            bundle.putInt("state", 2);
        } else if(fromPage.equals(CartFragment.class.getName())) {
            bundle.putInt("state", 4);
        }
        launchActivity(bundle, HomeActivity.class);
        finish();
    }

    @Override
    public void onFailureMessage(String message) {
        onMessage(message);
    }

    @Override
    public void onShowProgressDialog() {
        showProgressDialog();
    }

    @Override
    public void onHideProgressDialog() {
        hideProgressDialog();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
