package org.mqstack.bindsocket.okhttp;

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

public class NetHttpClient {

    private OkHttpClient client;

    private static NetHttpClient instance;

    public static NetHttpClient getInstance() {
        synchronized (NetHttpClient.class) {
            if (instance == null) {
                instance = new NetHttpClient();
            }
            return instance;
        }
    }

    private NetHttpClient() {
        getClient();
    }

    public OkHttpClient getClient() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool());
            client = builder.build();
        }
        return client;
    }

    public void getNet(String url, final MqHttpListener listener){
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
