package com.aghajari.ax;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aghajari.axvideotimelineview.AXTimelineViewListener;
import com.aghajari.axvideotimelineview.AXVideoTimelineView;

public class MainActivity extends AppCompatActivity {

    AXVideoTimelineView axView;
    TextView duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        axView = findViewById(R.id.axView);

        axView.setVisibility(View.GONE);
        duration = findViewById(R.id.duration);

        axView.setListener(new AXTimelineViewListener() {
            @Override
            public void onLeftProgressChanged(float progress) {

            }

            @Override
            public void onRightProgressChanged(float progress) {

            }

            @Override
            public void onDurationChanged(long Duration) {
                duration.setText("Duration : "+Duration);
            }

            @Override
            public void onPlayProgressChanged(float progress) {

            }

            @Override
            public void onDraggingStateChanged(boolean isDragging) {

            }
        });

        findViewById(R.id.picker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(Intent.createChooser(intent,"Select Video"),3);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, int[] grantResults) {
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    this.finish();
                }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
                Uri selectedVideoUri = data.getData();

                axView.setVisibility(View.VISIBLE);
                axView.setVideoUri(this, selectedVideoUri);
                duration.setText("Duration : " + axView.getVideoDuration());
        }
    }
}
