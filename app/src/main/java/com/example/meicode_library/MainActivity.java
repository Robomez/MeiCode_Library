package com.example.meicode_library;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAllBooks, btnCurrent, btnReading, btnWish, btnFav, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnAllBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllBooksActivity.class);
                startActivity(intent);
            }
        });

        btnReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlrReadBookActivity.class);
                startActivity(intent);
            }
        });

        btnWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WishBookActivity.class);
                startActivity(intent);
            }
        });

        btnCurrent.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ReadingBookActivity.class);
            startActivity(intent);
        });

        btnFav.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FavBookActivity.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("My take on book library app from MeiCode's Android for Beginners free 'Full' Course. " +
                    "\n Please visit goole.com so I can test the webView instance.");
            builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                    intent.putExtra("url", "https://google.com/");
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.create().show();
        });

        Utils.getInstance(this);
    }

    private void initViews() {
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnCurrent = findViewById(R.id.btnReading);
        btnReading = findViewById(R.id.btnAlrRead);
        btnWish = findViewById(R.id.btnWish);
        btnFav = findViewById(R.id.btnFavorites);
        btnAbout = findViewById(R.id.btnAbout);
    }
}