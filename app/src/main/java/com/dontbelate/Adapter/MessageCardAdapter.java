package com.dontbelate.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dontbelate.Class.Couple;
import com.dontbelate.Class.Message;
import com.dontbelate.MatchingActivities.MatchingDetails;
import com.dontbelate.MatchingActivities.MessageActivity;
import com.dontbelate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Cheng on 14/5/17.
 */

public class MessageCardAdapter extends RecyclerView.Adapter<MessageCardAdapter.MessageHolder> {
    private List<Message> messages;
    private Context context;
    private String id;
    private String name;
    private Message message;

    public MessageCardAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public MessageCardAdapter.MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_card_message, parent, false);
        return new MessageCardAdapter.MessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MessageCardAdapter.MessageHolder holder, int position) {
        message = messages.get(position);
        holder.mTitle.setText(message.getTitle());
        holder.mTime.setText(message.getSendTime());
        holder.mContent.setText(message.getContent());
        id = message.getSender();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView photo;
        TextView mTitle;
        TextView mContent;
        TextView mTime;
        Button mDetail;
        Button mAccept;
        Button mReject;

        public MessageHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.messageCard);
            photo = (ImageView) itemView.findViewById(R.id.messageImage);
            mTitle = (TextView) itemView.findViewById(R.id.textTitle);
            mContent = (TextView) itemView.findViewById(R.id.textContent);
            mTime = (TextView) itemView.findViewById(R.id.textTime);
            mDetail = (Button) itemView.findViewById(R.id.btnDetail);
            mAccept = (Button) itemView.findViewById(R.id.btnAccept);
            mReject = (Button) itemView.findViewById(R.id.btnReject);


            // Set on click listener for detail button
            mDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get name of a specific user
                    DatabaseReference nameReference = FirebaseDatabase.getInstance().getReference().child("users").child(id).child("name");
                    nameReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            name = dataSnapshot.getValue().toString();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(context, "Failed retrieve data, please try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(itemView.getContext(), MatchingDetails.class);

                    i.putExtra("key", name);
                    i.putExtra("id", id);
                    i.putExtra("disable", "true");
                    itemView.getContext().startActivity(i);
                }
            });

            mReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show dialog
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    // Reject
                                    message.setResult("reject");
                                    message.setStatus("response");
                                    message.setResponseTime(Calendar.getInstance().getTime().toString());
                                    DatabaseReference rejectReferenceSender = FirebaseDatabase.getInstance().getReference().child("message").child(id).child(message.getId());
                                    rejectReferenceSender.setValue(message);
                                    DatabaseReference rejectReferenceReceiver = FirebaseDatabase.getInstance().getReference().child("message").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(message.getId());
                                    rejectReferenceReceiver.setValue(message);
                                    ((Activity) context).finish();
                                    itemView.getContext().startActivity(new Intent(context, MessageActivity.class));
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirm");
                    builder.setMessage("Once you click YES, this request will be rejected and you will not able to revoke your decision, are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });

            mAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    // accept
                                    message.setResult("allow");
                                    message.setResult("response");
                                    message.setResponseTime(Calendar.getInstance().getTime().toString());
                                    DatabaseReference rejectReferenceSender = FirebaseDatabase.getInstance().getReference().child("message").child(id).child(message.getId());
                                    rejectReferenceSender.setValue(message);
                                    DatabaseReference rejectReferenceReceiver = FirebaseDatabase.getInstance().getReference().child("message").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(message.getId());
                                    rejectReferenceReceiver.setValue(message);
                                    Couple couple = new Couple();
                                    long timeStamp = System.currentTimeMillis() / 1000;
                                    couple.setId(Long.toString(timeStamp));
                                    couple.setStatus("processing");
                                    couple.setStart(Calendar.getInstance().getTime().toString());
                                    couple.setPartyOneId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    couple.setPartyTwoId(id);
                                    DatabaseReference coupleReference = FirebaseDatabase.getInstance().getReference().child("couple").child(Long.toString(timeStamp));
                                    coupleReference.setValue(couple);
                                    DatabaseReference myDetailReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    myDetailReference.child("coupleId").setValue(Long.toString(timeStamp));
                                    DatabaseReference coupleDetailReference = FirebaseDatabase.getInstance().getReference().child("users").child(id);
                                    coupleDetailReference.child("coupleId").setValue(Long.toString(timeStamp));
                                    ((Activity) context).finish();
                                    itemView.getContext().startActivity(new Intent(context, MessageActivity.class));
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("WARNING");
                    builder.setMessage("Once you click YES, you will become a pair of simulated couple with the requester, and all other requests will be rejected. I hope this is your deliberate decision, because you do not have any chance to revoke your decision. Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                }
            });
        }
    }
}
