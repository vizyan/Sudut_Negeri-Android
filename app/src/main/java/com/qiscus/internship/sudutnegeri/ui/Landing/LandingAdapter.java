package com.qiscus.internship.sudutnegeri.ui.Landing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.Car;
import com.qiscus.internship.sudutnegeri.data.model.Project;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vizyan on 03/01/18.
 */

public class LandingAdapter extends RecyclerView.Adapter<LandingAdapter.ProjekViewHolder> {

    private Context context;
    private List<Project> projekList;

    public LandingAdapter(Context context){
        this.context = context;
    }

    public void setData(List<Project> projekList){
        this.projekList = projekList;
        notifyDataSetChanged();
    }

    @Override
    public ProjekViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);
        return new ProjekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjekViewHolder holder, int position) {
        Project project = projekList.get(position);
        holder.tvProjectName.setText(project.getNameProject());

        //Picasso.with(context)
        //        .load(car.getImageUrl())
        //        .into(holder.imgProject);
    }

    @Override
    public int getItemCount() {
        if (projekList == null) return 0;
        return projekList.size();
    }

    public class ProjekViewHolder extends RecyclerView.ViewHolder {
        TextView tvProjectName;
        ImageView imgProject;

        public ProjekViewHolder(View itemView) {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.textNameProject);
            imgProject = itemView.findViewById(R.id.img_project);
        }
    }
}
