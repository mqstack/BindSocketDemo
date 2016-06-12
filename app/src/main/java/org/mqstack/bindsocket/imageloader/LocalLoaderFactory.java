package org.mqstack.bindsocket.imageloader;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import org.mqstack.bindsocket.okhttp.BindedHttpClient;

import java.io.InputStream;

/**
 * Created by mqstack on 2016/1/20.
 */
public class LocalLoaderFactory implements ModelLoaderFactory<GlideCameraUrl, InputStream> {

    @Override
    public ModelLoader<GlideCameraUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
        return new OkHttpUrlLoader(BindedHttpClient.getInstance().client);
    }

    @Override
    public void teardown() {

    }
}
