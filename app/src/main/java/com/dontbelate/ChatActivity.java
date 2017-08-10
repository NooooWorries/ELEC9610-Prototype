package com.dontbelate;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelate.Class.ChatMessage;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {
    private FirebaseListAdapter<ChatMessage> adapter;
    private String coupleId;
    private String type;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            coupleId = extras.getString("id");
            type = extras.getString("type");
            provider = extras.getString("provider");

            FloatingActionButton fab =
                    (FloatingActionButton) findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText input = (EditText) findViewById(R.id.input);

                    // Read the input field and push a new instance
                    // of ChatMessage to the Firebase database
                    if (type.equals("couple"))
                        FirebaseDatabase.getInstance()
                                .getReference().child("chat").child(coupleId)
                                .push()
                                .setValue(new ChatMessage(input.getText().toString(),
                                        FirebaseAuth.getInstance()
                                                .getCurrentUser()
                                                .getEmail())
                                );
                    else if (type.equals("consult"))
                        FirebaseDatabase.getInstance()
                                .getReference().child("chat").child(provider).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .push()
                                .setValue(new ChatMessage(input.getText().toString(),
                                        FirebaseAuth.getInstance()
                                                .getCurrentUser()
                                                .getEmail())
                                );

                    // Clear the input
                    input.setText("");
                }
            });
            ListView listOfMessages = (ListView) findViewById(R.id.list_of_messages);
            if (type.equals("couple"))
                adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                        R.layout.content_message, FirebaseDatabase.getInstance().getReference().child("chat").child(coupleId)) {
                    @Override
                    protected void populateView(View v, ChatMessage model, int position) {
                        // Get references to the views of message.xml
                        TextView messageText = (TextView) v.findViewById(R.id.message_text);
                        TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                        TextView messageTime = (TextView) v.findViewById(R.id.message_time);

                        // Set their text
                        messageText.setText(model.getMessageText());
                        messageUser.setText(model.getMessageUser());

                        // Format the date before showing it
                        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                                model.getMessageTime()));
                    }
                };
            else if (type.equals("consult")) {
                adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                        R.layout.content_message, FirebaseDatabase.getInstance().getReference().child("chat").child(provider).child(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    @Override
                    protected void populateView(View v, ChatMessage model, int position) {
                        // Get references to the views of message.xml
                        TextView messageText = (TextView) v.findViewById(R.id.message_text);
                        TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                        TextView messageTime = (TextView) v.findViewById(R.id.message_time);

                        // Set their text
                        messageText.setText(model.getMessageText());
                        messageUser.setText(model.getMessageUser());

                        // Format the date before showing it
                        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                                model.getMessageTime()));
                    }
                };
            }

            listOfMessages.setAdapter(adapter);
        }
    }
}
