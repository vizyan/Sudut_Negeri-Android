package com.qiscus.internship.sudutnegeri.adapter.user;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;

/**
 * Created by Vizyan on 1/14/2018.
 */

class UserViewHolder extends RecyclerView.ViewHolder {

    private TextView tvItemUName;
    private ImageView ivItemU;
    private LinearLayout llItemU;

    public UserViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvItemUName = itemView.findViewById(R.id.tvItemUName);
        llItemU = itemView.findViewById(R.id.llItemU);
        ivItemU = itemView.findViewById(R.id.ivItemU);
    }

    public void bind(final DataUser dataUser, final UserListener listener) {
        listener.displayImageUser(ivItemU, dataUser);
        tvItemUName.setText(dataUser.getName());

        llItemU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClick(dataUser);
            }
        });
    }
}
