package org.mqstack.bindsocket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.mqstack.bindsocket.databinding.ActivityMainBinding;
import org.mqstack.bindsocket.imageloader.DemoImageLoaderActivity;
import org.mqstack.bindsocket.okhttp.BindNetActivity;
import org.mqstack.bindsocket.util.ClickEvent;
import org.mqstack.bindsocket.util.L;

public class MainActivity extends AppCompatActivity {

    public ClickEvent event = new ClickEvent() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_bind_net:
                    L.d("click bind net");
                    startActivity(new Intent(MainActivity.this, BindNetActivity.class));
                    break;
                case R.id.button_image_loader:
                    startActivity(new Intent(MainActivity.this, DemoImageLoaderActivity.class));
                    L.d("click image loader");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ActivityMainBinding bind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bind.setClickEvent(event);

    }

}
