package com.example.meicode_library;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnAllBooks, btnReading, btnAlrRead, btnWish, btnFav, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnAllBooks.setOnClickListener(this);
        btnReading.setOnClickListener(this);
        btnAlrRead.setOnClickListener(this);
        btnWish.setOnClickListener(this);
        btnFav.setOnClickListener(this);
        btnAbout.setOnClickListener(this);

        Utils.getInstance(this);
    }

    private void initViews() {
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnReading = findViewById(R.id.btnReading);
        btnAlrRead = findViewById(R.id.btnAlrRead);
        btnWish = findViewById(R.id.btnWish);
        btnFav = findViewById(R.id.btnFav);
        btnAbout = findViewById(R.id.btnAbout);
    }

    private void startButtonActivity (Class<?> c) {
        Intent intent = new Intent(MainActivity.this, c);
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnAllBooks:
                startButtonActivity(AllBooksActivity.class);
                break;
            case R.id.btnReading:
                startButtonActivity(ReadingBookActivity.class);
                break;
            case R.id.btnAlrRead:
                startButtonActivity(AlrReadBookActivity.class);
                break;
            case R.id.btnWish:
                startButtonActivity(WishBookActivity.class);
                break;
            case R.id.btnFav:
                startButtonActivity(FavBookActivity.class);
                break;
            case R.id.btnAbout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("My take on book library app from MeiCode's Android for Beginners free 'Full' Course. " +
                        "\n Please visit google.com so I can test the webView instance.");
                builder.setPositiveButton("Go", (dialogInterface, i) -> {
                    Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                    intent.putExtra("url", "https://google.com/");
                    startActivity(intent);
                });
                builder.setNegativeButton("Decline", (dialogInterface, i) -> {
                });
                builder.create().show();
                break;
        }
    }
}