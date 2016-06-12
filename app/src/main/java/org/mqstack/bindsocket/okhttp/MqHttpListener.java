package org.mqstack.bindsocket.okhttp;

/**
 * Created by mq on 16/5/30.
 */

public interface MqHttpListener<T> {
    void onSuccess(T data);

    void onFail(String error);
}
