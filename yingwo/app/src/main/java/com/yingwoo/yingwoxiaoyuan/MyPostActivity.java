package com.yingwoo.yingwoxiaoyuan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.yingwoo.yingwoxiaoyuan.Adapter.HomePageRecycleAdapter;
import com.yingwoo.yingwoxiaoyuan.PopUpWindow.Comfirm_PopUp;
import com.yingwoo.yingwoxiaoyuan.PopUpWindow.HomePage_Action_PopUp;
import com.yingwoo.yingwoxiaoyuan.View.AutoLoadRecyclerView;
import com.yingwoo.yingwoxiaoyuan.View.XRecyclerView;
import com.yingwoo.yingwoxiaoyuan.model.PostModel;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by FJS0420 on 2016/8/5.
 */

public class MyPostActivity extends AppCompatActivity {


    private int GETDATA = 103;
    private Toolbar toolbar = null;
    private HomePage_Action_PopUp homePage_action_popUp;
    private Comfirm_PopUp comfirm_popUp;
    private AutoLoadRecyclerView recyclerView = null;
    private HomePageRecycleAdapter recycleAdapter;
    private HttpControl httpControl;
    boolean isFirst;
    private List<PostModel.InfoBean> data = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private boolean refleshFlag = false;
    private boolean loadMoreFlag = false;
    private int list_filter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);
        httpControl = HttpControl.getInstance();
        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(this);
        init();
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
    }

    private void init() {
        ButterKnife.bind(this);
        isFirst = true;
        getList(0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        AppManager.getAppManager().addActivity(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (AutoLoadRecyclerView) findViewById(R.id.recycleview);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void getList(int start_id) {
        UserinfoService userinfoService = httpControl.getRetrofit().create(UserinfoService.class);
        userinfoService.getMyPost(start_id)
                .map(new Func1<PostModel, List<PostModel.InfoBean>>() {
                    @Override
                    public List<PostModel.InfoBean> call(PostModel postModel) {
                        return postModel.getInfo();
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
                        Toast.makeText(MyPostActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<PostModel.InfoBean> infoBeen) {
                        data.clear();
                        data = infoBeen;
                        if (data.size() > 0) {
                            Message message = handler.obtainMessage();
                            message.what = GETDATA;
                            message.obj = infoBeen;
                            message.sendToTarget();
                        } else {
                            recyclerView.hideFooter();
                            recyclerView.loadMoreComplete();
                        }
                    }
                });
    }


    public void showPop() {
        homePage_action_popUp = new HomePage_Action_PopUp(MyPostActivity.this, recycleAdapter.Pop_onClick);
        homePage_action_popUp.showAtLocation(findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);
    }

    public void cancelPop() {
        if (homePage_action_popUp.isShowing()) {
            homePage_action_popUp.dismiss();
        }
    }

    public void showConfirmPop() {
        comfirm_popUp = new Comfirm_PopUp(MyPostActivity.this, recycleAdapter.Pop_onClick);
        comfirm_popUp.showAtLocation(findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);
    }

    public void cancelConfirmPop() {
        if (comfirm_popUp.isShowing()) {
            comfirm_popUp.dismiss();
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == GETDATA) {
                if (isFirst) {
                    isFirst = false;
                    recycleAdapter = new HomePageRecycleAdapter(MyPostActivity.this);
                    recyclerView.setAdapter(recycleAdapter);
                    recycleAdapter.addData(data);
                    recyclerView.setLoadingMoreEnabled(true);
                    recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

                        @Override
                        public void onRefresh() {
                            refleshFlag = true;
                            getList(0);
                        }

                        @Override
                        public void onLoadMore() {
                            if (data.size() > 0) {
                                getList(Integer.parseInt(data.get(data.size() - 1).getId()));
                                recyclerView.setLoadingMoreEnabled(false);
                                recyclerView.showFooter();
                                loadMoreFlag = true;
                            }
                        }
                    });
                    recycleAdapter.setOnItemClickListener(new HomePageRecycleAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, PostModel.InfoBean data) {
                            Intent intent = new Intent(MyPostActivity.this, PostActivity.class);
                            intent.putExtra("top_id", data.getId());
                            startActivity(intent);
                        }
                    });
                } else {
                    if (refleshFlag) {
                        recycleAdapter.clearData();
                        recycleAdapter.addData(data);
                        refleshFlag = false;
                        recyclerView.refreshComplete();
                    }
                    if (loadMoreFlag) {
                        recycleAdapter.addData(data);
                        recyclerView.setLoadingMoreEnabled(true);
                        recyclerView.hideFooter();
                        loadMoreFlag = false;
                        recyclerView.loadMoreComplete();
                    }
                }
            }
        }
    };
}
