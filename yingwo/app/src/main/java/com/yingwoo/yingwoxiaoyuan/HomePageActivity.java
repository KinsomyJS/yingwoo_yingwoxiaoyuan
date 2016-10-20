package com.yingwoo.yingwoxiaoyuan;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yingwoo.yingwoxiaoyuan.Adapter.HomePageRecycleAdapter;
import com.yingwoo.yingwoxiaoyuan.PopUpWindow.Comfirm_PopUp;
import com.yingwoo.yingwoxiaoyuan.PopUpWindow.HomePage_Action_PopUp;
import com.yingwoo.yingwoxiaoyuan.PopUpWindow.Home_PopUp;
import com.yingwoo.yingwoxiaoyuan.View.AutoLoadRecyclerView;
import com.yingwoo.yingwoxiaoyuan.View.XRecyclerView;
import com.yingwoo.yingwoxiaoyuan.model.PostModel;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.ScrollUtil;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.yingwoo.yingwoxiaoyuan.MyApplication.context;

/**
 * Created by FJS0420 on 2016/8/5.
 */

public class HomePageActivity extends AppCompatActivity {


    @BindView(R.id.action_home)
    ImageView action_home;
    @BindView(R.id.action_find)
    ImageView action_find;
    @BindView(R.id.action_add)
    ImageView action_add;
    @BindView(R.id.action_bub)
    ImageView action_bub;
    @BindView(R.id.action_head)
    ImageView action_head;
    @BindView(R.id.tabview)
    RelativeLayout tabview;
    @BindView(R.id.layout_action_home)
    LinearLayout layoutActionHome;
    @BindView(R.id.layout_action_find)
    LinearLayout layoutActionFind;
    @BindView(R.id.layout_action_bub)
    LinearLayout layoutActionBub;
    @BindView(R.id.layout_action_head)
    LinearLayout layoutActionHead;

    private int GETDATA = 103;
    private Toolbar toolbar = null;
    private Home_PopUp home_popUp;
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
    private int menuResid = R.id.tv_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        MyApplication.setWidth(wm.getDefaultDisplay().getWidth() - 100);
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
        getList(0, list_filter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        AppManager.getAppManager().addActivity(this);
        action_home.setImageResource(R.mipmap.home_g);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, rotationHolder);
                animator.setDuration(2000);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.start();
                home_popUp = new Home_PopUp(HomePageActivity.this, new MenuOnClickListener());
                home_popUp.changeTextColor(menuResid);
                home_popUp.showAsDropDown(toolbar, 10, 15);
            }
        });


        recyclerView = (AutoLoadRecyclerView) findViewById(R.id.recycleview);
    }

    @OnClick(R.id.action_home)
    public void refresh(){
        refleshFlag = true;
        getList(0, list_filter);
        recyclerView.scrollToPosition(0);
    }

    @OnClick(R.id.layout_action_find)
    public void tofind() {
        startActivity(new Intent(this, FindActivity.class));
    }

    @OnClick(R.id.layout_action_bub)
    public void toPost() {
        Toast.makeText(this,"正在开发中~~",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.layout_action_head)
    public void toHome() {
        startActivity(new Intent(this, PersonCenterActivity.class));
    }

    @OnClick(R.id.action_add)
    public void toRefreshthing() {
        Intent addPost = new Intent(this, RefreshThingActivity.class);
        startActivity(addPost);
    }

    public class MenuOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_all:
                    list_filter = 0;
                    break;
                case R.id.tv_new:
                    list_filter = 1;
                    break;
                case R.id.tv_attention:
                    list_filter = 2;
                    break;
//                case R.id.tv_friendthing:
//                    Toast.makeText(HomePageActivity.this, "toFriendThing", Toast.LENGTH_SHORT).show();
//                    break;
            }
            isFirst = true;
            getList(0, list_filter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Toast.makeText(HomePageActivity.this,"正在开发中~",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
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

    public void getList(int start_id, int filter) {
        UserinfoService userinfoService = httpControl.getRetrofit().create(UserinfoService.class);
        userinfoService.getAllList(start_id, filter)
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
                        Toast.makeText(HomePageActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
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
//                        recycleAdapter.notifyItemRemoved(recycleAdapter.getItemCount());
//                        recycleAdapter = new HomePageRecycleAdapter(HomePageActivity.this, infoBeen);
//                        recyclerView.setAdapter(recycleAdapter);
//                        recycleAdapter.setOnItemClickListener(new HomePageRecycleAdapter.OnRecyclerViewItemClickListener() {
//                            @Override
//                            public void onItemClick(View view, PostModel.InfoBean data) {
//                                Intent intent = new Intent(HomePageActivity.this, PostActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("top", data);
//                                intent.putExtra("topBundle", bundle);
//                                startActivity(intent);
//                            }
//                        });

                    }
                });
    }


    public void showPop() {
        homePage_action_popUp = new HomePage_Action_PopUp(HomePageActivity.this, recycleAdapter.Pop_onClick);
        homePage_action_popUp.showAtLocation(findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);
    }

    public void cancelPop() {
        if (homePage_action_popUp.isShowing()) {
            homePage_action_popUp.dismiss();
        }
    }

    public void showConfirmPop() {
        comfirm_popUp = new Comfirm_PopUp(HomePageActivity.this, recycleAdapter.Pop_onClick);
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
                    recycleAdapter = new HomePageRecycleAdapter(HomePageActivity.this);
                    recyclerView.setAdapter(recycleAdapter);
                    recycleAdapter.addData(data);
                    recyclerView.setLoadingMoreEnabled(true);
                    recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

                        @Override
                        public void onRefresh() {
                            refleshFlag = true;
                            getList(0, list_filter);
                        }

                        @Override
                        public void onLoadMore() {
                            if (data.size() > 0) {
                                getList(Integer.parseInt(data.get(data.size() - 1).getId()), list_filter);
                                recyclerView.setLoadingMoreEnabled(false);
                                recyclerView.showFooter();
                                loadMoreFlag = true;
                            }
                        }
                    });
                    recyclerView.setOnScrollListener(new ScrollUtil.inVisibleScorllListener() {
                        @Override
                        public void onHide() {
                            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tabview.getLayoutParams();
                            int fabBottomMargin = lp.bottomMargin;
                            tabview.animate().translationY(tabview.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
                        }

                        @Override
                        public void onShow() {
                            tabview.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                        }
                    });
                    switch (list_filter) {
                        case 0:
                            menuResid = R.id.tv_all;
                            break;
                        case 1:
                            menuResid = R.id.tv_new;
                            break;
                        case 2:
                            menuResid = R.id.tv_attention;
                            break;
                        default:
                            break;
                    }
                    if (home_popUp != null)
                        home_popUp.changeTextColor(menuResid);
                    recycleAdapter.setOnItemClickListener(new HomePageRecycleAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, PostModel.InfoBean data) {
                            Intent intent = new Intent(HomePageActivity.this, PostActivity.class);
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
