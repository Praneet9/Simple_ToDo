package com.example.praneet.todo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;

//    public ArrayList<String> item_title = new ArrayList<>();
//    private ArrayList<String> item_subtitle = new ArrayList<>();
//    private ArrayList<Boolean> checkbox = new ArrayList<>();
    private ArrayList<ToDo> todos = new ArrayList<>();
    private FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = findViewById(R.id.add_todo);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_Todo.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        initTitles();

    }

    private void initTitles(){

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(todos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final String title = data.getStringExtra("title");
                final String subtitle = data.getStringExtra("subtitle");
                ToDo toDo = new ToDo(title, subtitle,false);
                todos.add(0, toDo);
//                item_title.add(0, title);
//                item_subtitle.add(0, subtitle);
//                checkbox.add(0, false);
                initRecyclerView();
            }
        }
    }
}
