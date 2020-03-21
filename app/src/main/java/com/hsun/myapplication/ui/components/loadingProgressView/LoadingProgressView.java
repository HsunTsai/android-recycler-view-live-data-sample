package com.hsun.myapplication.ui.components.loadingProgressView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hsun.myapplication.R;
import com.hsun.myapplication.utils.HandleMessage;

public class LoadingProgressView extends RelativeLayout {

    private Handler handler;
    private ProgressBar loadingProgressBar, downloadProgressBar;
    private TextView txt_download;

    public LoadingProgressView(@NonNull Context context) {
        super(context);
        if (!isInEditMode()) init(context);
    }

    public LoadingProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.component_loading_progress_view, this);
        Handler();
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        loadingProgressBar.setVisibility(VISIBLE);
        downloadProgressBar = findViewById(R.id.downloadProgressBar);
        downloadProgressBar.setVisibility(GONE);
        downloadProgressBar.setMax(100);
        txt_download = findViewById(R.id.txt_download);
        txt_download.setVisibility(VISIBLE);
    }

    public void setDownloadProgress(int progress) {
        setDownloadProgress((long) progress);
    }

    public void setDownloadProgress(long progress) {
        HandleMessage.set(handler, "updateProgress", String.valueOf(progress));
    }

    public void setBlackMode() {
        downloadProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.circular_progress_bar_white));
        txt_download.setTextColor(Color.WHITE);
    }

    public void setProgressTextVisible(boolean visible) {
        txt_download.setVisibility(visible ? VISIBLE : GONE);
    }

    @SuppressLint("HandlerLeak")
    private void Handler() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.getData().getString("title", "")) {
                    case "updateProgress":
                        long progress = Long.parseLong(msg.getData().getString("message", "-1"));
                        if (progress < 0) {
                            loadingProgressBar.setVisibility(VISIBLE);
                            downloadProgressBar.setVisibility(GONE);
                            txt_download.setText("Loading");
                        } else if (progress <= 100) {
                            loadingProgressBar.setVisibility(GONE);
                            downloadProgressBar.setVisibility(VISIBLE);
                            downloadProgressBar.setProgress((int) progress);
                            txt_download.setText(progress + " %");
                        } else {
                            txt_download.setText(String.valueOf(progress));
                        }
                        break;
                }
            }
        };
    }
}
