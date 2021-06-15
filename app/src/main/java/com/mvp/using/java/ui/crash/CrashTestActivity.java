package com.mvp.using.java.ui.crash;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.badasoftware.library.utilities.ScreenshotUtils;
import com.mvp.using.java.R;

public class CrashTestActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_test);

       textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(ScreenshotUtils.screenShot(CrashTestActivity.this, true));
            }
        });
    }
}