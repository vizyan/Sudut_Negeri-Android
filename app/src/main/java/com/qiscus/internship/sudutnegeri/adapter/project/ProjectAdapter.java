package com.qiscus.internship.sudutnegeri.adapter.project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vizyan on 1/14/2018.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder> implements Filterable {

    private List<DataProject> dataProjectList;
    private List<DataProject> dataProjectsFiltered;
    private ProjectListener projectListener;

    public ProjectAdapter(List<DataProject> dataProjectList){
        this.dataProjectList = dataProjectList;
        dataProjectsFiltered = dataProjectList;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);

        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        DataProject dataProject = dataProjectsFiltered.get(position);
        //DataProject dataProject = getItem(position);
        holder.bind(dataProject, projectListener);
    }

    private DataProject getItem(int position) {
        return dataProjectList.get(position);
    }

    @Override
    public int getItemCount() {
        if (dataProjectsFiltered == null) return 0;
        return dataProjectsFiltered.size();
    }

    public void setAdapterListener(ProjectListener listener) {
        this.projectListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    dataProjectsFiltered = dataProjectList;
                } else {
                    List<DataProject> filteredList = new ArrayList<>();
                    for (DataProject row : dataProjectList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNameProject().toLowerCase().contains(charString.toLowerCase()) || row.getLocation().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    dataProjectsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataProjectsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataProjectsFiltered = (List<DataProject>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
