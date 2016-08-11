package org.mqstack.bindsocket.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.mqstack.bindsocket.okhttp.BindedHttpClient;

import java.io.InputStream;

/**
 * Created by mqstack on 16/5/26.
 */

public class ImageLoader {


    private static Picasso bindPicasso;
    private static Picasso netPicasso;

    public static void initPicasso(Context context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        netPicasso = builder.build();

        updateBindPicasso(context);
    }

    public static void updateBindPicasso(Context context) {
        Picasso.Builder cameraBuilder = new Picasso.Builder(context);
        cameraBuilder.downloader(new OkHttp3Downloader(BindedHttpClient.getInstance().client));
        bindPicasso = cameraBuilder.build();
    }

    public static void loadLocalImage(String imageUri, ImageView imageView) {
        bindPicasso.load(imageUri).into(imageView);
    }

    public static void loadNetImage(String imageUri, ImageView imageView) {
        netPicasso.load(imageUri).into(imageView);
    }

    public static void initGlide(Context context) {
        Glide.get(context).register(GlideCustomUrl.class, InputStream.class, new LocalLoaderFactory());
    }

    public static void loadLocalImage(Context context, String imageUri, ImageView imageView) {
        Glide.with(context).load(new GlideCustomUrl(imageUri)).into(imageView);
    }

    public static void loadNetImage(Context context, String imageUri, ImageView imageView) {
        Glide.with(context).load(imageUri).into(imageView);
    }
}
