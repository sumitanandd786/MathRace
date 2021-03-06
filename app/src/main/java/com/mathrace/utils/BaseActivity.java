package com.mathrace.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.mathrace.R;
import com.mathrace.interfaces.AddFragmentCallBack;
import com.mathrace.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/7/2017.
 */

public class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, AddFragmentCallBack {

    protected int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 20;
    protected int MY_PERMISSION_REQUEST_WRITE_CONTACTS = 30;
    protected int MY_PHOTO_TAGGING_PERMISSIONS = 40;

    protected final String[] permissionsNeededForPhotoTagging = new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};

    String[] deniedPermissionsAmongPhotoTagging;

    protected void requestRunTimePermissions(final Activity activity, final String[] permissions, final int customPermissionConstant) {
        if (permissions.length == 1) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {

                Snackbar.make(findViewById(android.R.id.content), "App needs permission to work", Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(activity, permissions, customPermissionConstant);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permissions[0]}, customPermissionConstant);
            }
        } else if (permissions.length > 1 && customPermissionConstant == MY_PHOTO_TAGGING_PERMISSIONS) {
            if (getDeniedPermissionsAmongPhototaggingPermissions().length == 1) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, deniedPermissionsAmongPhotoTagging[0])) {
                    Snackbar.make(findViewById(android.R.id.content), "App needs permission to work", Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    ActivityCompat.requestPermissions(activity, deniedPermissionsAmongPhotoTagging, customPermissionConstant);
                                }
                            }).show();
                } else {
                    ActivityCompat.requestPermissions(activity, deniedPermissionsAmongPhotoTagging, customPermissionConstant);
                }
            } else if (getDeniedPermissionsAmongPhototaggingPermissions().length > 1) {
                if (isFirstTimeAskForPhotoTaggingPermission()) {
                    ActivityCompat.requestPermissions(activity, deniedPermissionsAmongPhotoTagging, customPermissionConstant);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "This functionality needs multiple app permissions", Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(activity, deniedPermissionsAmongPhotoTagging, customPermissionConstant);
                                }
                            }).show();
                }

            }
        }
    }

    protected boolean isFirstTimeAskForPhotoTaggingPermission() {
        SharedPreferences sharedPreferences = getSharedPreferences("permissionasks", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("PHOTO_FIRST_PERMISSION", true);
        if (isFirstTime) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("PHOTO_FIRST_PERMISSION", false);
            editor.commit();
        }
        return isFirstTime;
    }

    protected boolean checkWhetherAllPermissionsPresentForPhotoTagging() {
        for (String permission : permissionsNeededForPhotoTagging) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    protected String[] getDeniedPermissionsAmongPhototaggingPermissions() {

        final List<String> deniedPermissions = new ArrayList<String>();
        for (String permission : permissionsNeededForPhotoTagging) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        this.deniedPermissionsAmongPhotoTagging = deniedPermissions.toArray(new String[deniedPermissions.size()]);
        return deniedPermissionsAmongPhotoTagging;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.appPermissions);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.appPermissions:
                startAppPermissions();
                break;
            case R.id.appSettings:
//                Fragment fragment = getSupportFragmentManager().findFragmentByTag(SettingFragment.class.getSimpleName());

                SettingFragment settingFragment = (SettingFragment) getSupportFragmentManager().findFragmentByTag(SettingFragment.class.getSimpleName());
                if (settingFragment == null || !settingFragment.isVisible()) {
                    settingFragment = new SettingFragment();
                }
                String transactionName = settingFragment.getClass().getSimpleName();
                changeFragment(settingFragment, !settingFragment.isVisible(), transactionName, transactionName);
                break;
            default:
                break;
        }
        return true;
    }

    private void startAppPermissions() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void changeFragment(Fragment fragment, boolean addToBackStack, String transactionName, String tag) {
        try {
            if (AppUtils.isActivityAvailable(BaseActivity.this)) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentFrame, fragment, tag);
                if (addToBackStack)
                    fragmentTransaction.addToBackStack(transactionName);
                fragmentTransaction.commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
