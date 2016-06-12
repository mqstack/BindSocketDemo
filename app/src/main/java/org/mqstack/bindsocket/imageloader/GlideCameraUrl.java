package org.mqstack.bindsocket.imageloader;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;

import java.net.URL;

/**
 * Created by mqstack on 2016/1/20.
 */
public class GlideCameraUrl extends GlideUrl {

    public GlideCameraUrl(URL url) {
        super(url);
    }

    public GlideCameraUrl(String url) {
        super(url);
    }

    public GlideCameraUrl(URL url, Headers headers) {
        super(url, headers);
    }

    public GlideCameraUrl(String url, Headers headers) {
        super(url, headers);
    }


}
