package com.example.meicode_library;

import static com.example.meicode_library.AllBooksActivity.ALLBOOKS_KEY;
import static com.example.meicode_library.AlrReadBookActivity.ALRREAD_KEY;
import static com.example.meicode_library.BookActivity.BOOK_ID_KEY;
import static com.example.meicode_library.FavBookActivity.FAV_KEY;
import static com.example.meicode_library.ReadingBookActivity.READING_KEY;
import static com.example.meicode_library.WishBookActivity.WISH_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder> {

    private ArrayList<Book> books = new ArrayList<>();
    private Context adContext;
    private String parentActivity;

    public BookRecViewAdapter(Context adContext, String parentActivity) {
        this.adContext = adContext;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtBookName.setText(books.get(position).getName());
        Glide.with(adContext).asBitmap().load(books.get(position).getImageUrl())
                .into(holder.imgBook);
        holder.parentItem.setOnClickListener(view -> {
            Intent intent = new Intent(adContext, BookActivity.class);
            intent.putExtra(BOOK_ID_KEY, books.get(holder.getAdapterPosition()).getId());
            adContext.startActivity(intent);
        });

        holder.txtAuthor.setText(books.get(position).getAuthor());
        holder.txtDesc.setText(books.get(position).getShortDesc());

        if (books.get(position).isExpanded()) {
            showExpandedCard(holder, position);
        } else {
            TransitionManager.beginDelayedTransition(holder.parentItem);
            holder.expandedRelLayout.setVisibility(View.GONE);
            holder.arrowDown.setVisibility(View.VISIBLE);
        }
    }

    private void showExpandedCard(ViewHolder holder, int position) {
        TransitionManager.beginDelayedTransition(holder.parentItem);
        holder.expandedRelLayout.setVisibility(View.VISIBLE);
        holder.arrowDown.setVisibility(View.GONE);
        if (parentActivity != ALLBOOKS_KEY) {
            holder.txtDelete.setVisibility(View.VISIBLE);
            holder.txtDelete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(adContext);
                builder.setMessage("Delete " + books.get(holder.getAdapterPosition()).getName() + "?");
                builder.setPositiveButton("Yes", (dialogInterface, i) -> deleteListItem(holder, parentActivity));
                builder.setNegativeButton("No", (dialogInterface, i) -> {
                });
                builder.create().show();
            });
        }
    }

    /**
     * Methods: Get count, Get & Set books arr list
     * @return
     */
    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    private void deleteListItem(ViewHolder holder, String parentActivity) {
        boolean delSuccess = false;

        switch (parentActivity) {
            case ALRREAD_KEY:
                delSuccess = Utils.getInstance(adContext).remAlrRead(books.get(holder.getAdapterPosition()));
                setBooks(Utils.getInstance(adContext).getAlrReadBooks());
                break;
            case WISH_KEY:
                delSuccess = Utils.getInstance(adContext).remWished(books.get(holder.getAdapterPosition()));
                setBooks(Utils.getInstance(adContext).getWishBooks());
                break;
            case READING_KEY:
                delSuccess = Utils.getInstance(adContext).remReading(books.get(holder.getAdapterPosition()));
                setBooks(Utils.getInstance(adContext).getReadingBooks());
                break;
            case FAV_KEY:
                delSuccess = Utils.getInstance(adContext).remFav(books.get(holder.getAdapterPosition()));
                setBooks(Utils.getInstance(adContext).getFavBooks());
                break;
            default:
                Toast.makeText(adContext, "Smth wrong", Toast.LENGTH_SHORT).show();
        }

        if (delSuccess) {
            Toast.makeText(adContext, "Book removed from " +parentActivity+ " list", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(adContext, "smth wrg", Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    /**
     * Inner class ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parentItem;
        private ImageView imgBook;
        private TextView txtBookName;
        private ImageView arrowUp, arrowDown;
        private RelativeLayout expandedRelLayout;
        private TextView txtAuthor, txtDesc, txtDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentItem = itemView.findViewById(R.id.parent_item);
            imgBook = itemView.findViewById(R.id.imgBook);
            txtBookName = itemView.findViewById(R.id.textView1);

            arrowDown = itemView.findViewById(R.id.btnArrowDown);
            arrowUp = itemView.findViewById(R.id.btnArrowUp);
            expandedRelLayout = itemView.findViewById(R.id.expandedRelativeLayout);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtDesc = itemView.findViewById(R.id.txtShortDesc);
            txtDelete = itemView.findViewById(R.id.btnDelete);

            arrowDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            arrowUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(false);
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
