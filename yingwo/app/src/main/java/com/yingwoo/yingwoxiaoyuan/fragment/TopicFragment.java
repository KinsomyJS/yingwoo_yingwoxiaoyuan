package com.yingwoo.yingwoxiaoyuan.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yingwoo.yingwoxiaoyuan.Adapter.TopicRecycleAdapter;
import com.yingwoo.yingwoxiaoyuan.MyApplication;
import com.yingwoo.yingwoxiaoyuan.PopUpWindow.Comfirm_PopUp;
import com.yingwoo.yingwoxiaoyuan.PopUpWindow.HomePage_Action_PopUp;
import com.yingwoo.yingwoxiaoyuan.PostActivity;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.View.XRecyclerView;
import com.yingwoo.yingwoxiaoyuan.model.PostModel;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopicFragment extends Fragment implements View.OnTouchListener {
    private XRecyclerView mRecyclerView;
    private List<PostModel.InfoBean> data;
    private UserinfoService userinfoService;
    private int GETDATA = 103;
    private HomePage_Action_PopUp homePage_action_popUp;
    private Comfirm_PopUp comfirm_popUp;
    private Activity activity;
    boolean isFirst;
    private TopicRecycleAdapter recycleAdapter;
    private TopicMainFragment topicMainFragment;
    private boolean refleshFlag;
    private boolean loadMoreFlag;


    public static TopicFragment newInstance(TopicMainFragment topicMainFragment, Bundle bundle) {
        TopicFragment newFragment = new TopicFragment();
        newFragment.setArguments(bundle);
        newFragment.setMainFragment(topicMainFragment);
        return newFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        mRecyclerView = (XRecyclerView) inflater.inflate(R.layout.fragment_activity_topic, container, false);
        userinfoService = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        refleshFlag = false;
        loadMoreFlag = false;
        mRecyclerView.setOnTouchListener(this);
        return mRecyclerView;
    }

    private void setMainFragment(TopicMainFragment topicMainFragment) {
        this.topicMainFragment = topicMainFragment;
    }

    public TopicMainFragment getTopicMainFragment() {
        return topicMainFragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isFirst = true;
        data = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        createItemList(0);
    }

    public void createItemList(int startId) {
        int topic_id = (int) getArguments().get("topic_id");
        boolean isHot = getArguments().getBoolean("isHot");
        Map<String, Object> map = new HashMap<>();
        map.put("topic_id", topic_id);
        map.put("start_id", startId);
        if (isHot) {
            map.put("sort", "hot");
        }
        userinfoService.getPostList(map)
                .map(new Func1<PostModel, List<PostModel.InfoBean>>() {
                    @Override
                    public List<PostModel.InfoBean> call(PostModel topicModel) {
                        return topicModel.getInfo();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PostModel.InfoBean>>() {
                               @Override
                               public void onCompleted() {
                                   Log.d("Home", "Completed");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
                               }

                               @Override
                               public void onNext(List<PostModel.InfoBean> infoBeen) {
                                   data.clear();
                                   data = infoBeen;
                                   Message message = handler.obtainMessage();
                                   message.what = GETDATA;
                                   message.obj = infoBeen;
                                   message.sendToTarget();

                               }

                           }

                );
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GETDATA) {
                if (isFirst) {
                    isFirst = false;
                    recycleAdapter = new TopicRecycleAdapter(MyApplication.getGlobalContext(), TopicFragment.this);
                    recycleAdapter.setOnItemClickListener(new TopicRecycleAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, PostModel.InfoBean data) {
                            Intent intent = new Intent(getContext()
                                    , PostActivity.class);
                            intent.putExtra("top_id", data.getId());
                            startActivity(intent);
                        }
                    });
                    mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                        @Override
                        public void onRefresh() {
                            refleshFlag = true;
                            createItemList(0);
                        }

                        @Override
                        public void onLoadMore() {
                            if (data.size() > 0) {
                                createItemList(Integer.parseInt(data.get(data.size() - 1).getId()));
                                loadMoreFlag = true;
                            }
                        }
                    });
                    mRecyclerView.setAdapter(recycleAdapter);
                    recycleAdapter.addData(data);
                } else {
                    if (refleshFlag) {
                        recycleAdapter.clearData();
                        recycleAdapter.addData(data);
                        refleshFlag = false;
                        mRecyclerView.refreshComplete();
                    }
                    if (loadMoreFlag) {
                        recycleAdapter.addData(data);
                        loadMoreFlag = false;
                        mRecyclerView.loadMoreComplete();
                    }
                }
            }
        }
    };


    public void showPop() {
        homePage_action_popUp = new HomePage_Action_PopUp(activity, recycleAdapter.Pop_onClick);
        homePage_action_popUp.showAtLocation(activity.findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);
    }

    public void cancelPop() {
        if (homePage_action_popUp.isShowing()) {
            homePage_action_popUp.dismiss();
        }
    }

    public void showConfirmPop() {
        comfirm_popUp = new Comfirm_PopUp(activity, recycleAdapter.Pop_onClick);
        comfirm_popUp.showAtLocation(activity.findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);
    }

    public void cancelConfirmPop() {
        if (comfirm_popUp.isShowing()) {
            comfirm_popUp.dismiss();
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
            if (getTopicMainFragment().isLoading()) {
                mRecyclerView.setPullRefreshEnabled(true);
            } else {
                mRecyclerView.setPullRefreshEnabled(false);
            }
        }
        if (getTopicMainFragment().betweenTitleView()){
            getTopicMainFragment().hideTitle();
        }else {
            getTopicMainFragment().showTitle();
        }
        return false;
    }
}
