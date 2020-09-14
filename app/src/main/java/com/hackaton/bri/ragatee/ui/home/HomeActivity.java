package com.hackaton.bri.ragatee.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hackaton.bri.ragatee.R;
import com.hackaton.bri.ragatee.base.BaseActivity;
import com.hackaton.bri.ragatee.helper.BottomNavigationViewHelper;
import com.hackaton.bri.ragatee.ui.home.fragmentbantuan.BantuanFragment;
import com.hackaton.bri.ragatee.ui.home.fragmentcart.CartFragment;
import com.hackaton.bri.ragatee.ui.home.fragmenthome.HomeFragment;
import com.hackaton.bri.ragatee.ui.home.fragmentprofile.ProfileFragment;
import com.hackaton.bri.ragatee.ui.home.fragmenttransaksi.TransaksiFragment;
import com.hackaton.bri.ragatee.ui.login.LoginActivity;

public class HomeActivity extends BaseActivity {

    private BottomNavigationView bnvHome;

    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        initComponent();
        setActionComponent();
        setRuleState();
    }

    private void initComponent() {
        bnvHome = findViewById(R.id.bnv_home);
        BottomNavigationViewHelper.disableShiftMode(bnvHome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initData(){
        Intent intent = getIntent();
        state = intent.getIntExtra("state", 0);
    }

    private void setActionComponent() {
        bnvHome.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_menu:
                        setFragment(new HomeFragment());
                        break;
                    case R.id.profile_menu:
                        if(mAppPreference.getUserLoginState()){
                            setFragment(new ProfileFragment());
                        } else {
                            toLoginPage(ProfileFragment.class.getName());
                        }
                        break;
                    case R.id.transaksi_menu:
                        if(mAppPreference.getUserLoginState()) {
                            setFragment(new TransaksiFragment());
                        } else {
                            toLoginPage(TransaksiFragment.class.getName());
                        }
                        break;
                    case R.id.bantuan_menu:
                        setFragment(new BantuanFragment());
                        break;
                }
                return true;
            }
        });
    }

    private void setRuleState() {
        switch (state){
            case 0:
                bnvHome.setSelectedItemId(R.id.home_menu);
                break;
            case 1:
                bnvHome.setSelectedItemId(R.id.profile_menu);
                break;
            case 2:
                bnvHome.setSelectedItemId(R.id.transaksi_menu);
                break;
            case 3:
                bnvHome.setSelectedItemId(R.id.bantuan_menu);
                break;
            case  4:
                if(mAppPreference.getUserLoginState()){
                    setFragment(new CartFragment());
                } else {
                    toLoginPage(CartFragment.class.getName());
                }
                break;
        }
    }

    private void setFragment(Fragment fragment) {
        replaceFragment(R.id.frame_home, fragment, fragment.getClass().getSimpleName(), null);
    }

    private void toLoginPage(String fromPage) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("from_page", fromPage);
        startActivity(intent);
        overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_down );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if(item.getItemId() == R.id.home_cart) {
            if(mAppPreference.getUserLoginState()){
                setFragment(new CartFragment());
            } else {
                toLoginPage(CartFragment.class.getName());
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
