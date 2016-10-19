package com.yingwoo.yingwoxiaoyuan.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yingwoo.yingwoxiaoyuan.Adapter.MyTopicAdapter;
import com.yingwoo.yingwoxiaoyuan.View.XRecyclerView;
import com.yingwoo.yingwoxiaoyuan.model.FieldModel;
import com.yingwoo.yingwoxiaoyuan.model.MyTopicEntity;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;
import com.yingwoo.yingwoxiaoyuan.R;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTopicFragment extends Fragment {
    private XRecyclerView mRecyclerView;
    private List<MyTopicEntity.InfoBean> data;
    private UserinfoService userinfoService;
    private static final int MyTopicMeg = 353;

    public static MyTopicFragment newInstance(Context context, Bundle bundle) {
        MyTopicFragment newFragment = new MyTopicFragment();
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (XRecyclerView) inflater.inflate(R.layout.fragment_activity_new, container, false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                createItemList();
            }

            @Override
            public void onLoadMore() {

            }
        });
        userinfoService = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        createItemList();
    }

    public void createItemList() {
        FieldModel.InfoBean fieldbean = (FieldModel.InfoBean) getArguments().get("fieldbean");
        userinfoService.getMyTopicList(Integer.parseInt(fieldbean.getId()))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyTopicEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("RecyclerViewSimpleFragm", "Success");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RecyclerViewSimpleFragm", "Error");
                    }

                    @Override
                    public void onNext(MyTopicEntity myTopicEntity) {
                        if (myTopicEntity.getStatus() == 1) {
                            data = myTopicEntity.getInfo();
                            Message message = handler.obtainMessage();
                            message.what = MyTopicMeg;
                            message.sendToTarget();
                        }
                    }
                });
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MyTopicMeg) {
                MyTopicAdapter recyclerAdapter = new MyTopicAdapter((AppCompatActivity) getActivity());
                recyclerAdapter.addItems(data);
                mRecyclerView.setAdapter(recyclerAdapter);
                mRecyclerView.refreshComplete();
            }
        }
    };
}
