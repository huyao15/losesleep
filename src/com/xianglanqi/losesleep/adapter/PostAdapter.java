package com.xianglanqi.losesleep.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.xianglanqi.losesleep.R;
import com.xianglanqi.losesleep.model.Topic;
import com.xianglanqi.losesleep.model.TopicPost;
import com.xianglanqi.losesleep.util.DateUtil;
import com.xianglanqi.losesleep.util.HttpUtil;

public class PostAdapter extends BaseAdapter {

    private Topic topic;

    private List<TopicPost> posts = new ArrayList<TopicPost>();

    private Context context;

    private int page = 0;

    public PostAdapter(Context context, Topic topic) {
        this.context = context;
        this.topic = topic;
    }

    public void addPosts(List<TopicPost> posts) {
        this.posts.addAll(posts);
    }

    public void load() {
        page = 0;
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... arg0) {
                try {
                    return HttpUtil.getString(String.format("http://115.29.36.59:8080/ls/tpc-%d/post/list?page=%d",
                            topic.getId(), page));
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
                        JSONArray data = jo.optJSONArray("data");
                        List<TopicPost> tps = new ArrayList<TopicPost>();
                        for (int i = 0; i < data.length(); i++) {
                            tps.add(TopicPost.parse(data.getJSONObject(i)));
                        }
                        PostAdapter.this.addPosts(tps);
                        PostAdapter.this.notifyDataSetChanged();
                        page++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };

        }.execute();
    }

    @Override
    public int getCount() {
        return this.posts.size();
    }

    @Override
    public Object getItem(int pos) {
        return this.posts.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_post, null);
            holder = new ViewHolder();
            holder.userAvatar = (ImageView) convertView.findViewById(R.id.userAvatar);
            holder.userName = (TextView) convertView.findViewById(R.id.userName);
            holder.addTime = (TextView) convertView.findViewById(R.id.addTime);
            holder.post = (TextView) convertView.findViewById(R.id.post);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TopicPost post = this.posts.get(pos);
        UrlImageViewHelper.setUrlDrawable(holder.userAvatar, post.getUserAvatar());
        holder.userName.setText(post.getUserName());
        holder.addTime.setText(DateUtil.getTimeShow(post.getAddTime()));
        holder.post.setText(post.getPost());

        return convertView;
    }

    static class ViewHolder {
        ImageView userAvatar;
        TextView userName;
        TextView addTime;
        TextView post;
    }

}
