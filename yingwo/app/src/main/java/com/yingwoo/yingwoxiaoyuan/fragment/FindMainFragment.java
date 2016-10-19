package com.yingwoo.yingwoxiaoyuan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwoo.yingwoxiaoyuan.Adapter.FragmentAdapter;
import com.yingwoo.yingwoxiaoyuan.HomePageActivity;
import com.yingwoo.yingwoxiaoyuan.PersonCenterActivity;
import com.yingwoo.yingwoxiaoyuan.RefreshThingActivity;
import com.yingwoo.yingwoxiaoyuan.TopicActivity;
import com.yingwoo.yingwoxiaoyuan.model.FieldModel;
import com.yingwoo.yingwoxiaoyuan.model.HotlistModel;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;
import com.yingwoo.yingwoxiaoyuan.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FindMainFragment extends Fragment implements BGABanner.OnItemClickListener, BGABanner.Adapter {
    @BindView(R.id.action_find)
    ImageView actionFind;
    private ViewPager vPager;
    private List<Fragment> mFragmentList;
    private List<String> mTitles;
    private View rootView;
    UserinfoService userinfoService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userinfoService = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        rootView = inflater.inflate(R.layout.fragment_find, null);
        ButterKnife.bind(this, rootView);
        initToolbar(rootView);
        init();
        return rootView;
    }

    public void init() {
        mFragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        actionFind.setImageResource(R.mipmap.find_g);
        vPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        vPager.setOffscreenPageLimit(999);
        vPager.setCurrentItem(0);
        initViewPager();
    }

    @OnClick(R.id.layout_action_find)
    public void refresh(){
        Log.i("什么都不干","什么都不干，就是不想被穿透");
    }

    @OnClick(R.id.layout_action_home)
    public void tofind() {
        startActivity(new Intent(getContext(), HomePageActivity.class));
    }

    @OnClick(R.id.layout_action_bub)
    public void toPost() {
        Toast.makeText(getActivity(),"正在开发中~~",Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.layout_action_head)
    public void toHome() {
        startActivity(new Intent(getContext(), PersonCenterActivity.class));
    }

    @OnClick(R.id.action_add)
    public void toRefreshthing() {
        Intent addPost = new Intent(getContext(), RefreshThingActivity.class);
        startActivity(addPost);
    }

    private void initViewPager() {
        userinfoService.getFieldList()
                .map(new Func1<FieldModel, List<FieldModel.InfoBean>>() {

                    @Override
                    public List<FieldModel.InfoBean> call(FieldModel fieldModel) {
                        return fieldModel.getInfo();
                    }
                }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FieldModel.InfoBean>>() {
                    @Override
                    public void onCompleted() {
                        FragmentAdapter pagerAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
                        for (int i = 0; i < mFragmentList.size(); i++) {
                            pagerAdapter.addFragment(mFragmentList.get(i), mTitles.get(i));
                        }

                        vPager.setAdapter(pagerAdapter);
                        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
                        tabLayout.setupWithViewPager(vPager);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<FieldModel.InfoBean> infoBeen) {

                        for (FieldModel.InfoBean bean : infoBeen) {
                            mTitles.add(bean.getTitle());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("fieldbean", bean);
                            Fragment exchangeFragment = FindFragment.newInstance(
                                    getActivity(), bundle);
                            mFragmentList.add(exchangeFragment);
                        }
                    }
                });

    }

    public void initToolbar(View rootView) {
        final BGABanner mAccordionBanner = (BGABanner) rootView.findViewById(R.id.banner_main_accordion);
        userinfoService.gethotlist()
                .map(new Func1<HotlistModel, List<HotlistModel.InfoBean>>() {
                    @Override
                    public List<HotlistModel.InfoBean> call(HotlistModel hotlistModel) {
                        return hotlistModel.getInfo();
                    }
                }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<HotlistModel.InfoBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<HotlistModel.InfoBean> infoBeen) {
                        List<View> pics = new ArrayList<View>();
                        for (HotlistModel.InfoBean infobean : infoBeen) {
                            SimpleDraweeView simple = new SimpleDraweeView(getActivity());
                            simple.setImageURI(infobean.getBig_img());
                            pics.add(simple);
                        }
                        mAccordionBanner.setData(pics, infoBeen, null);
                        mAccordionBanner.setOnItemClickListener(FindMainFragment.this);
                    }
                });


    }

    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {

    }

    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
//        Toast.makeText(getActivity(), ((HotlistModel.InfoBean) model).getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), TopicActivity.class);

        intent.putExtra("topic_id", ((HotlistModel.InfoBean) model).getId());
        intent.putExtra("topic_name", ((HotlistModel.InfoBean) model).getTitle());
        startActivity(intent);
    }
}
