package com.newsuper.t.juejinbao.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;

public class WebSmsActivity extends AppCompatActivity {
    WebView bridgeWebView;
    ImageView ivclose;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_sms);

        bridgeWebView = findViewById(R.id.activity_web_sms_web);
        ivclose = findViewById(R.id.module_include_title_bar_return);
        tvTitle = findViewById(R.id.module_include_title_bar_title);
        tvTitle.setText(getIntent().getStringExtra("title"));
        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bridgeWebView.loadUrl(getIntent().getStringExtra("url"));
    }

    public static void intentMe(Context context, String url, String title) {
        Intent intent = new Intent(context, WebSmsActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
}
