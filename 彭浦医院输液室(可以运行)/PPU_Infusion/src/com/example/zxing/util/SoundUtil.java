package com.example.zxing.util;

import java.util.HashMap;
import java.util.Map;

import com.example.ppu_infusion.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;



/**
 * ����������
 * Created by zhouweilong on 2016/11/2.
 */

public class SoundUtil {

    public static SoundPool sp ;
    public static Map<Integer, Integer> suondMap;
    public static Context context;

    /**
     * ��ʼ��������
     * @param context
     */
    public static void initSoundPool(Context context){
        SoundUtil.context = context;
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        suondMap = new HashMap<Integer, Integer>();
        suondMap.put(1, sp.load(context, R.raw.beep, 1));

    }

    /**
     * ��������������
     * @param sound
     * @param number
     */
    public static  void play(int sound, int number){
        AudioManager am = (AudioManager) SoundUtil.context.getSystemService(SoundUtil.context.AUDIO_SERVICE);
        //���ص�ǰAlarmManager�������
        float audioMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //���ص�ǰAudioManager���������ֵ
        float audioCurrentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolume/audioMaxVolume;
        sp.play(
                suondMap.get(sound), //���ŵ�����Id
                audioCurrentVolume, //����������
                audioCurrentVolume, //����������
                1, //���ȼ���0Ϊ���
                number, //ѭ��������0�޲�ѭ����-1����Զѭ��
                1);//�ط��ٶȣ�ֵ��0.5-2.0֮�䣬1Ϊ�����ٶ�
    }
}
