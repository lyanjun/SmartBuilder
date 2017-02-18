package com.study.lyan.smartbuilder.listener;

import android.os.Bundle;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Author LYJ
 * Created on 2017/2/18.
 * Time 09:56
 * 语音合成监听器
 */

public class TTSSynthesizerListener implements SynthesizerListener {
    /**
     * 开始播放
     */
    @Override
    public void onSpeakBegin() {

    }
   /** 缓冲进度回调
    * percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
    */
   @Override
    public void onBufferProgress(int i, int i1, int i2, String s) {

    }

    /**
     * 暂停播放
     */
    @Override
    public void onSpeakPaused() {

    }

    /**
     * 恢复播放回调接口
     */
    @Override
    public void onSpeakResumed() {

    }

    /**
     * 播放进度回调
     * percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void onSpeakProgress(int i, int i1, int i2) {

    }

    /**
     * 会话结束回调接口，没有错误时，error为null
     * @param speechError
     */
    @Override
    public void onCompleted(SpeechError speechError) {

    }

    /**
     * 会话事件回调接口
     * @param i
     * @param i1
     * @param i2
     * @param bundle
     */
    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }
}
