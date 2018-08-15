package com.example.praneet.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Todo extends AppCompatActivity {

    private EditText title, subtitle;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        title = findViewById(R.id.add_title);
        subtitle = findViewById(R.id.add_subtitle);
        add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("subtitle", subtitle.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
