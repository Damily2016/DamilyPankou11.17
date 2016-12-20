package com.damily.pkds.view;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.damily.pkds.R;

/**
 * Created by Dandan.Cao on 2016/12/19.
 */
public class Test extends Activity {
    private static final String PARAM_GIFT = "com.damily.pkds.entity.GiftInfo";
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        String url = getIntent().getStringExtra(PARAM_GIFT);
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
