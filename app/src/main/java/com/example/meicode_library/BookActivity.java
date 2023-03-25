package com.example.meicode_library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";
    private ImageView imgBook;
    private Button btnReading, btnWish, btnAlrRead, btnFav;
    private TextView txtBookName, txtAuthor, txtPages, txtDesc, txtVisitUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

        Intent intent = getIntent();
        if (intent != null) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                handleIncomingBook(bookId);
            }
        }
    }

    private void handleIncomingBook(int bookId) {
        Book incomingBook = Utils.getInstance(this).getBookById(bookId);
        if (incomingBook != null) {
            setData(incomingBook);

            handleAlrRead(incomingBook);
            handleWish(incomingBook);
            handleReading(incomingBook);
            handleFav(incomingBook);

            txtVisitUrl.setOnClickListener(view -> {
                Intent intent1 = new Intent(BookActivity.this, WebsiteActivity.class);
                intent1.putExtra("url", incomingBook.getVisitUrl());
                startActivity(intent1);
            });
        }
    }

    private void handleReading(Book book) {
        ArrayList<Book> readingBooks = Utils.getInstance(this).getReadingBooks();
        boolean alrReadingBook = false;

        for (Book b: readingBooks) {
            if (b.getId() == book.getId()) {
                alrReadingBook = true;
                break;
            }
        }

        if (alrReadingBook) {
            btnReading.setEnabled(false);
        } else {
            btnReading.setOnClickListener(view -> {
                if (Utils.getInstance(BookActivity.this).addReading(book)) {
                    Toast.makeText(BookActivity.this, "Added to now reading list", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, ReadingBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "Smth's wrong, try'gain", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void handleWish(Book book) {
        ArrayList<Book> wishBooks = Utils.getInstance(this).getWishBooks();
        boolean alrWishedBook = false;

        for (Book b: wishBooks) {
            if (b.getId() == book.getId()) {
                alrWishedBook = true;
                break;
            }
        }

        if (alrWishedBook) {
            btnWish.setEnabled(false);
        } else {
            btnWish.setOnClickListener(view -> {
                if (Utils.getInstance(BookActivity.this).addWished(book)) {
                    Toast.makeText(BookActivity.this, "Added to wishlist", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, WishBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "Something's wrong, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleAlrRead(Book book) {
        ArrayList<Book> alrReadBooks = Utils.getInstance(this).getAlrReadBooks();

        boolean alrReadBook = false;

        for (Book b: alrReadBooks) {
            if (b.getId() == book.getId()) {
                alrReadBook = true;
                break;
            }
        }

        if (alrReadBook) {
            btnAlrRead.setEnabled(false);
        } else {
            btnAlrRead.setOnClickListener(view -> {
                if (Utils.getInstance(BookActivity.this).addAlrRead(book)) {
                    Toast.makeText(BookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, AlrReadBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "Smth went wrong, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleFav(Book incomingBook) {
        ArrayList<Book> favBooks = Utils.getInstance(this).getFavBooks();
        boolean alrFavBook = false;

        for (Book b: favBooks) {
            if (b.getId() == incomingBook.getId()) {
                alrFavBook = true;
                break;
            }
        }

        if (alrFavBook) {
            btnFav.setEnabled(false);
        } else {
            btnFav.setOnClickListener(view -> {
                if (Utils.getInstance(BookActivity.this).addFav(incomingBook)) {
                    Toast.makeText(BookActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookActivity.this, FavBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "SmthWntWrg,TrAgn", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtDesc.setText(book.getLongDesc());
        txtPages.setText(String.valueOf(book.getPages()));

        Glide.with(this).asBitmap().load(book.getImageUrl()).into(imgBook);
    }

    private void initViews() {
        imgBook = findViewById(R.id.imgBook);

        btnReading = findViewById(R.id.btnAddReading);
        btnWish = findViewById(R.id.btnAddWish);
        btnAlrRead = findViewById(R.id.btnAddAlrRead);
        btnFav = findViewById(R.id.btnAddFav);

        txtBookName = findViewById(R.id.txtBookName);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtPages = findViewById(R.id.txtPages);
        txtDesc = findViewById(R.id.txtDesc);
        txtVisitUrl = findViewById(R.id.txtVisitUrl);
    }
}