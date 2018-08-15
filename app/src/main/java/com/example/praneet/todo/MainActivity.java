package com.example.praneet.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> item_title = new ArrayList<>();
    private ArrayList<String> item_subtitle = new ArrayList<>();
    private ArrayList<Boolean> checkbox = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTitles();

    }

    private void initTitles(){

        item_title.add("Hello");
        item_subtitle.add("24/2/2018");
        checkbox.add(false);

        item_title.add("OK");
        item_subtitle.add("25/2/2018");
        checkbox.add(false);

        item_title.add("Bye");
        item_subtitle.add("26/2/2018");
        checkbox.add(false);

        item_title.add("Now");
        item_subtitle.add("27/2/2018");
        checkbox.add(false);

        item_title.add("Bro");
        item_subtitle.add("28/2/2018");
        checkbox.add(false);

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(item_title, item_subtitle, checkbox, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
