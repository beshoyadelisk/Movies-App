package com.example.movieapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.utilities.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuoteDisplayActivity extends AppCompatActivity {

    @BindView(R.id.quote_text_view)
    TextView quoteText;
    @BindView(R.id.author_text_view)
    TextView authorText;
    String author, quote;
    @BindView(R.id.share_quote_button)
    FloatingActionButton shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_display);
        ButterKnife.bind(this);
        quote = getIntent().getStringExtra(Constants.KEY_QUOTES);
        author = getIntent().getStringExtra(Constants.KEY_AUTHOR);
        quoteText.setText(quote);
        authorText.setText("- " + author);
        shareButton.setOnClickListener(view -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.setAction(Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_TEXT,
                    quote + " by " + author + " \nShared via MovieApp");
            startActivity(sharingIntent);
        });
    }
}
