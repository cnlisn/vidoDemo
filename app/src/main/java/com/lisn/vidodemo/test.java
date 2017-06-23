package com.lisn.vidodemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class test extends AppCompatActivity implements View.OnClickListener {

    private String sp_path;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button bf = (Button) findViewById(R.id.bf);
        videoView = (VideoView) findViewById(R.id.vv);
        bf.setOnClickListener(this);
        Intent intent = getIntent();
        sp_path = intent.getStringExtra("sp_path");

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bf){
            if (sp_path.length()>1) {
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoPath(sp_path);
                videoView.start();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();

    }
}
