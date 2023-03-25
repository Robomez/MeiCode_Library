package com.example.meicode_library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

public class AllBooksActivity extends AppCompatActivity {

    public static final String ALL_BOOKS_KEY = "all books";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        RecyclerView booksRecView = findViewById(R.id.booksRecyclerView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, ALL_BOOKS_KEY);
        booksRecView.setAdapter(adapter);
        booksRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getInstance(this).getAllBooks());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}