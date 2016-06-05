package com.bluewave.reservation.base;

import android.support.v4.app.Fragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Developer on 2016-06-06.
 */
public class BaseFragment extends Fragment {

    protected SweetAlertDialog getProgressDialog() {
        SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        dialog.setCancelable(false);

        return dialog;
    }
}
