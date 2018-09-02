package com.acrs.buddies.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale;

public class PermissionHelper {

    private static final String TAG = "PermissionHelper";
    private static PermissionHelper permissionHelper;
    private static int REQUEST_CODE;

    private Activity activity;
    private Fragment fragment;
    private String[] permissions;
    private PermissionCallback mPermissionCallback;
    private boolean showRational;

    //=========Constructors - START=========
    private PermissionHelper(Activity activity, Fragment fragment, String[] permissions, int requestCode) {
        this.activity = activity;
        this.fragment = fragment;
        this.permissions = permissions;
        this.REQUEST_CODE = requestCode;
        checkIfPermissionPresentInAndroidManifest();
    }

    public PermissionHelper(Activity activity, String[] permissions, int requestCode) {
        this.activity = activity;
        this.permissions = permissions;
        this.REQUEST_CODE = requestCode;
        checkIfPermissionPresentInAndroidManifest();
    }

    public PermissionHelper(Fragment fragment, String[] permissions, int requestCode) {
        this.fragment = fragment;
        this.permissions = permissions;
        this.REQUEST_CODE = requestCode;
        checkIfPermissionPresentInAndroidManifest();
    }

    private void checkIfPermissionPresentInAndroidManifest() {
        for (String permission : permissions) {
            if (hasPermission(permission) == false) {
                throw new RuntimeException("Permission (" + permission + ") Not Declared in manifest");
            }
        }
    }

    //=========Constructors- END=========
    public void request(PermissionCallback permissionCallback) {
        this.mPermissionCallback = permissionCallback;
        if (checkSelfPermission(permissions) == false) {
            showRational = shouldShowRational(permissions);
            if (activity != null)
                ActivityCompat.requestPermissions(activity, filterNotGrantedPermission(permissions), REQUEST_CODE);
            else
                fragment.requestPermissions(filterNotGrantedPermission(permissions), REQUEST_CODE);
        } else {
            Log.e(TAG, "PERMISSION: Permission Granted");
            if (mPermissionCallback != null)
                mPermissionCallback.onPermissionGranted();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {
            boolean denied = false;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    denied = true;
                    break;
                }
            }

            if (denied) {
                boolean currentShowRational = shouldShowRational(permissions);
                if (showRational == false && currentShowRational == false) {
                    Log.d(TAG, "PERMISSION: Permission Denied By System");
                    if (mPermissionCallback != null)
                        mPermissionCallback.onPermissionDeniedBySystem();
                } else {
                    Log.i(TAG, "PERMISSION: Permission Denied");
                    if (mPermissionCallback != null)
                        mPermissionCallback.onPermissionDenied();
                }
            } else {
                Log.i(TAG, "PERMISSION: Permission Granted");
                if (mPermissionCallback != null)
                    mPermissionCallback.onPermissionGranted();
            }
        }
    }

    //====================================
    //====================================

    public interface PermissionCallback {
        void onPermissionGranted();

       void onPermissionDenied();

      void onPermissionDeniedBySystem();
    }


    private <T extends Context> T getContext() {
        if (activity != null)
            return (T) activity;
        return (T) fragment.getContext();
    }

    /**
     * Return list that is not granted and we need to ask for permission
     *
     * @param permissions
     * @return
     */
    private String[] filterNotGrantedPermission(String[] permissions) {
        List<String> notGrantedPermission = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                notGrantedPermission.add(permission);
            }
        }
        return notGrantedPermission.toArray(new String[notGrantedPermission.size()]);
    }


    public boolean checkSelfPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * Checking if there is need to show rational for group of permissions
     *
     * @param permissions
     * @return
     */
    private boolean shouldShowRational(String[] permissions) {
        boolean currentShowRational = false;
        for (String permission : permissions) {

            if (activity != null) {
                if (shouldShowRequestPermissionRationale(activity, permission) == true) {
                    currentShowRational = true;
                    break;
                }
            } else {
                if (fragment.shouldShowRequestPermissionRationale(permission) == true) {
                    currentShowRational = true;
                    break;
                }
            }
        }
        return currentShowRational;
    }

    //===================
    public boolean hasPermission(String permission) {
        try {
            Context context = activity != null ? activity : fragment.getActivity();
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    if (p.equals(permission)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Open current application detail activity so user can change permission manually.
     */
    public void openAppDetailsActivity() {
        if (getContext() == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + getContext().getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        getContext().startActivity(i);
    }
}
