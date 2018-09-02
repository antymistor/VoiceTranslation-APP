package com.tangao.test;
import org.json.JSONException;
import org.json.JSONObject;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

public class IflytekWakeUp {

    private Context mContext;
    //唤醒的阈值，就相当于门限值，当用户输入的语音的置信度大于这一个值的时候，才被认定为成功唤醒。
    private int curThresh = 1500;
    //是否持续唤醒
    private String keep_alive = "0";
    private String ivwNetMode = "0";
    // 语音唤醒对象
    private VoiceWakeuper mIvw;
    //存储唤醒词的ID
    private String wordID = "";
    private resultresolve listener;

    public IflytekWakeUp(Context context,resultresolve lis) {
        this.listener=lis;
        this.mContext = context;
        mIvw = VoiceWakeuper.createWakeuper(mContext, null);
    }
    public void startWakeuper() {
        mIvw = VoiceWakeuper.getWakeuper();
        if (mIvw != null) {
            mIvw.setParameter(SpeechConstant.PARAMS, null);
            mIvw.setParameter(SpeechConstant.IVW_THRESHOLD, "0:" + curThresh);
            mIvw.setParameter(SpeechConstant.IVW_SST, "wakeup");
            mIvw.setParameter(SpeechConstant.KEEP_ALIVE, keep_alive);
            mIvw.setParameter(SpeechConstant.IVW_NET_MODE, ivwNetMode);
            mIvw.setParameter(SpeechConstant.IVW_RES_PATH, getResource());
            mIvw.startListening(new MyWakeuperListener());
        }
    }

    private String getResource() {
        final String resPath = ResourceUtil.generateResourcePath(mContext, RESOURCE_TYPE.assets, "ivw/" + mContext.getString(R.string.IflytekAPP_id) + ".jet");
        return resPath;
    }
    /**
     * 销毁唤醒功能
     */
    public void destroyWakeuper() {
        // 销毁合成对象
        mIvw = VoiceWakeuper.getWakeuper();
        if (mIvw != null) {
            mIvw.destroy();
        }
    }
    /**
     * 停止唤醒
     */
    public void stopWakeuper() {
        mIvw.stopListening();
    }
    /**
     * 唤醒词监听类
     *
     * @author Administrator
     */
    private class MyWakeuperListener implements WakeuperListener {

        @Override
        public void onVolumeChanged(int arg0) {}
        //开始说话
        @Override
        public void onBeginOfSpeech() {}
        //错误码返回
        @Override
        public void onError(SpeechError arg0) {}
        @Override
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {}
        @Override
        public void onResult(WakeuperResult result) {
          listener.resolveresult("waked");
        }
    }
}