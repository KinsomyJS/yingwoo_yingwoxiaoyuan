package com.yingwoo.yingwoxiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yingwoo.yingwoxiaoyuan.Adapter.TopicListAdapter;
import com.yingwoo.yingwoxiaoyuan.View.AutoLoadRecyclerView;
import com.yingwoo.yingwoxiaoyuan.model.SubjectModdel;
import com.yingwoo.yingwoxiaoyuan.model.TopicListModel;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 主题列表界面
 * 展示改主题下的所有话题
 * Created by FJS0420 on 2016/9/26.
 */

public class SubjectHomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerview)
    AutoLoadRecyclerView xRecyclerView;
    private UserinfoService userinfoService = null;
    private SubjectModdel.InfoBean subject = null;
    private List<TopicListModel.InfoBean> data;
    private static final int TopicMeg = 352;
    private boolean refreshFlag;
    private TopicListAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_subjecthome);
        AppManager.getAppManager().addActivity(this);
        initView();
        initData();
    }

    private void initView() {
        ButterKnife.bind(this);
        refreshFlag = false;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = this.getIntent();
        subject = (SubjectModdel.InfoBean) intent.getSerializableExtra("Subject");
        tv_title.setText(subject.getTitle());
    }

    private void initData() {
        userinfoService = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        Log.d("subjecthome_subjectid", subject.getId());
        userinfoService.getTopicList(Integer.parseInt(subject.getId()), 0)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TopicListModel>() {
                    @Override
                    public void onCompleted() {
                        Log.d("RecyclerViewSimpleFragm", "Success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RecyclerViewSimpleFragm", "Error");
                    }

                    @Override
                    public void onNext(TopicListModel topicListModel) {
                        if (topicListModel.getStatus() == 1) {
                            data = topicListModel.getInfo();
                            Message message = handler.obtainMessage();
                            message.what = TopicMeg;
                            message.sendToTarget();
                        }
                    }
                });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TopicMeg) {
                if (!refreshFlag) {
                    RecyclerView.LayoutManager manager = new LinearLayoutManager(SubjectHomeActivity.this);
                    recyclerAdapter = new TopicListAdapter(SubjectHomeActivity.this);
                    xRecyclerView.setLayoutManager(manager);
                    xRecyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.setItems(data);
                    xRecyclerView.setLoadingMoreEnabled(false);
                    xRecyclerView.setLoadingListener(new com.yingwoo.yingwoxiaoyuan.View.XRecyclerView.LoadingListener() {
                        @Override
                        public void onRefresh() {
                            refreshFlag = true;
                            initData();
                        }

                        @Override
                        public void onLoadMore() {

                        }
                    });
                }else {
                    recyclerAdapter.setItems(data);
                    refreshFlag = false;
                    xRecyclerView.refreshComplete();
                }
            }
        }
    };
}
