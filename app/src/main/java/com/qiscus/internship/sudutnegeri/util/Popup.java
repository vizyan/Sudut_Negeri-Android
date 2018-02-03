package com.qiscus.internship.sudutnegeri.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
/**
 * Created by Vizyan on 2/3/2018.
 */

public class Popup {

    TextView tvPopupMsg, tvPopupType, tvPopupSType;
    private PopupListener popupListener;

    public Popup(PopupListener popupListener){
        this.popupListener = popupListener;
    }

    public void PopupWindow(Context context, String messsage, String error){
        try {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (messsage.equals("success")){
                View layout = inflater.inflate(R.layout.layout_popup_success,
                        null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, false);
                pw.setOutsideTouchable(true);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                tvPopupSType = layout.findViewById(R.id.tvPopupSType);
                tvPopupSType.setText("Berhasil");

                popupListener.PopupSuccess();

            } else {
                View layout = inflater.inflate(R.layout.layout_popup_failed, null);
                final PopupWindow pw = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
                pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                tvPopupMsg = layout.findViewById(R.id.tvPopupFMsg);
                tvPopupType = layout.findViewById(R.id.tvPopupFType);
                tvPopupMsg.setText(error);
                tvPopupType.setText("Gagal Bergabung");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface PopupListener {
        void PopupSuccess();

    }
}
