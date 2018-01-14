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

    private TextView tvName;
    private ImageView imgUser;
    private LinearLayout linearLayout;

    public UserViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvName = itemView.findViewById(R.id.tvNameUser);
        linearLayout = itemView.findViewById(R.id.llItemUser);
    }

    public void bind(final DataUser dataUser, final UserListener listener) {
        tvName.setText(dataUser.getName());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClick(dataUser);
            }
        });
    }
}
