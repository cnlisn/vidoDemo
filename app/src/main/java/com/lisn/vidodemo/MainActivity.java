package com.lisn.vidodemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.werb.permissionschecker.PermissionChecker;

import java.io.File;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaUtils mediaUtils;
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private PermissionChecker permissionChecker;
    private Context mContent;
    private String sp_path;
    private String TAG="---";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.camera_show_view);
        mContent = this;
        permissionChecker = new PermissionChecker(this); // initialize，must need
        if (permissionChecker.isLackPermissions(PERMISSIONS)) {
            permissionChecker.requestPermissions();
        }
        
        // setting
        mediaUtils = new MediaUtils(this);
        mediaUtils.setRecorderType(MediaUtils.MEDIA_VIDEO);

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        mediaUtils.setTargetDir(file);
        String name = UUID.randomUUID() + ".mp4";
        mediaUtils.setTargetName(name);
        sp_path = file.getAbsolutePath() + File.separator + name;
        mediaUtils.setSurfaceView(surfaceView);

        Button bt1 = (Button) findViewById(R.id.bt1);
        Button bt_save = (Button) findViewById(R.id.bt_save);
        Button bt_sta = (Button) findViewById(R.id.bt_sta);
        bt_save.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt_sta.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_save) {        //保存视频 保存位置sp_path= /storage/emulated/0/Movies/3053e16d-17b7-4414-8cac-d00957a4d856.mp4
            mediaUtils.stopRecordSave();
            Intent intent = new Intent(mContent,test.class);
            intent.putExtra("sp_path", sp_path);
            Log.e(TAG, "onClick:sp_path= "+sp_path);
            mContent.startActivity(intent);
        } else if (v.getId() == R.id.bt1) {     //重新录制
            mediaUtils.stopRecordUnSave();
            mediaUtils.record();
        }else if (v.getId() == R.id.bt_sta) {   //开始录制视频
            mediaUtils.record();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
                    // TODO: 2017/6/22
                } else {
                    permissionChecker.showDialog();
                }
                break;
        }
    }
}
