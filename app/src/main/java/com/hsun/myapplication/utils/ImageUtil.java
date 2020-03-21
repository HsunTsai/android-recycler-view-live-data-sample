package com.hsun.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hsun.myapplication.R;
import com.hsun.myapplication.ui.components.loadingProgressView.LoadingProgressView;
import com.hsun.myapplication.ui.imageViewer.GlideImageLoader;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ImageUtil {

    public static RequestOptions getDefaultOptions() {
        return new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL) /* 緩存所有影像(此選項為預設值) */
                .priority(Priority.LOW);
    }

    private static RequestOptions getDefaultImageOptions() {
        return new RequestOptions()
                .centerCrop()
//                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_broken)
                .diskCacheStrategy(DiskCacheStrategy.ALL) /* 緩存所有影像(此選項為預設值) */
                .priority(Priority.LOW);
    }

    public static RequestOptions getProgressImageOptions() {
        return new RequestOptions()
                .fitCenter()
                .error(R.drawable.img_broken)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.LOW);
    }

    public static void setBitmapImage(ImageView imageView, Bitmap bitmap) {
        setBitmapImage(imageView, bitmap, getDefaultImageOptions());
    }

    public static void setBitmapImage(ImageView imageView, Bitmap bitmap, RequestOptions options) {
        if (!((Activity) imageView.getContext()).isFinishing() && null != bitmap)
            Glide.with(imageView.getContext())
                    .load(bitmap)
                    .apply(options)
                    .into(imageView);
    }

    public static void setBase64Image(ImageView imageView, String base64) {
        setBase64Image(imageView, base64, getDefaultImageOptions(), Color.BLACK);
    }

    public static void setBase64Image(final ImageView imageView, String base64, RequestOptions options) {
        if (!((Activity) imageView.getContext()).isFinishing()) {
            Glide.with(imageView.getContext())
                    .load(base64ToBitmap(base64))
                    .apply(options.diskCacheStrategy(DiskCacheStrategy.NONE)) //不緩存資源(Base64你緩存個屁)
                    .into(imageView);
        }
    }

    public static void setBase64Image(final ImageView imageView, String base64, RequestOptions options, final int tintColorRes) {
        if (!((Activity) imageView.getContext()).isFinishing()) {
            Glide.with(imageView.getContext())
                    .load(base64ToBitmap(base64))
                    .apply(options.diskCacheStrategy(DiskCacheStrategy.NONE)) //不緩存資源(Base64你緩存個屁)
//                    .error(errorImageRes == 0 ? null : Glide.with(imageView.getContext())
//                            .load(errorImageRes))
                    .listener((tintColorRes == Color.BLACK) ? null : new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), tintColorRes), android.graphics.PorterDuff.Mode.SRC_IN);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }

    public static void setResourceImage(ImageView imageView, int imageResource) {
        setResourceImage(imageView, imageResource, getDefaultImageOptions());
    }

    public static void setResourceImage(ImageView imageView, int imageResource, RequestOptions options) {
        if (!((Activity) imageView.getContext()).isFinishing() && imageResource != 0)
            Glide.with(imageView.getContext())
                    .load(imageResource)
                    .apply(options)
                    .into(imageView);
    }

    private void setResourceImageBlur(ImageView imageView, int imageResource) {
        if (!((Activity) imageView.getContext()).isFinishing())
            Glide.with(imageView.getContext())
                    .load(imageResource)
                    .apply(getDefaultImageOptions().transform(
                            new MultiTransformation<Bitmap>(new BlurTransformation(25), new CenterCrop())))
                    .into(imageView);
    }

    public static void setUrlImage(ImageView imageView, String url) {
        setUrlImage(imageView, url, getDefaultImageOptions());
    }

    private static void setUrlImage(ImageView imageView, String url, RequestOptions options) {
        setUrlImageProgress(imageView, null, url, options);
    }

    @SuppressLint("CheckResult")
    public static void setUrlImageProgress(final ImageView imageView, final LoadingProgressView loadingProgressView, String url,
                                           RequestOptions options) {
        Headers headers = new LazyHeaders.Builder().build();
        if (!((Activity) imageView.getContext()).isFinishing()) {
            if (null != loadingProgressView) loadingProgressView.setVisibility(View.VISIBLE);
            if (url.contains("DH_STORE") && !url.contains("size="))
                url = url.concat("?size=original");
            new GlideImageLoader(imageView, loadingProgressView).load(url, headers, options);
        }
    }

    public static Bitmap base64ToBitmap(String imageBase64) {
        if (null != imageBase64 && imageBase64.length() > 5) {
            byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
            return (BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }
        return null;
    }

    public static int getDominantColor(Bitmap bitmap) {
        int color = Color.parseColor("#0087dc");
        if (null != bitmap) {
//            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
//            color = bitmap.getPixel(0, 0);
//            newBitmap.recycle();
            if (bitmap.getHeight() > 5 && bitmap.getWidth() > 5)
                color = bitmap.getPixel(5, 5);
        }
        return color;
    }
}
