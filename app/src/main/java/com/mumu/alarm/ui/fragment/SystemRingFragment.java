package com.mumu.alarm.ui.fragment;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.mumu.alarm.App;
import com.mumu.alarm.Constants;
import com.mumu.alarm.R;
import com.mumu.alarm.bean.Alarm;
import com.mumu.alarm.bean.Ring;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SystemRingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SystemRingFragment extends Fragment {

    @Bind(R.id.ring_list_view)
    ListView listView;

    private MediaPlayer mediaPlayer;
    private Alarm alarm;

    // TODO: Rename and change types and number of parameters
    public static SystemRingFragment newInstance(Alarm alarm) {
        SystemRingFragment fragment = new SystemRingFragment();
        Bundle args = new Bundle();
        args.putSerializable("alarm", alarm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            alarm = (Alarm) args.getSerializable("alarm");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_ring, container, false);
        ButterKnife.bind(this, view);
        boolean flag = loadSystemRingData();
        if (flag) {
            final List<Ring> ringList = App.getApp().getRingList();
            listView.setAdapter(new ArrayAdapter<Ring>(getContext(), android.R.layout.simple_list_item_single_choice, ringList) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    convertView = super.getView(position, convertView, parent);
                    Ring ring = getItem(position);
                    CheckedTextView ctv = (CheckedTextView) convertView.findViewById(android.R.id.text1);
                    ctv.setText(ring.getName());
                    return convertView;
                }
            });
            listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

            if(alarm.getRingType() == Constants.ASSERTS_RING_TYPE){
                listView.setItemChecked(0 , true);
            }else if(alarm.getRingType() == Constants.SYSTEM_RING_TYPE){
                int length = ringList.size();
                for(int i = 0 ; i < length;i++){
                    Ring ring = ringList.get(i);
                    if(alarm.getRingName().equals(ring.getName())){
                        listView.setItemChecked(i , true);
                    }
                }
            }

        } else {//加载失败

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ring ring = (Ring) adapterView.getAdapter().getItem(i);
                alarm.setRingName(ring.getName());
                alarm.setRingPath(ring.getPath());
                alarm.setRingType(ring.getType());
                App.getApp().getDaoSession().getAlarmDao().insertOrReplace(alarm);
                play(ring);
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    private boolean loadSystemRingData() {
        List<Ring> ringList = App.getApp().getRingList();
        if (ringList.isEmpty()) {

            Ring r = new Ring();
            r.setName(Constants.DEFAULT_ALARM_NAME);
            r.setPath(Constants.DEFAULT_ALARM_PATH);
            r.setType(Constants.ASSERTS_RING_TYPE);
            ringList.add(r);

            File ringDir = new File(Constants.ANDROID_ALARM);
            if (ringDir.exists() && ringDir.isDirectory()) {
                File[] files = ringDir.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File file, String s) {
                        return s.endsWith(".ogg");
                    }
                });
                if (files != null && files.length > 0) {
                    for (File file : files) {
                        Ring ring = new Ring();
                        String name = file.getName();
                        ring.setName(name.substring(0, name.lastIndexOf('.')));
                        ring.setPath(file.getPath());
                        ring.setType(Constants.SYSTEM_RING_TYPE);
                        ringList.add(ring);
                    }
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }


    private void play(Ring ring) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        try {
            if (ring.getType() == Constants.ASSERTS_RING_TYPE) {
                AssetManager manager = getActivity().getAssets();
                AssetFileDescriptor assetFileDescriptor = manager.openFd(Constants.DEFAULT_ALARM_PATH);
                mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset()
                        , assetFileDescriptor.getLength());
            } else {
                String url = ring.getPath();
                mediaPlayer.setDataSource(url);
            }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
