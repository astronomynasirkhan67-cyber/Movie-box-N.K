package com.nasir.moviebox;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private WebView web;
    private static final String BASE = "https://www.justwatch.com/in/";

    private Button tab(String label, final String path) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextColor(Color.parseColor("#D4AF37"));
        b.setBackgroundColor(Color.TRANSPARENT);
        b.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1f));
        b.setOnClickListener(v -> web.loadUrl(BASE + path));
        return b;
    }

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.BLACK);

        web = new WebView(this);
        root.addView(web, new LinearLayout.LayoutParams(-1, 0, 1f));

        LinearLayout bar = new LinearLayout(this);
        bar.setBackgroundColor(Color.parseColor("#16161D"));
        bar.setGravity(Gravity.CENTER);
        bar.addView(tab("Movies", "movies"));
        bar.addView(tab("TV Shows", "tv-shows"));
        bar.addView(tab("New", "new"));
        root.addView(bar, new LinearLayout.LayoutParams(-1, -2));
        setContentView(root);

        WebSettings ws = web.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                String host = r.getUrl().getHost();
                if (host != null && host.endsWith("justwatch.com")) return false;
                // Streaming services (Netflix, Prime, etc.) open in their own app or browser
                startActivity(new Intent(Intent.ACTION_VIEW, r.getUrl()));
                return true;
            }
        });
        web.loadUrl(BASE + "movies");
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) web.goBack(); else super.onBackPressed();
    }
}
