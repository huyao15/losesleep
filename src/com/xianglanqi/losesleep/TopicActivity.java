package com.xianglanqi.losesleep;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xianglanqi.losesleep.adapter.PostAdapter;
import com.xianglanqi.losesleep.model.Topic;
import com.xianglanqi.losesleep.model.User;
import com.xianglanqi.losesleep.util.DataUtil;
import com.xianglanqi.losesleep.util.DateUtil;
import com.xianglanqi.losesleep.util.HttpUtil;

public class TopicActivity extends Activity {

    private Topic topic;

    private ListView postListView;

    private PostAdapter postAdapter;

    private TextView topicDay;

    private TextView currentTopic;

    private Button postAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_topic);

        topic = (Topic) getIntent().getSerializableExtra("topic");
        topicDay = (TextView) findViewById(R.id.topic_day);
        topicDay.setText(DateUtil.getDayShow(topic.getOpenDate()));
        currentTopic = (TextView) findViewById(R.id.currentTopic);
        currentTopic.setText("今日话题: " + topic.getSubject());
        postListView = (ListView) findViewById(R.id.postList);
        postAdapter = new PostAdapter(this, topic);
        postListView.setAdapter(postAdapter);
        postAction = (Button) findViewById(R.id.postAction);
        postAction.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final EditText post = new EditText(TopicActivity.this);
                post.setLines(6);
                post.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                new AlertDialog.Builder(TopicActivity.this).setTitle("参与话题").setView(post)
                        .setPositiveButton("提交", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String content = post.getText().toString();
                                if (TextUtils.isEmpty(content)) {
                                    return;
                                }
                                final User user = DataUtil.loadUser(TopicActivity.this);

                                new AsyncTask<Void, Void, String>() {

                                    @Override
                                    protected String doInBackground(Void... arg0) {
                                        try {
                                            return HttpUtil.getString(String.format(
                                                    "http://115.29.36.59:8080/ls/tpc-%d/post/add?deviceId=%s&post=%s",
                                                    topic.getId(), user.getDeviceId(), content));
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
                                                postAdapter.load();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    };

                                }.execute();
                            }

                        }).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        postAdapter.load();
    }
}
