package com.photosaloon.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.photosaloon.R;
import com.photosaloon.config.App;
import com.photosaloon.ui.base.BaseActivity;
import com.photosaloon.ui.login.LoginActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener{

    @BindView(R.id.toolbar_title_id)
    TextView toolbarTitle;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.view_tab_layout)
    TabLayout tabLayout;

    private TabAdapter adapter;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        if (App.getFirebaseUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {

            toolbarTitle.setText(R.string.title_toolbar_my_record);
            initTabs();
            loadMyRecord();

        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    private void initTabs() {

        adapter = new TabAdapter(getSupportFragmentManager(), this);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(TabAdapter.PAGE_MY_RECORD).setIcon(R.drawable.tabbar_ic_1_selector);
        tabLayout.getTabAt(TabAdapter.PAGE_NEW_RECORD).setIcon(R.drawable.tabbar_ic_2_selector);
        tabLayout.getTabAt(TabAdapter.PAGE_PROFILE).setIcon(R.drawable.tabbar_ic_3_selector);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateTitle(int position) {

        switch (position) {
            case TabAdapter.PAGE_MY_RECORD:

                getSupportFragmentManager().popBackStack();
                toolbarTitle.setText(R.string.title_toolbar_my_record);
                setTitle(R.string.title_toolbar_my_record);

                break;
            case TabAdapter.PAGE_NEW_RECORD:

                getSupportFragmentManager().popBackStack();
                toolbarTitle.setText(R.string.title_toolbar_record);
                setTitle(R.string.title_toolbar_record);

                break;

            case TabAdapter.PAGE_PROFILE:

                getSupportFragmentManager().popBackStack();
                toolbarTitle.setText(R.string.title_toolbar_profile);
                setTitle(R.string.title_toolbar_profile);

                break;
        }

    }

    private void loadMyRecord() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.logout_menu_item:
                showExitDialog();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    private void showExitDialog() {
        new MaterialDialog
                .Builder(this)
                .backgroundColorRes(R.color.dialog_bg)
                .titleColorRes(R.color.dialog_caption)
                .contentColorRes(R.color.dialog_text)
                .positiveColorRes(R.color.dialog_button)
                .negativeColorRes(R.color.dialog_button)
                .title(R.string.exit_title)
                .content(R.string.exit_text)
                .positiveText(R.string.dialog_yes)
                .onPositive((dialog, which) -> {
                    App.getFirebaseAuth().signOut();
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    startActivity(new Intent(this, LoginActivity.class));
                })
                .negativeText(R.string.dialog_cancel)
                .show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
