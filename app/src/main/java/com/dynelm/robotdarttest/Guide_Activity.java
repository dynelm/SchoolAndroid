package com.dynelm.robotdarttest;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class Guide_Activity extends BasicActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_layout);
        ImageView imageView = (ImageView) findViewById(R.id.guide_main);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Guide_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
