package com.example.instagram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.R.attr.data;

public class StartActivity extends AppCompatActivity {
    private Button mButton;
    private Button mButtonToMain;
    private ImageView mImageView;
    private Bitmap mBitmap;
    private final int IMAGE_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mButton = (Button) findViewById(R.id.PhotoButton);
        mButtonToMain = (Button) findViewById(R.id.ButtonToMain);
        mImageView = (ImageView) findViewById(R.id.PhotoImage);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.PhotoButton) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE_RESULT);
                } else {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    intent.putExtra("image", byteArray);
                    startActivity(intent);
                }
            }
        };

        mButton.setOnClickListener(clickListener);
        mButtonToMain.setOnClickListener(clickListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    mImageView.setImageBitmap(mBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


