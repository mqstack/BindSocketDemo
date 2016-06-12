package org.mqstack.bindsocket.okhttp;

import android.net.Network;
import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mq on 16/5/25.
 */

public class BindedHttpClient {

    public OkHttpClient client;

    private static BindedHttpClient instance;

    public static BindedHttpClient getInstance() {
        synchronized (BindedHttpClient.class) {
            if (instance == null) {
                instance = new BindedHttpClient();
            }
            return instance;
        }
    }

    private BindedHttpClient() {
        updateClient(null);
    }

    public void updateClient(Network network) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(0, 5, TimeUnit.MINUTES));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && network != null) {
            builder.socketFactory(network.getSocketFactory());
        }
        client = builder.build();
    }

    public void getLocal(String url, final MqHttpListener listener) {
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (response.isSuccessful()) {
                    listener.onSuccess(result);
                } else {
                    listener.onFail(result);
                }
            }
        });
    }
}
