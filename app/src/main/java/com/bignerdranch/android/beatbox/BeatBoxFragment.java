package com.bignerdranch.android.beatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by liubin on 2017/2/21.
 */

public class BeatBoxFragment extends Fragment {

    //beatbox
    private BeatBox mBeatBox;

    //用来创建BeatBoxFragment实例
    public static BeatBoxFragment newIntance() {

        return new BeatBoxFragment();


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //通过构造函数获取mBeatBox实例
        setRetainInstance(true);
        mBeatBox = new BeatBox(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //fragment_beat_box中包含RecyclerView控件
        View view = inflater.inflate(R.layout.fragment_beat_box,container,false);

        //RecyclerView控件
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_beat_box_recycler_view);

        //网格显示，每行3列
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));


        //设置适配器
        recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    //创建SoundHolder类
    private class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button mButton;
        private Sound mSound;
        public SoundHolder(LayoutInflater inflater, ViewGroup container) {
            //list_item_sound包含一个button控件
            super(inflater.inflate(R.layout.list_item_sound,container,false));
            /**
             * public static abstract class ViewHolder {
             * public final View itemView;
             * 这里用的itemView是继承的
             */
            mButton  = (Button) itemView.findViewById(R.id.list_item_sound_button);
            mButton.setOnClickListener(this);
        }
        public void bindSound(Sound sound) {
            mSound = sound;
            mButton.setText(mSound.getName());
        }

        @Override
        public void onClick(View v) {

            mBeatBox.play(mSound);
        }
    }


    //创建SoundAdapter类
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {

        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds){
            mSounds = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundHolder(inflater, parent);

//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            View view = layoutInflater.inflate(R.layout.list_item_crime,parent,false);
//
//            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            Sound sound = mSounds.get(position);

            holder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }

}
