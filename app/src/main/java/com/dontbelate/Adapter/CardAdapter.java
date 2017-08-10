package com.dontbelate.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import com.dontbelate.Class.Message;
import com.dontbelate.Class.ShowCard;
import com.dontbelate.MatchingActivities.MatchPoolActivity;
import com.dontbelate.MatchingActivities.MatchingDetails;
import com.dontbelate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Cheng on 7/5/17.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private Context mContext;
    private List<ShowCard> albumList;
    private String key;
    private String id;

    public CardAdapter(Context mContext, List<ShowCard> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ShowCard album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(album.getGender());
        key = album.getName();
        id = album.getUserId();

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

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
        inflater.inflate(R.menu.menu_showcard, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
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
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_view:
                    Intent i = new Intent(mContext, MatchingDetails.class);
                    i.putExtra("key", key);
                    i.putExtra("id", id);
                    i.putExtra("disable", "false");
                    mContext.startActivity(i);
                    return true;
                case R.id.action_match_request:
                    final ProgressDialog progressDialog = new ProgressDialog(mContext);
                    progressDialog.setTitle("Sending......");
                    progressDialog.show();
                    Message message = new Message();
                    message.setSender(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    message.setReceiver(id);
                    message.setSendTime(Calendar.getInstance().getTime().toString());
                    message.setTitle("You have received a matching request");
                    message.setContent("A user invite you to match up with him/her, please click view to get more information about this user. " +
                            "And select accept or reject.");
                    message.setResult("waiting for response");
                    message.setStatus("no response");
                    long timeStamp = System.currentTimeMillis() / 1000;
                    message.setId(Long.toString(timeStamp));
                    DatabaseReference messageReferenceReceiver = FirebaseDatabase.getInstance().getReference().child("message").child(id).child(Long.toString(timeStamp));
                    message.setType("send");
                    DatabaseReference messageReferenceSender = FirebaseDatabase.getInstance().getReference().child("message").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Long.toString(timeStamp));
                    messageReferenceSender.setValue(message);
                    message.setType("receive");
                    messageReferenceReceiver.setValue(message);
                    progressDialog.hide();
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Request had already been sent to the receiver, good luck.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mContext.startActivity(new Intent(mContext, MatchPoolActivity.class));
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                default:
            }
            return false;
        }
    }
}
