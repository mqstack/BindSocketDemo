package org.mqstack.bindsocket.okhttp;

import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.mqstack.bindsocket.R;
import org.mqstack.bindsocket.databinding.ActivityBindNetBinding;
import org.mqstack.bindsocket.util.ClickEvent;
import org.mqstack.bindsocket.util.L;
import org.mqstack.bindsocket.util.ToastUtil;

/**
 * Created by mq on 16/5/25.
 */

public class BindNetActivity extends AppCompatActivity {

    public ClickEvent event = new ClickEvent() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_bind_net:
                    L.d("click button bind net");
                    NetworkUtil.clearBindProcess(BindNetActivity.this);
                    NetworkUtil.init(BindNetActivity.this);
                    break;
                case R.id.button_bind_process:
                    L.d("click button bind process");
                    NetworkUtil.bindProcess(BindNetActivity.this);
                    break;
                case R.id.button_get_bind:
                    L.d("click button get bind net");
                    BindedHttpClient.getInstance().getLocal("http://192.168.2.1", new MqHttpListener<String>() {
                        @Override
                        public void onSuccess(final String data) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(BindNetActivity.this, data);
                                }
                            });
                        }

                        @Override
                        public void onFail(final String error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(BindNetActivity.this, error);
                                }
                            });
                        }
                    });
                    break;
                case R.id.button_get_net:
                    L.d("click button get normal net");
                    NetHttpClient.getInstance().getNet("http://www.baidu.com", new MqHttpListener<String>() {
                        @Override
                        public void onSuccess(final String data) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(BindNetActivity.this, data);
                                }
                            });
                        }

                        @Override
                        public void onFail(final String error) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(BindNetActivity.this, error);
                                }
                            });
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBindNetBinding bind = DataBindingUtil.setContentView(this, R.layout.activity_bind_net);
        bind.setClickEvent(event);
        bindNet();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void bindNet() {
        final ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkUtil.clearBindProcess(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkRequest.Builder request = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            connManager.registerNetworkCallback(request.build(), new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
//                    connManager.bindProcessToNetwork(network);
                    NetworkUtil.setNetwork(BindNetActivity.this, network);
                    L.d("bind network " + network.toString());
                }

                @Override
                public void onLost(Network network) {
                    NetworkUtil.setNetwork(BindNetActivity.this, null);
                    try {
                        connManager.unregisterNetworkCallback(this);
                    } catch (SecurityException e) {
                        L.d("Failed to unregister network callback");
                    }
                }
            });
        }
    }
}
