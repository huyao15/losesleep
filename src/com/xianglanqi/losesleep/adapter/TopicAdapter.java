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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xianglanqi.losesleep.R;
import com.xianglanqi.losesleep.model.Topic;
import com.xianglanqi.losesleep.util.DateUtil;
import com.xianglanqi.losesleep.util.HttpUtil;

public class TopicAdapter extends BaseAdapter {

    private List<Topic> topics = new ArrayList<Topic>();

    private Context context;

    private int page = 0;

    public void load() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... arg0) {
                try {
                    return HttpUtil.getString("http://115.29.36.59:8080/ls/topic/list?page=" + page);
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
                if (null == result) {
                    return;
                }
                try {
                    JSONObject jo = new JSONObject(result);
                    if (jo.optInt("code") == 0) {
                        JSONArray data = jo.optJSONArray("data");
                        List<Topic> ts = new ArrayList<Topic>();
                        for (int i = 0; i < data.length(); i++) {
                            ts.add(Topic.parse(data.getJSONObject(i)));
                        }
                        TopicAdapter.this.addTopics(ts);
                        TopicAdapter.this.notifyDataSetChanged();
                        page++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };

        }.execute();
    }

    public TopicAdapter(Context context) {
        this.context = context;
    }

    public void addTopics(List<Topic> topics) {
        this.topics.addAll(topics);
    }

    @Override
    public int getCount() {
        return this.topics.size();
    }

    @Override
    public Object getItem(int pos) {
        return this.topics.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_topic, null);
            holder = new ViewHolder();
            holder.openDay = (TextView) convertView.findViewById(R.id.openDay);
            holder.openMonth = (TextView) convertView.findViewById(R.id.openMonth);
            holder.subject = (TextView) convertView.findViewById(R.id.subject);
            holder.viewDetail = (TextView) convertView.findViewById(R.id.viewDetail);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Topic topic = this.topics.get(pos);
        holder.openDay.setText(DateUtil.getDayString(topic.getOpenDate()));
        holder.openMonth.setText(DateUtil.getMonthString(topic.getOpenDate()));
        if (pos == 0) {
            holder.openDay.setTextColor(context.getResources().getColor(R.color.day_color_first));
            holder.openMonth.setTextColor(context.getResources().getColor(R.color.day_color_first));
        } else {
            holder.openDay.setTextColor(context.getResources().getColor(R.color.day_color_other));
            holder.openMonth.setTextColor(context.getResources().getColor(R.color.day_color_other));
        }
        holder.subject.setText(topic.getSubject());
        holder.viewDetail.setText(String.format("围观: %d 参与: %d", topic.getViewCount(), topic.getCommentCount()));

        return convertView;
    }

    static class ViewHolder {
        TextView openDay;
        TextView openMonth;
        TextView subject;
        TextView viewDetail;
    }

}
