package org.mqstack.bindsocket.imageloader;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.mqstack.bindsocket.R;
import org.mqstack.bindsocket.okhttp.BindNetActivity;
import org.mqstack.bindsocket.okhttp.NetworkUtil;
import org.mqstack.bindsocket.util.L;

/**
 * Created by mq on 16/5/25.
 */

public class DemoImageLoaderActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        findViewById(R.id.button_bind_net).setOnClickListener(this);
        findViewById(R.id.button_local).setOnClickListener(this);
        findViewById(R.id.button_net).setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.imageView);
        ImageLoader.initPicasso(this);
        ImageLoader.initGlide(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_bind_net:
                L.d("click button bind net");
                NetworkUtil.clearBindProcess(DemoImageLoaderActivity.this);
                NetworkUtil.init(DemoImageLoaderActivity.this);
                break;
            case R.id.button_local:
                ImageLoader.loadLocalImage("http://192.168.2.1/1.jpg", imageView);
                break;
            case R.id.button_net:
                ImageLoader.loadLocalImage("http://d.hiphotos.baidu.com/image/h%3D200/sign=ea218b2c5566d01661199928a729d498/a08b87d6277f9e2fd4f215e91830e924b999f308.jpg", imageView);
                break;
        }
    }
}
