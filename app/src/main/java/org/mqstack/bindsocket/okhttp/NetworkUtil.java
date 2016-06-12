package org.mqstack.bindsocket.okhttp;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import org.mqstack.bindsocket.imageloader.ImageLoader;
import org.mqstack.bindsocket.util.L;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Socket;

/**
 * Created by mqstack on 2016/1/19.
 */
public class NetworkUtil {

    private static Network network;

    private static int netId;

    public static void setNetwork(Context context, Network network) {
        L.d("init network" + network);
        NetworkUtil.network = network;
        BindedHttpClient.getInstance().updateClient(network);
        ImageLoader.initGlide(context);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void init(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ConnectivityManager connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            Network[] networks = connManager.getAllNetworks();

            for (int i = 0; i < networks.length; i++) {
                NetworkInfo netInfo = connManager.getNetworkInfo(networks[i]);
                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    NetworkUtil.setNetwork(context, networks[i]);
                }
            }
        }
    }


    /**
     * 绑定FileDescriptor
     *
     * @param socketfd
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void bindSocketToNetwork(int socketfd) {
        L.d("start bindSocketToNetwork");
        if (network != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FileDescriptor fileDescriptor = new FileDescriptor();
            try {
                Field field = FileDescriptor.class.getDeclaredField("descriptor");
                field.setAccessible(true);
                field.setInt(fileDescriptor, socketfd);
                // fileDescriptor.sync();

                network.bindSocket(fileDescriptor);
//                bindSocket(socketfd, netId);
                L.d("bindSocketToNetwork success: network" + network + "+socketfd" + socketfd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 绑定socket
     *
     * @param socket
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void bindSocket(Socket socket) {
        L.d("start bindSocket");
        if (network != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                network.bindSocket(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            L.d("bindSocket success: network" + network + "+socket" + socket);
        }
    }

    /**
     * 清除绑定的network
     *
     * @param mContext
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void bindProcess(Context mContext) {

        if (mContext != null && network != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            L.d("start bindprocess");
            ConnectivityManager connManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            connManager.bindProcessToNetwork(network);
        }
    }

    /**
     * 清除绑定的network
     *
     * @param mContext
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void clearBindProcess(Context mContext) {
        if (mContext != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            L.d("start clearprocess");
            ConnectivityManager connManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            connManager.bindProcessToNetwork(null);
        }
    }

}
