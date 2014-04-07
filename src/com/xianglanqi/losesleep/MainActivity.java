package com.xianglanqi.losesleep;

import com.xianglanqi.losesleep.adapter.TopicAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends Activity {

    private ViewPager viewPager;
    private TopicAdapter topicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initPagerView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.topicAdapter.load();
    }

    private void initPagerView() {
        final LayoutInflater inflater = LayoutInflater.from(this);

        final View[] views = new View[] { inflater.inflate(R.layout.page_topic, null),
                inflater.inflate(R.layout.page_suggest, null), inflater.inflate(R.layout.page_quan, null) };
        final String[] titles = new String[] { "失眠话题", "失眠圈", "失眠推荐" };

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        // 填充ViewPager的数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return views.length;
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView(views[position]);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager) container).addView(views[position]);
                return views[position];
            }
        };

        viewPager.setAdapter(mPagerAdapter);

        final ListView topicList = (ListView) views[0].findViewById(R.id.topicList);
        topicAdapter = new TopicAdapter(this);
        topicList.setAdapter(this.topicAdapter);
    }

}
