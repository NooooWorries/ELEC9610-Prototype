package com.dontbelate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dontbelate.Class.ServiceDetail;
import com.dontbelate.ConsultActivities.ServiceDetailActivity;
import com.dontbelate.R;

import java.util.List;

/**
 * Created by Cheng on 7/5/17.
 */

public class ServiceCardAdapter extends RecyclerView.Adapter<ServiceCardAdapter.ServiceViewHolder> {
    private Context mContext;
    private List<ServiceDetail> albumList;
    private String key;
    private String id;

    public ServiceCardAdapter(Context mContext, List<ServiceDetail> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card_service, parent, false);

        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ServiceViewHolder holder, int position) {
        ServiceDetail album = albumList.get(position);
        holder.title.setText(album.getTitle());
        holder.count.setText("Sydney, $" + album.getPrice());
        key = album.getTitle();
        id = album.getConsultantId();

        // loading album cover using Glide library
        Glide.with(mContext).load(R.drawable.default_user).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_service, popup.getMenu());
        popup.setOnMenuItemClickListener(new ServiceMenuItemClickListener());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public ServiceViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    /**
     * Click listener for popup menu items
     */
    class ServiceMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public ServiceMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_view:
                    Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("id", id);
                    mContext.startActivity(intent);

                    return true;
                case R.id.action_match_request:

                default:
            }
            return false;
        }
    }
}
