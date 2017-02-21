package com.bignerdranch.android.beatbox;

import android.util.Log;

/**
 * Created by liubin on 2017/2/21.
 */

public class Sound {

    private static final String TAG = "Sound";
    private String mAssetPath;
    private String mName;

    private Integer mSoundId;

    public Sound(String assetPath) {
        mAssetPath = assetPath;

        //使用"／"分离出文件名
        String[] components = assetPath.split("/");
        Log.i(TAG, components[0]);//sample_sounds
        Log.i(TAG, components[1]);//xxxxx.wav

        //得到xxx.wav
        String filename = components[components.length - 1];
        //删除.wav后缀
        mName = filename.replace(".wav", "");
    }

    public String getAssetPath(){
        return mAssetPath;
    }
    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
