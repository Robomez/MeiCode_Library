package com.example.meicode_library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AlrReadBookActivity extends AppCompatActivity {

    public static final String ALRREAD_KEY = "already read books";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alr_read_book);

        TextView txtNoAlrRead = findViewById(R.id.txtNoAlrRead);
        RecyclerView recyclerView = findViewById(R.id.bookRecView);
        BookRecViewAdapter adapter = new BookRecViewAdapter(this, ALRREAD_KEY);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setBooks(Utils.getInstance(this).getAlrReadBooks());

        if (adapter.getItemCount() == 0) {
            txtNoAlrRead.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtNoAlrRead.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}