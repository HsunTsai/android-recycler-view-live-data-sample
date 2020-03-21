package com.hsun.myapplication.ui.imageViewer;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hsun.myapplication.ui.components.loadingProgressView.LoadingProgressView;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideImageLoader {
    private ImageView imageView;
    private LoadingProgressView loadingProgressView;

    public GlideImageLoader(ImageView imageView, final LoadingProgressView loadingProgressView) {
        this.imageView = imageView;
        this.loadingProgressView = loadingProgressView;
    }

    public void load(final String url, Headers headers, RequestOptions options) {
        if (url == null || options == null) return;

        /* set Listener & start */
        ProgressAppGlideModule.expect(url, new ProgressAppGlideModule.UIonProgressListener() {
            @Override
            public void onProgress(long bytesRead, long expectedLength) {
                if (loadingProgressView != null && expectedLength > 0)
                    loadingProgressView.setDownloadProgress((int) (100 * bytesRead / expectedLength));
            }

            @Override
            public float getGranualityPercentage() {
                return 1.0f;
            }
        });
        /* Get Image */
        Glide.with(imageView.getContext())
                .load(new GlideUrl(url, headers))
                .transition(withCrossFade())
                .apply(options.skipMemoryCache(true))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ProgressAppGlideModule.forget(url);
                        onFinished();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ProgressAppGlideModule.forget(url);
                        onFinished();
                        return false;
                    }
                })
                .into(imageView);
    }

    private void onFinished() {
        if (loadingProgressView != null)
            loadingProgressView.setVisibility(View.GONE);
    }
}