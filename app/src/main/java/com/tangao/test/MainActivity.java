package com.tangao.test;

import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends Activity {
    public EditText etText;
    private Button btnStartSpeak;
    private EditText etHeCheng;
    private Button btnStartHeCheng;
    public IflytekWakeUp wkup;
    public IflytekSpeech spe;
    public Iflytekrecognize rec;
    public GetTranslation trs;
    private boolean ison = false;
    public Handler handler =new Handler();
    private resultresolve resolver =new resultresolve()
    {
        @Override
        public void resolveresult(String str) {
            if(str.equals("speechover"))
            { if(ison){ison=false;rec.listening();}
               else {wkup.startWakeuper();}}
            else{
                if(str.equals("waked"))
                {    ison=true;
                    spe.Speek("你需要翻译什么");
                }
                else
                {
                    trs.translate(0,str,translatecall);
                   // spe.Speek(str);
                }
            }
        }
    };
    private okhttp3.Callback translatecall = new okhttp3.Callback(){
        @Override
        public void onResponse(Call call, final Response response)throws IOException
        {
            handler.post(new Runnable() {
            @Override
            public void run() {
                    try {
                        JSONObject jsonob = new JSONObject(response.body().string());
                        etText.setText(jsonob.getString("translation"));
                        //etText.setText(response.body().string());
                        spe.Speek(jsonob.getString("translation"));
                    }catch (Exception e)
                    {e.printStackTrace();}
            }
        });
        }
        @Override
        public void onFailure(Call call, IOException e) {;}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 语音配置对象初始化(如果只使用 语音识别 或 语音合成 时都得先初始化这个)
        SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID +"="+getString(R.string.IflytekAPP_id));
        wkup= new IflytekWakeUp(this,resolver);
        spe=new IflytekSpeech(this,resolver);
        rec=new Iflytekrecognize(this,resolver);
        wkup.startWakeuper();
        etText = (EditText) findViewById(R.id.main_et_text);
         trs=new GetTranslation(this);
        // trs.translate(0,"你要做什么,其实我自己也不知道",translatecall);

    }

}