package org.mqstack.bindsocket.imageloader;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;

import java.net.URL;

/**
 * Created by mqstack on 2016/1/20.
 */
public class GlideCustomUrl extends GlideUrl {

    public GlideCustomUrl(URL url) {
        super(url);
    }

    public GlideCustomUrl(String url) {
        super(url);
    }

    public GlideCustomUrl(URL url, Headers headers) {
        super(url, headers);
    }

    public GlideCustomUrl(String url, Headers headers) {
        super(url, headers);
    }


}
