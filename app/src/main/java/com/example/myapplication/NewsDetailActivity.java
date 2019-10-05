package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class NewsDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private AppBarLayout appBarLayout;
    private TextView title_appBar, subtitle_appBar, title, time, date;
    private Toolbar toolbar;
    private FrameLayout dateBehavior;

    private ImageView imageViewBackdrop;
    private String mUrl, mImg, mTitle, mDate, mSource, mAuthor;

    private Boolean isHideToolbarView = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);

        dateBehavior = findViewById(R.id.layout_date_behavior);
        title_appBar = findViewById(R.id.title_on_toolbar);
        subtitle_appBar = findViewById(R.id.subtitle_on_toolbar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_layout);
        collapsingToolbarLayout.setTitle("");
        title = findViewById(R.id.title);
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);

        imageViewBackdrop = findViewById(R.id.backdrop);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mImg = intent.getStringExtra("image");
        mTitle = intent.getStringExtra("title");
        mDate = intent.getStringExtra("date");
        mSource = intent.getStringExtra("source");
        mAuthor = intent.getStringExtra("author");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawableColor());

        Glide.with(this)
                .load(mImg)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageViewBackdrop);

        title_appBar.setText(mSource);
        subtitle_appBar.setText(mUrl);
        date.setText(mDate);
        title.setText(mTitle);


        String author;
        if (mAuthor != null) {
            author = "\u2022" + mAuthor;
        } else {
            author = "";
        }

        time.setText(mDate +author + " \u2022 " + Utils.DateToTimeFormat(mDate));

        initWebView(mUrl);

    }

    private void initWebView(String mUrl) {
        WebView webView = findViewById(R.id.web_view);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(mUrl);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

        int maxScroll = appBarLayout.getTotalScrollRange();
        float precentage = (float) Math.abs(i) / (float) maxScroll;

        if (precentage == 1f && isHideToolbarView) {
            dateBehavior.setVisibility(View.GONE);
            title_appBar.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;
        } else if (precentage == 1f && !isHideToolbarView) {
            dateBehavior.setVisibility(View.VISIBLE);
            title_appBar.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.view_on_web) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setData(Uri.parse(mUrl));
            startActivity(i);
            return true;
        } else if (id == R.id.share) {

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plan");
            i.putExtra(Intent.EXTRA_SUBJECT, mSource);

            String body = mTitle + "\n" + mUrl + "Share news" + "\n";
            i.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(i, "share with"));


        }
        return super.onOptionsItemSelected(item);
    }
}
