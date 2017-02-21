package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubin on 2017/2/21.
 */

public class BeatBox {

    private static final String TAG = "BeatBox";

    //目录
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static int MAX_SOUNDS = 5;



    //访问assets需要用到AssetManager类，我们可以从Context中获取它

    private AssetManager mAssets;

    private List<Sound> mSounds = new ArrayList<>();

    private SoundPool mSoundPool;


    //添加带参构造方法获取并保存它，在世纪开发的场景下，所有Context的AssetManager管理的都是同一套assets资源
    //要取得assets中的资源清单，可以使用list(String)方法，下面一个loadSounds()，方法，点用它给出声音文件清单
    public BeatBox(Context context) {

        mAssets = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);

        loadSounds();
    }

    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();

        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId,1.0f,1.0f,1,0,1.0f);
    }

    public void release() {

        mSoundPool.release();
    }

    private void loadSounds() {

        String[] soundNames;

        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);//列出在指定文件夹中所有文件
            Log.i(TAG, "Found" + soundNames.length + " sounds");
            Log.i(TAG, soundNames[0]);//65_cjipie.wav
            Log.i(TAG, soundNames[1]);//66_indios.wav
        } catch (IOException e) {
            /**
             * e(String tag, String msg, Throwable tr)
             *发送ERROR日志消息和日志例外。
             */
            Log.e(TAG, "Could not list assets",e);
            return;
        }

        for (String filename : soundNames) {

            try {
                //assetPath: sample_sounds/xxxxxx.wav
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                //调用Sound(String assetPath)构造函数
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException e) {
                Log.e(TAG, "Could not load sound" + filename,e);
            }
        }
    }

    //加载音频
    private void load(Sound sound) throws IOException {

        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    //返回list<Sound>
    public List<Sound> getSounds() {
        return mSounds;
    }


}
