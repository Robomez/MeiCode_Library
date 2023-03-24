package com.example.meicode_library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReadingBookActivity extends AppCompatActivity {
    public static final String READING_KEY = "reading now";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_book);

        setTitle("Reading now");

        TextView txtNoReading = findViewById(R.id.txtNoBooksReading);
        RecyclerView recyclerView = findViewById(R.id.readingRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, READING_KEY);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getInstance(this).getReadingBooks());

        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            txtNoReading.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtNoReading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}