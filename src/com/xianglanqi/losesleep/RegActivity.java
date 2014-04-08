package com.xianglanqi.losesleep;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.xianglanqi.losesleep.model.User;
import com.xianglanqi.losesleep.util.DataUtil;
import com.xianglanqi.losesleep.util.HttpUtil;

public class RegActivity extends Activity {

    private EditText userName;

    private RadioButton boy;

    private RadioButton girl;

    private Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reg);
        userName = (EditText) findViewById(R.id.userName);
        boy = (RadioButton) findViewById(R.id.userGenderBoy);
        girl = (RadioButton) findViewById(R.id.userGenderGirl);
        reg = (Button) findViewById(R.id.regAction);
        reg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final String name = userName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    return;
                }
                new AsyncTask<Void, Void, String>() {

                    @Override
                    protected String doInBackground(Void... arg0) {
                        try {
                            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            String deviceId = tm.getDeviceId();
                            return HttpUtil.getString(String
                                    .format("http://115.29.36.59:8080/ls/user/reg?deviceId=%s&system=android&name=%s&gender=%d&avatar=",
                                            deviceId, name, boy.isChecked() ? 1 : 0));
                        } catch (ClientProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    protected void onPostExecute(String result) {
                        Log.d("hy", result);
                        if (null == result) {
                            return;
                        }
                        try {
                            JSONObject jo = new JSONObject(result);
                            if (jo.optInt("code") == 0) {
                                DataUtil.saveUser(RegActivity.this, User.parse(jo.optJSONObject("data")));
                                finish();
                                startActivity(new Intent(RegActivity.this, MainActivity.class));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    };

                }.execute();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
