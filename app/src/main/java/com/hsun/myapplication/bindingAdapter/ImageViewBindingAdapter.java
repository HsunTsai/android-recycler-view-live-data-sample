package com.hsun.myapplication.bindingAdapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.hsun.myapplication.R;
import com.hsun.myapplication.utils.ImageUtil;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ImageViewBindingAdapter {

    /**
     * 設定Base64到圖片中
     *
     * @param imageView 影像元件
     * @param base64    圖形編碼
     */
    @BindingAdapter("setImageBase64")
    public static void setImageBase64(ImageView imageView, String base64) {
        ImageUtil.setBase64Image(imageView, base64, ImageUtil.getDefaultOptions().centerCrop(), Color.BLACK);
    }

    /**
     * User
     * 設定Base64到圖片中(圓形)
     * default圖 為 ic_member
     *
     * @param imageView 影像元件
     * @param base64    圖形編碼
     */
    @BindingAdapter("setUserImageBase64")
    public static void setUserImageBase64(ImageView imageView, String base64) {
        Bitmap bitmap = ImageUtil.base64ToBitmap(base64);
        if (null == bitmap) {
            Glide.with(imageView.getContext())
                    .load(R.mipmap.ic_launcher)
                    .apply(ImageUtil.getDefaultOptions())
                    .into(imageView);
        } else {
            ImageUtil.setBitmapImage(imageView, bitmap, ImageUtil.getDefaultOptions().circleCropTransform());
        }
    }

    /**
     * 設定URL到圖片中
     *
     * @param imageView 影像元件
     * @param url       圖片網址
     */
    @BindingAdapter("setUserImageUrl")
    public static void setUserImageUrl(ImageView imageView, String url) {
        ImageUtil.setUrlImage(imageView, url);
    }

    /**
     * 設定Resource到圖片中(Crop)
     *
     * @param imageView   影像元件
     * @param imgResource 圖像來源
     */
    @BindingAdapter("setImageResource")
    public static void setImageResource(ImageView imageView, int imgResource) {
        ImageUtil.setResourceImage(imageView, imgResource, ImageUtil.getDefaultOptions().centerCrop());
    }

    /**
     * 設定Resource到圖片中(Fit)
     *
     * @param imageView   影像元件
     * @param imgResource 圖像來源
     */
    @BindingAdapter("setImageResourceFit")
    public static void setImageResourceFit(ImageView imageView, int imgResource) {
        ImageUtil.setResourceImage(imageView, imgResource, ImageUtil.getDefaultOptions().fitCenter());
    }

    /**
     * 設定Resource到圖片中(有Corner)
     *
     * @param imageView   影像元件
     * @param imgResource 圖像來源
     */
    @BindingAdapter("setImageResourceCorner")
    public static void setImageResourceCorner(ImageView imageView, int imgResource) {
        ImageUtil.setResourceImage(imageView, imgResource,
                ImageUtil.getDefaultOptions().bitmapTransform(new RoundedCornersTransformation(50, 0,
                        RoundedCornersTransformation.CornerType.ALL)));
    }

    /**
     * 設定影像顏色
     *
     * @param imageView     影像元件
     * @param colorResource 色碼來源
     */
    @BindingAdapter("setImageTint")
    public static void setImageTint(ImageView imageView, int colorResource) {
        if (0 == colorResource) return;
        imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), colorResource), android.graphics.PorterDuff.Mode.SRC_IN);
//        imageView.setImageTintList(imageView, ColorStateList.valueOf(ContextCompat.getColor(imageView.getContext(), colorId)));
    }

    /**
     * 讓圖片縮放顯示消失
     *
     * @param imageView 影像元件
     * @param show      是否要顯示元件
     */
    @BindingAdapter("setScaleShow")
    public static void setScaleShow(ImageView imageView, boolean show) {
        if (show) {
            imageView.animate().scaleY(1).scaleX(1).alpha(1).setDuration(250).start();
        } else {
            imageView.animate().scaleY(0).scaleX(0).alpha(0).setDuration(250).start();
        }
    }


}
