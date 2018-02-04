package com.qiscus.internship.sudutnegeri.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.ui.splashscreen.SplashscreenActivity;

/**
 * Created by Vizyan on 2/3/2018.
 */

public class Popup {

    TextView tvPopupMsg, tvPopupType;
    private PopupListener popupListener;

    public Popup(PopupListener popupListener){
        this.popupListener = popupListener;
    }

    public void PopupWindow(Context context, String parameter, String message){
        String tag = "Popup-PopupWindow";
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (parameter.equals("success")){
                View layout = inflater.inflate(R.layout.layout_popup_success, null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                pw.setOutsideTouchable(true);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                tvPopupType = layout.findViewById(R.id.tvPopupSType);
                tvPopupMsg = layout.findViewById(R.id.tvPopupSMsg);
                tvPopupType.setText("Berhasil");
                tvPopupMsg.setText(message);

                popupListener.PopupSuccess();

            } else {
                View layout = inflater.inflate(R.layout.layout_popup_failed, null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                pw.setOutsideTouchable(true);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                tvPopupMsg = layout.findViewById(R.id.tvPopupFMsg);
                tvPopupType = layout.findViewById(R.id.tvPopupFType);
                tvPopupType.setText("Gagal");
                tvPopupMsg.setText(message);
            }
        } catch (Exception e) {
            Log.d(tag, e.getMessage());
        }
    }

    public interface PopupListener {
        void PopupSuccess();

    }
}
