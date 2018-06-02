package com.qiscus.internship.sudutnegeri.adapter.recent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.qiscus.internship.sudutnegeri.R;
import com.qiscus.internship.sudutnegeri.data.model.DataProject;
import com.qiscus.internship.sudutnegeri.data.model.DataUser;
import com.qiscus.sdk.data.model.QiscusChatRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vizyan on 2/1/2018.
 */

public class RecentAdapter extends RecyclerView.Adapter<RecentViewHolder> implements Filterable {

    private List<QiscusChatRoom> qiscusChatRooms;
    private List<QiscusChatRoom> qiscusChatRoomsFiltered;
    private RecentListener recentListener;
    private DataUser dataUser;

    public RecentAdapter(List<QiscusChatRoom> qiscusChatRooms, DataUser dataUser){
        this.qiscusChatRooms = qiscusChatRooms;
        this.dataUser = dataUser;
        qiscusChatRoomsFiltered = qiscusChatRooms;
    }

    @Override
    public RecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_room, parent, false);

        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentViewHolder holder, int position) {
        QiscusChatRoom qiscusChatRoom = qiscusChatRoomsFiltered.get(position);
        holder.bind(qiscusChatRoom, recentListener, dataUser);
    }

    @Override
    public int getItemCount()
    {
       if (qiscusChatRoomsFiltered==null)return 0;
       return qiscusChatRoomsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    qiscusChatRoomsFiltered = qiscusChatRooms;
                } else {
                    List<QiscusChatRoom> filteredList = new ArrayList<>();
                    for (QiscusChatRoom row : qiscusChatRooms) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getLastComment().getMessage().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    qiscusChatRoomsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = qiscusChatRoomsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }

    public void setAdapterListener(RecentListener recentListener){
        this.recentListener = recentListener;
    }
}
