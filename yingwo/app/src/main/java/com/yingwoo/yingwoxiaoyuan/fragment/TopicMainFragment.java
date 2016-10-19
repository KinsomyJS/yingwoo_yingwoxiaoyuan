package com.yingwoo.yingwoxiaoyuan.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.melnykov.fab.FloatingActionButton;
import com.yingwoo.yingwoxiaoyuan.Adapter.FragmentAdapter;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.RefreshThingActivity;
import com.yingwoo.yingwoxiaoyuan.model.RegisterEntity;
import com.yingwoo.yingwoxiaoyuan.model.TopicBaseInfo;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TopicMainFragment extends Fragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private TextView topicTitle;
    private TextView tieziNum;
    private TextView guanzhuNum;
    private ImageButton ibAdd;
    private LinearLayout Topic_Layout;
    private SimpleDraweeView face_img;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    boolean isFirst;
    private static final int TOPICIMG = 215;
    private ViewPager vPager;
    private List<Fragment> mFragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;
    private View rootView;
    private FloatingActionButton fab;
    private int defalutValue;
    private static final int doLike = 414;
    private int toolbarHeight = -1;
    private int TitleHeight = -1;
    private String topic_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_topic, null);
        setHasOptionsMenu(true);
        init();
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void initTitleView(final TopicBaseInfo.InfoBean Topic_bean) {
        Topic_Layout = (LinearLayout) rootView.findViewById(R.id.topic_info_layout);
        TitleHeight = Topic_Layout.getHeight();
        topicTitle = (TextView) rootView.findViewById(R.id.topic_title);
        tieziNum = (TextView) rootView.findViewById(R.id.tiezi_num);
        guanzhuNum = (TextView) rootView.findViewById(R.id.guanzhu_num);
        ibAdd = (ImageButton) rootView.findViewById(R.id.ib_add);
        face_img = (SimpleDraweeView) rootView.findViewById(R.id.face_img);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(20f);
        roundingParams.setRoundAsCircle(false);
        face_img.getHierarchy().setRoundingParams(roundingParams);
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        face_img.setImageURI(Topic_bean.getImg());
        tieziNum.setText(Topic_bean.getPost_cnt());
        guanzhuNum.setText(Topic_bean.getLike_cnt());
        topicTitle.setText(Topic_bean.getTitle());
//        tvTitle.setText(Topic_bean.getTitle());
        topic_title = Topic_bean.getTitle();
        defalutValue = Topic_bean.getLike();
        ibAdd.setBackgroundResource(defalutValue == 1 ? R.mipmap.weiguanzhu : R.mipmap.tianjia);
        final int like_id = Integer.parseInt(Topic_bean.getId());
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like_id != -99) {
                    ibAdd.setEnabled(false);
                    confirmDialog(like_id);
                } else {
                    Toast.makeText(getActivity(), "此话题默认关注", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        final int topic_id = (int) getArguments().get("topic_id");
        final String topic_name = (String) getArguments().get("topic_name");
        Log.d("topic", topic_id + topic_name);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), RefreshThingActivity.class);
                intent.putExtra("topicid", topic_id);
                intent.putExtra("topicname", topic_name);
                startActivity(intent);
            }
        });
        final String blur_backGroundImg = Topic_bean.getImg() + "?imageMogr2/blur/25x4";
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("TopicActivity", blur_backGroundImg);
                loadImageFromNetwork(blur_backGroundImg);
            }
        }).start();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_topichome, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {

        }
        return super.onOptionsItemSelected(item);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TOPICIMG) {
                Drawable background_img = (Drawable) msg.obj;
                if (background_img != null)
                    collapsingToolbarLayout.setBackgroundDrawable(background_img);
            } else if (msg.what == doLike) {
                if (defalutValue == 1) {
                    ibAdd.setBackgroundResource(R.mipmap.tianjia);
                    defalutValue = 0;
                } else {
                    ibAdd.setBackgroundResource(R.mipmap.weiguanzhu);
                    defalutValue = 1;
                }
                ibAdd.setEnabled(true);
            }
        }
    };

    public void loadImageFromNetwork(String imageUrl) {
        Drawable drawable = null;
        try {
            // 可以在这里通过第二个参数(文件名)来判断，是否本地有此图片
            drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), imageUrl);
            Message message = handler.obtainMessage();
            message.what = TOPICIMG;
            message.obj = drawable;
            message.sendToTarget();
        } catch (IOException e) {
            Log.d("skythinking", e.getMessage());
        }
        if (drawable == null) {
            Log.d("skythinking", "null drawable");
        } else {
            Log.d("skythinking", "not null drawable");
        }

    }


    public static TopicMainFragment newInstance(Context context, Bundle bundle) {
        TopicMainFragment newFragment = new TopicMainFragment();
        newFragment.setArguments(bundle);
        return newFragment;
    }


    public void confirmDialog(final int topic_id) {
        int value = defalutValue == 1 ? 0 : 1;
        if (value == 0) {
            new AlertDialog.Builder(getContext()).setTitle("贴子操作")
                    .setMessage("确定取消关注？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doLike(topic_id);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        } else {
            doLike(topic_id);
        }
    }

    public void doLike(int topic_id) {
        int value = defalutValue == 1 ? 0 : 1;
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService service = retrofit.create(UserinfoService.class);
        service.TopicLike(topic_id, value)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEntity>() {
                               @Override
                               public void onCompleted() {
                                   Log.d("MyTopicAdapter", "Completed");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.d("MyTopicAdapter", "Error");
                               }

                               @Override
                               public void onNext(RegisterEntity registerEntity) {
                                   if (registerEntity.getStatus() == 1) {
                                       Message message = handler.obtainMessage();
                                       message.what = doLike;
                                       message.sendToTarget();
                                   } else {
                                       ibAdd.setEnabled(true);
                                   }
                               }
                           }
                );
    }

    public void init() {
        defalutValue = 0;
        int topic_id = (int) getArguments().get("topic_id");
        if (topic_id != 0) {
            getTopicDetail();
        } else {
            TopicBaseInfo.InfoBean infoBean = new TopicBaseInfo.InfoBean();
            infoBean.setImg("http://image.zhibaizhi.com/icon/topic_default.png");
            infoBean.setPost_cnt("0");
            infoBean.setLike_cnt("0");
            infoBean.setTitle("新鲜事");
            infoBean.setLike(1);
            infoBean.setId("-99");
            initTitleView(infoBean);
        }

        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isFirst = true;
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        toolbarHeight = toolbar.getHeight();
        mFragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        vPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        vPager.setOffscreenPageLimit(999);
        vPager.setCurrentItem(0);
        initViewPager();
    }


    private void initViewPager() {
        int topic_id = (int) getArguments().get("topic_id");
        mTitles.add("最新");
        mTitles.add("最热");
        Bundle bundle = new Bundle();
        bundle.putInt("topic_id", topic_id);
        bundle.putBoolean("isHot", false);
        Bundle bundle1 = new Bundle();
        bundle1.putInt("topic_id", topic_id);
        bundle1.putBoolean("isHot", true);
        TopicFragment exchangeFragment = TopicFragment.newInstance(
                this, bundle);
        TopicFragment exchangeFragment1 = TopicFragment.newInstance(
                this, bundle1);
        mFragmentList.add(exchangeFragment);
        mFragmentList.add(exchangeFragment1);
        FragmentAdapter pagerAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
        for (int i = 0; i < mFragmentList.size(); i++) {
            pagerAdapter.addFragment(mFragmentList.get(i), mTitles.get(i));
        }
        vPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vPager);
    }

    public boolean isLoading() {
        int height = 0;
        if (Topic_Layout != null) {
            height = Topic_Layout.getHeight();
        }
        if (vPager.getTop() > height)
            return true;
        return false;
    }

    public boolean betweenTitleView() {
        if (TitleHeight != -1 && toolbarHeight != -1) {
            toolbarHeight = toolbar.getHeight();
            Log.d("TopicMainFraasat", "toolbar" + toolbarHeight * 5 / 2 + " title" + (TitleHeight) + "adsassa" + vPager.getTop());
            if (vPager.getTop() >= toolbarHeight * 5 / 2) {
                return true;
            }
        }
        return false;
    }

    public void showTitle() {
        if (Topic_Layout != null) {
            if (topic_title != null)
                tvTitle.setText(topic_title);
        }
    }

    public void hideTitle() {
        if (Topic_Layout != null) {
            tvTitle.setText("");
        }
    }


    public void getTopicDetail() {
        int topic_id = (int) getArguments().get("topic_id");
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService service = retrofit.create(UserinfoService.class);
        service.getTopicDetail(topic_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TopicBaseInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TopicActivity", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TopicActivity", "Error");
                    }

                    @Override
                    public void onNext(final TopicBaseInfo topicBaseInfo) {
                        if (topicBaseInfo.getStatus() == 1) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initTitleView(topicBaseInfo.getInfo());
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
