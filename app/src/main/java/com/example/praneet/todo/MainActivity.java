package com.example.praneet.todo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

    private ArrayList<ToDo> todos = new ArrayList<>();
    private FloatingActionButton add;

    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private FirebaseAuth firebaseAuth;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("users").child(uid);

        System.out.println(todos.size());

        add = findViewById(R.id.add_todo);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_Todo.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });



        // Read from the database
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()){
//                    ToDo user = item_snapshot.getValue(ToDo.class);
//                    todos.add(user);
//                }
//
//                initRecyclerView();
//                //String value = dataSnapshot.getValue(String.class);
//                //Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Toast.makeText(MainActivity.this, "Failed to read value", Toast.LENGTH_SHORT).show();
//                //Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()){
                    ToDo user = item_snapshot.getValue(ToDo.class);
                    if (!user.isChecked()) {
                        todos.add(0, user);
                    }
                    else {
                        todos.add(todos.size(), user);
                    }
                }

                initRecyclerView();
                //String value = dataSnapshot.getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "Failed to read value", Toast.LENGTH_SHORT).show();
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(todos, uid);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void addTodo(String title, String subtitle) {
        ToDo toDo = new ToDo(title, subtitle,false);
        dbRef.child(Integer.toString(todos.size() + 1)).setValue(toDo);
        todos.add(0, toDo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final String title = data.getStringExtra("title");
                final String subtitle = data.getStringExtra("subtitle");

                addTodo(title, subtitle);

                initRecyclerView();
            }
        }
    }
}
