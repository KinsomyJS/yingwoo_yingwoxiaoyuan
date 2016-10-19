package com.yingwoo.yingwoxiaoyuan;

import android.app.Activity;
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
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;
import com.yingwoo.yingwoxiaoyuan.Adapter.PostRecyclerAdapter;
import com.yingwoo.yingwoxiaoyuan.Listener.SoftKeyBoardListener;
import com.yingwoo.yingwoxiaoyuan.PopUpWindow.Command_PopUp;
import com.yingwoo.yingwoxiaoyuan.View.AutoLoadRecyclerView;
import com.yingwoo.yingwoxiaoyuan.View.XRecyclerView;
import com.yingwoo.yingwoxiaoyuan.model.PostDetailEntity;
import com.yingwoo.yingwoxiaoyuan.model.PostListEntity;
import com.yingwoo.yingwoxiaoyuan.model.RegisterEntity;
import com.yingwoo.yingwoxiaoyuan.model.ReplyEntity;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.ScrollUtil;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by FJS0420 on 2016/8/5.
 */

public class PostActivity extends AppCompatActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ib_like)
    ImageButton ibLike;
    @BindView(R.id.block_keyboardview)
    LinearLayout block_keyboardview;
    @BindView(R.id.tv_input)
    EditText tvInput;
    @BindView(R.id.post_comment_layout)
    LinearLayout postCommentLayout;
    @BindView(R.id.btn_emoj)
    ImageView btn_emoj;
    @BindView(R.id.comment_num)
    TextView commentNum;
    @BindView(R.id.like_num)
    TextView likeNum;
    @BindView(R.id.btn_send)
    TextView btnSend;

    private Toolbar toolbar;
    private int POSTLIST = 10;
    private int REPLYLIST = 11;
    private boolean isFirst;
    private Command_PopUp command_popUp;
    private AutoLoadRecyclerView mRecyclerVeiew;
    private PostRecyclerAdapter mAdapter;
    private Intent topIntent;
    private int doneFlag = 0;
    private ReplyEntity.InfoBean currentInfoBean = null;
    public static PostDetailEntity.InfoBean topBean;
    private String top_id;
    private List<List<ReplyEntity.InfoBean>> replyData;
    private List<PostListEntity.InfoBean> data;
    private LinearLayoutManager layoutManager;
    private InputMethodManager imm;
    private TextView.OnEditorActionListener mOnEditorActionListener = null;
    private boolean isemoj = false;
    private FrameLayout layout_emoj;
    private LinearLayout.LayoutParams layoutParams;
    private int upLikeValue = 0;
    private boolean refleshFlag = false;
    private boolean loadMoreFlag = false;
    private static final int upLikeMsg = 980;
    private static final int replyFlag = 155;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        init();
        layoutParams = (LinearLayout.LayoutParams) layout_emoj.getLayoutParams();
        setEmojiconFragment(false);
    }

    private void init() {
        isFirst = true;
        topIntent = getIntent();
        replyData = new ArrayList<>();
        top_id = topIntent.getStringExtra("top_id");
        doneFlag = 0;
        getPostDetail(Integer.parseInt(top_id));
        tvInput.setFocusable(true);
        mRecyclerVeiew = (AutoLoadRecyclerView) findViewById(R.id.rv_post);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerVeiew.setLayoutManager(layoutManager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tv_title.setText("贴子");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layout_emoj = (FrameLayout) findViewById(R.id.emojicons);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //注册软键盘的监听
        SoftKeyBoardListener.setListener(this,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
                        Log.i("keyboard-height", height + "");
                        layoutParams.height = height;
                        layout_emoj.setLayoutParams(layoutParams);
                        block_keyboardview.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void keyBoardHide(int height) {
                        Log.i("keyboard-height", height + "");
                        if (!isemoj)
                            block_keyboardview.setVisibility(View.GONE);
                    }
                });

    }

    public void initTopBean() {
        likeNum.setText(topBean.getLike_cnt());
        commentNum.setText(topBean.getReply_cnt());
        upLikeValue = Integer.parseInt(topBean.getUser_post_like());
        ibLike.setBackgroundResource(upLikeValue == 1 ? R.mipmap.heart_red : R.mipmap.heart_gray);
    }

    @OnClick(R.id.btn_emoj)
    public void showeandhidemoj() {
        if (!isemoj) {
            hideKeyboard(this);
            btn_emoj.setBackgroundResource(R.mipmap.keyboard_gray);
            isemoj = true;
        } else {
            showKeyboard(this);
            btn_emoj.setBackgroundResource(R.mipmap.emoji);
            isemoj = false;
        }

    }

    public void buildItem(View view) {
        if (topBean != null) {
            Intent intent = new Intent(this, PostBuildingActivity.class);
            intent.putExtra("post_id", topBean.getId());
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getPostList(int start_id) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService userinfoService = retrofit.create(UserinfoService.class);
        userinfoService.getPost(Integer.parseInt(topBean.getId()), start_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PostListEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("PostActivity", "Completed");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("PostActivity", "Error");
                    }

                    @Override
                    public void onNext(PostListEntity postListEntity) {
                        if (postListEntity.getStatus() != 1) {
                            onError(new Exception());
                        } else {
                            data = postListEntity.getInfo();
                            replyData.clear();
                            if (data.size() < 1) {
                                loadMoreFlag = false;
                                mRecyclerVeiew.setLoadingMoreEnabled(true);
                                mRecyclerVeiew.loadMoreComplete();
                                Message message = handler.obtainMessage();
                                message.what = POSTLIST;
                                message.sendToTarget();
                            } else {
                                Message message = handler.obtainMessage();
                                message.what = REPLYLIST;
                                message.sendToTarget();
                            }
                        }
                    }
                });
    }

    @OnClick(R.id.ib_like)
    public void Like() {
        if (topBean != null) {
            doLike(Integer.parseInt(topBean.getId()));
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == POSTLIST) {
                if (isFirst) {
                    mAdapter = new PostRecyclerAdapter(PostActivity.this);
                    mAdapter.setPopUpClickListener(new popUpClickListener());
                    mAdapter.setData(topBean, replyData, data);
                    mRecyclerVeiew.setAdapter(mAdapter);
                    mRecyclerVeiew.setLoadingMoreEnabled(true);
                    mRecyclerVeiew.setLoadingListener(new XRecyclerView.LoadingListener() {
                        @Override
                        public void onRefresh() {
                            isFirst = true;
                            doneFlag = 0;
                            refleshFlag = true;
                            getPostDetail(Integer.parseInt(top_id));
                        }

                        @Override
                        public void onLoadMore() {
                            doneFlag = 0;
                            if (data.size() > 0) {
                                getPostList(Integer.parseInt(data.get(data.size() - 1).getId()));
                                mRecyclerVeiew.setLoadingMoreEnabled(false);
                                mRecyclerVeiew.showFooter();
                                loadMoreFlag = true;
                            } else
                                mRecyclerVeiew.loadMoreComplete();
                        }
                    });
                    mRecyclerVeiew.setOnScrollListener(new ScrollUtil.inVisibleScorllListener() {
                        @Override
                        public void onHide() {
                            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) postCommentLayout.getLayoutParams();
                            int fabBottomMargin = lp.bottomMargin;
                            postCommentLayout.animate().translationY(postCommentLayout.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
                        }

                        @Override
                        public void onShow() {
                            postCommentLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                        }
                    });
                    bindOnEditorActionListener(tvInput);
                    if (refleshFlag) {
                        mRecyclerVeiew.refreshComplete();
                        refleshFlag = false;
                    }
                    isFirst = false;
                } else {
                    if (loadMoreFlag) {
                        mAdapter.addData(replyData, data);
                        loadMoreFlag = false;
                        mRecyclerVeiew.setLoadingMoreEnabled(true);
                        mRecyclerVeiew.hideFooter();
                        mRecyclerVeiew.loadMoreComplete();
                    }
                }
                mAdapter.notifyDataSetChanged();
            } else if (msg.what == REPLYLIST) {
                get_comment_list();
            } else if (msg.what == upLikeMsg) {
                if (upLikeValue == 0) {
                    ibLike.setBackgroundResource(R.mipmap.heart_red);
                    upLikeValue = 1;
                } else {
                    ibLike.setBackgroundResource(R.mipmap.heart_gray);
                    upLikeValue = 0;
                }
                ibLike.setEnabled(true);
            } else if (msg.what == replyFlag) {
                int id = msg.arg1;
                update_comment_list(id);
            }
        }
    };

    public void showSoftInput() {
        if (imm.isActive()) {
            block_keyboardview.setVisibility(View.GONE);
            imm.hideSoftInputFromWindow(tvInput.getWindowToken(), 0);
        }
        tvInput.requestFocus();
        imm.showSoftInput(tvInput, InputMethodManager.SHOW_FORCED);
    }

    @OnClick(R.id.btn_send)
    public void onClick() {
        InputMethodManager imm = (InputMethodManager) tvInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isActive()) {
            String content = this.getContent();
            ReplyEntity.InfoBean replyBean;
            replyBean = getCurrentInfoBean();
            if (!content.equals("")) {
                PostListEntity.InfoBean infoBean = mAdapter.getCurrentInfoBean();
                Map<String, Object> map = new HashMap<>();
                map.put("post_reply_id", Integer.valueOf(infoBean.getId()));
                map.put("content", content);
                if (replyBean != null) {
                    map.put("post_comment_id", replyBean.getId());
                    tvInput.setText("");
                    clearCurrentInfoBean();
                }
                post_comment(map);
            }
            imm.hideSoftInputFromWindow(tvInput.getApplicationWindowToken(), 0);
        }
        if (isemoj)
            block_keyboardview.setVisibility(View.GONE);
    }


    public void getPostDetail(int post_id) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService service = retrofit.create(UserinfoService.class);
        service.getPostDetail(post_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PostDetailEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TopicActivity", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TopicActivity", "Error");
                    }

                    @Override
                    public void onNext(final PostDetailEntity postBaseInfo) {
                        if (postBaseInfo.getStatus() == 1) {
                            topBean = postBaseInfo.getInfo();
                            initTopBean();
                            getPostList(0);
                        } else {
                            Toast.makeText(PostActivity.this, "获取信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public class popUpClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (imm.isActive()) {
                block_keyboardview.setVisibility(View.GONE);
                imm.hideSoftInputFromWindow(tvInput.getWindowToken(), 0);
            }
            command_popUp = new Command_PopUp(PostActivity.this, Pop_onClick);
            command_popUp.showAtLocation(findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);

        }

    }

    public View.OnClickListener Pop_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.copy_btn:
                    Toast.makeText(PostActivity.this, "这是复制按钮", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.report_btn:
                    Toast.makeText(PostActivity.this, "这是举报按钮", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void get_comment_list() {
        Map<String, Integer> map = new HashMap<>();
        Log.d("PostActivity", data.get(doneFlag).getId());
        map.put("post_reply_id", Integer.valueOf(data.get(doneFlag).getId()));
        map.put("page", 1);
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService userinfoService = retrofit.create(UserinfoService.class);
        userinfoService.comment_list(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReplyEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("PostActivity", "Completed");
                        if (doneFlag < data.size()) {
                            get_comment_list();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("PostActivity", "Error");
                    }

                    @Override
                    public void onNext(ReplyEntity replyEntity) {
                        if (replyEntity.getStatus() == 1) {
                            if (replyEntity.getInfo() == null) {
                                replyData.add(new ArrayList<ReplyEntity.InfoBean>());
                            } else {
                                replyData.add(replyEntity.getInfo());
                            }
                            doneFlag++;
                            if (doneFlag == data.size()) {
                                Message message = handler.obtainMessage();
                                message.what = POSTLIST;
                                message.sendToTarget();
                            }
                        } else {
                            onError(new Exception());
                        }
                    }
                });
    }


    public void update_comment_list(final int post_reply_id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("post_reply_id", post_reply_id);
        map.put("page", 1);
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService userinfoService = retrofit.create(UserinfoService.class);
        userinfoService.comment_list(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReplyEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("PostActivity", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("PostActivity", "Error");
                    }

                    @Override
                    public void onNext(ReplyEntity replyEntity) {
                        if (replyEntity.getStatus() == 1) {
                            if (replyEntity.getInfo() != null) {
                                int index = mAdapter.getIndex(String.valueOf(post_reply_id));
                                Log.d("PostActivitydadassa", "index:" + index);
                                if (index != -99) {
                                    if (mAdapter.isNoData(index)){
                                        isFirst = true;
                                        doneFlag = 0;
                                        refleshFlag = true;
                                        getPostDetail(Integer.parseInt(top_id));
                                    }else {
                                        mAdapter.updateReply(index, replyEntity.getInfo());
                                    }
                                }
                            }
                        }
                    }
                });
    }


    public void post_comment(final Map<String, Object> map) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService userinfoService = retrofit.create(UserinfoService.class);
        userinfoService.post_comment(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        tvInput.setText("");
                        int id = (Integer) map.get("post_reply_id");
                        Message message = handler.obtainMessage();
                        message.arg1 = id;
                        message.what = replyFlag;
                        message.sendToTarget();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("PostActivity4342342", "ERROR");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("PostActivity", s);
                    }
                });
    }

    public String getContent() {
        return tvInput.getText().toString();
    }

    public void bindOnEditorActionListener(EditText editText) {
        if (mOnEditorActionListener != null) {
            editText.setOnEditorActionListener(mOnEditorActionListener);
        }
    }

    public ReplyEntity.InfoBean getCurrentInfoBean() {
        return currentInfoBean;
    }

    public void setCurrentInfoBean(ReplyEntity.InfoBean currentInfoBean) {
        this.currentInfoBean = currentInfoBean;
    }

    public void clearCurrentInfoBean() {
        currentInfoBean = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void doLike(int post_id) {
        int value = upLikeValue == 1 ? 0 : 1;
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService service = retrofit.create(UserinfoService.class);
        service.post_like(post_id, value)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEntity>() {
                               @Override
                               public void onCompleted() {
                                   if (BuildConfig.DEBUG)
                                       Log.d("HomePageRecycleAdapter", "Completed");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   if (BuildConfig.DEBUG)
                                       Log.d("HomePageRecycleAdapter", "Error");
                               }

                               @Override
                               public void onNext(RegisterEntity registerEntity) {
                                   if (registerEntity.getStatus() == 1) {
                                       Message message = handler.obtainMessage();
                                       message.what = upLikeMsg;
                                       message.sendToTarget();
                                   }
                               }
                           }
                );
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(tvInput, emojicon);
        Log.i("emoj", emojicon.getEmoji());
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(tvInput);
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive() && activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
    }

    /**
     * 显示软键盘
     */
    public static void showKeyboard(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(activity.getCurrentFocus()
                    .getWindowToken(), 0);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isemoj)
            block_keyboardview.setVisibility(View.GONE);
    }


}
