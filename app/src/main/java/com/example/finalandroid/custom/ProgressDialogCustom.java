package com.example.finalandroid.custom;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogCustom {
    private ProgressDialog progress;
    public ProgressDialogCustom(Context context) {
        progress = new ProgressDialog(context);
        progress.setMessage("Loading...");
        progress.setInverseBackgroundForced(false);
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
    }
    public void show(){
        progress.show();
    }
    public void hide(){
        progress.dismiss();
    }
}
