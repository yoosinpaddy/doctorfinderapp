package freaktemplate.nearbydoctor.chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import freaktemplate.nearbydoctor.R;
import freaktemplate.nearbydoctor.adapters.AdapterAllChat;
import freaktemplate.nearbydoctor.adapters.AdapterChatBBM;
import freaktemplate.nearbydoctor.doctorfinder.Login;
import freaktemplate.nearbydoctor.models.Message;
import freaktemplate.nearbydoctor.utils.Tools;

public class AllChatActivity extends AppCompatActivity {

    private View btn_send;
    private EditText et_content;
    private AdapterAllChat adapter;
    private RecyclerView recycler_view;
    String senderMail = "";
    String receiverMail = "";
    String specialities_id = "";
    String specialities_name = "";
    String specialities_mail = "";
    String my_id = "";
    FirebaseDatabase database;
    DatabaseReference myRef;
    String userId;
    private static final String MyPREFERENCES = "DoctorPrefrance";

    private ActionBar actionBar;
    List<Message> messageList=new ArrayList<>();
    List<Message> messageList2=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chat);
        Intent iv = getIntent();
        specialities_id = iv.getStringExtra("profile_id");
        specialities_name = iv.getStringExtra("specialities_name");
        specialities_mail = iv.getStringExtra("specialities_email");
        initToolbar();
        iniComponent();

        SharedPreferences s = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        userId = s.getString("email", "0");
        Log.e(TAG, "onCreate: "+userId );
        Log.e(TAG, "onCreate: "+specialities_mail );
        if (userId.contentEquals("0")) {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage("You must register/login to proceed")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(AllChatActivity.this, Login.class));
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
            return;
        }

        //FIREBASE
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        getDataFromFirebase(userId, false);
//        getDataFromFirebase(specialities_mail, false);

//        myRef.setValue("Hello, World!");
    }

    private void getDataFromFirebase(final String email_id, final boolean fromMe) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("message");
//        if (fromMe){
        ref.orderByChild("receiver").equals(email_id);// Read from the database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: "+fromMe +" sender:"+email_id);
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot == null)
                    return;
                sanitizeList(fromMe);
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Message message = s.getValue(Message.class);
                    message.setFromMe(fromMe);
                    if (message.getReceiver().contentEquals(email_id)){
                        messageList.add(message);
                        Log.e(TAG, "onDataChange: loop:"+s.getValue().toString() );
                    }
                    Log.e(TAG, "Value is: " + message.getId());
                }
                rearrange();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void rearrange() {
        Log.e(TAG, "rearrange: "+messageList.size());
        Collections.sort(messageList, new Comparator<Message>() {
            @Override
            public int compare(Message lhs, Message rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });
        Collections.reverse(messageList);
        filter();
    }
    private void filter() {
        Log.e(TAG, "filter: " );
        for (int i=0;i<messageList.size();i++) {
            if (!hasModel(messageList.get(i))&&!userId.contentEquals(messageList.get(i).getSender())){
                messageList2.add(messageList.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void sanitizeList(boolean fromMe) {
       messageList2.clear();
    }
    public boolean hasModel(Message message){
        boolean hasM=false;
        for (Message m:messageList2
             ) {
            if (m.getSender().contentEquals(message.getSender())){
                hasM=true;
                return hasM;
            }
        }
        return hasM;
    }

    private static final String TAG = "ChatActivity";

    public void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(specialities_name);
        Tools.setSystemBarColorInt(this, Color.parseColor("#0A7099"));
    }

    public void iniComponent() {
        recycler_view = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);

        adapter = new AdapterAllChat(this,messageList2);
        adapter.setOnItemClickListener(new AdapterAllChat.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Message obj, int position) {

                SharedPreferences s = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                userId = s.getString("email", "0");
                Intent i= new Intent(AllChatActivity.this,ChatActivity.class);
                i.putExtra("profile_id",obj.getSender());
                i.putExtra("specialities_name",obj.getSender());
                i.putExtra("specialities_email",obj.getSender());
                startActivity(i);
            }
        });
        recycler_view.setAdapter(adapter);
//        adapter.insertItem(new Message(adapter.getItemCount(), "Hello!", true, Tools.getFormattedTimeEvent(System.currentTimeMillis())));
//        adapter.insertItem(new Message(adapter.getItemCount(), "Hai..", false, Tools.getFormattedTimeEvent(System.currentTimeMillis())));

        btn_send = findViewById(R.id.btn_send);
        et_content = findViewById(R.id.text_content);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChat();
            }
        });
        et_content.addTextChangedListener(contentWatcher);
    }

    private void sendChat() {
        final String msg = et_content.getText().toString();
        String id=String.valueOf(System.currentTimeMillis());
        Message message= new Message(id,id,msg,true,true,userId,specialities_mail);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message").child(id);
        myRef.setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "onSuccess: " );
                et_content.setText("");
                recycler_view.scrollToPosition(adapter.getItemCount() - 1);
            }
        });

//        adapter.insertItem(new Message(adapter.getItemCount(), msg, true, Tools.getFormattedTimeEvent(System.currentTimeMillis())));
//        et_content.setText("");
//        recycler_view.scrollToPosition(adapter.getItemCount() - 1);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                adapter.insertItem(new Message(adapter.getItemCount(), msg, false, Tools.getFormattedTimeEvent(System.currentTimeMillis())));
//                recycler_view.scrollToPosition(adapter.getItemCount() - 1);
//            }
//        }, 1000);
        if (et_content.length() == 0) {
            btn_send.setEnabled(false);
        }
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        hideKeyboard();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private TextWatcher contentWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable etd) {
            if (etd.toString().trim().length() == 0) {
                btn_send.setEnabled(false);
            } else {
                btn_send.setEnabled(true);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_bbm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}