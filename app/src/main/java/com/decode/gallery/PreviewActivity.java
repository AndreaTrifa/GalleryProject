package com.decode.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreviewActivity extends AppCompatActivity {
    SquareRelativeLayout mediaPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        mediaPreview = findViewById(R.id.preview_media);
        mediaPreview.setBackgroundColor(getIntent().getIntExtra("color", 0));

    }


}
