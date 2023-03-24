package com.example.meicode_library;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static Utils instance;
    private SharedPreferences sharedPrefs;
    Gson gson = new Gson();

    private Type type = new TypeToken<ArrayList<Book>>(){}.getType();

    private static final String ALLBOOKS_JSON_KEY = "all_books";
    private static final String READING_JSON_KEY = "reading_books";
    private static final String ALRREAD_JSON_KEY = "already_read_books";
    private static final String WISH_JSON_KEY = "wish_books";
    private static final String FAV_JSON_KEY = "favorites_books";

    // Utils constructor, init and get instance methods
    private Utils(Context context) {
        sharedPrefs = context.getSharedPreferences("altDB", Context.MODE_PRIVATE);

        if (getAllBooks() == null) {
            initData();
        }

        if (getReadingBooks() == null) {
            editorPutNCommitString(READING_JSON_KEY, new ArrayList<Book>());
        }

        if (getWishBooks() == null) {
            editorPutNCommitString(WISH_JSON_KEY, new ArrayList<Book>());
        }

        if (getAlrReadBooks() == null) {
            editorPutNCommitString(ALRREAD_JSON_KEY, new ArrayList<Book>());
        }

        if (getFavBooks() == null) {
            editorPutNCommitString(FAV_JSON_KEY, new ArrayList<Book>());
        }
    }

    private void initData() {
        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book(1, "Bookatcha", "Autho", 234,
                "https://www.shutterstock.com/image-vector/creative-book-cover-design-vintage-600w-1115305040.jpg",
                "some Book", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                "https://www.abebooks.com/signed/Book-Number-One-Stuart-Gordon-Pacific/30952771231/bd"));
        books.add(new Book(2, "Book two", "Authora", 23,
                "https://img.freepik.com/free-psd/book-hardcover-mockup-three-views_125540-226.jpg",
                "second book", "Some other Really Real Book", "https://www.amazon.com/Book-Number-Christopher-Maxwell-Thomas/dp/1534901051"));
        books.add(new Book(3, "Bookrust", "Outor", 123,
                "https://d3ui957tjb5bqd.cloudfront.net/uploads/images/4/49/49666.pinterest.jpg",
                "third Book", "Ain't that a Really Real Book?", "https://www.amazon.com/Book-Number-Three-Paper-Production/dp/B0000CJ5AC"));

        editorPutNCommitString(ALLBOOKS_JSON_KEY, books);
    }

    public static Utils getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);
        }
        return instance;
    }

    // SharedPreferences Editor methods
    private void editorPutNCommitString(String key, ArrayList<Book> booksList) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(key, gson.toJson(booksList));
        editor.commit();
    }

    private void editorRemNPutNCommitString(String key, ArrayList<Book> booksList) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(key);
        editor.putString(key, gson.toJson(booksList));
        editor.commit();
    }

    // Individual book methods. getBookById, book add & remove from lists
    public Book getBookById(int id) {
        ArrayList<Book> books = getAllBooks();
        if (books != null) {
            for (Book b: books) {
                if (b.getId() == id) {
                    return b;
                }
            }
        }
        return null;
    }

    public boolean addReading(Book book) {
        ArrayList<Book> books = getReadingBooks();
        if (books != null) {
            if (books.add(book)) {
                editorRemNPutNCommitString(READING_JSON_KEY, books);
                return true;
            }
        }
        return false;
    }

    public boolean addWished(Book book) {
        ArrayList<Book> books = getWishBooks();
        if (books != null) {
            if (books.add(book)) {
                editorRemNPutNCommitString(WISH_JSON_KEY, books);
                return true;
            }
        }
        return false;
    }

    public boolean addAlrRead(Book book) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        ArrayList<Book> books = getAlrReadBooks();
        if (books != null) {
            if (books.add(book)) {
                editorRemNPutNCommitString(ALRREAD_JSON_KEY, books);
                return true;
            }
        }
        return false;
    }

    public boolean addFav(Book book) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        ArrayList<Book> books = getFavBooks();
        if (books != null) {
            if (books.add(book)) {
                editorRemNPutNCommitString(FAV_JSON_KEY, books);
                return true;
            }
        }
        return false;
    }

    public boolean remReading(Book book) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        ArrayList<Book> books = getReadingBooks();
        if (books != null) {
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        editorRemNPutNCommitString(READING_JSON_KEY, books);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean remAlrRead(Book book) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        ArrayList<Book> books = getAlrReadBooks();
        if (books != null) {
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        editorRemNPutNCommitString(ALRREAD_JSON_KEY, books);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean remWished(Book book) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        ArrayList<Book> books = getWishBooks();
        if (books != null) {
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        editorRemNPutNCommitString(WISH_JSON_KEY, books);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean remFav(Book book) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        ArrayList<Book> books = getFavBooks();
        if (books != null) {
            for (Book b: books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        editorRemNPutNCommitString(FAV_JSON_KEY, books);
                        return true;
                    }
                }
            }
        }
        return false;
    }

     // Get <book> lists from Json
    public ArrayList<Book> getAllBooks() {
         ArrayList<Book> books = gson.fromJson(sharedPrefs.getString(ALLBOOKS_JSON_KEY, null), type);
         return books;
    }

    public ArrayList<Book> getReadingBooks() {
        return getBooksList(READING_JSON_KEY);
    }

    public ArrayList<Book> getWishBooks() {
        return getBooksList(WISH_JSON_KEY);
    }

    public ArrayList<Book> getAlrReadBooks() {
        return getBooksList(ALRREAD_JSON_KEY);
    }

    public ArrayList<Book> getFavBooks() {
        return getBooksList(FAV_JSON_KEY);
    }

    public ArrayList<Book> getBooksList(String key) {
        return gson.fromJson(sharedPrefs.getString(key, null), type);
    }
}
