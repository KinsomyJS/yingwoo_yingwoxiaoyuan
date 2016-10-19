package com.yingwoo.yingwoxiaoyuan.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yingwoo.yingwoxiaoyuan.Adapter.FragmentAdapter;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.model.FieldModel;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MyTopicMainFragment extends Fragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ViewPager vPager;
    private List<Fragment> mFragmentList;
    private List<String> mTitles;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mytopic, null);
        init();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return rootView;
    }

    public void init() {
        ButterKnife.bind(this, rootView);
        mFragmentList = new ArrayList<>();
        mTitles = new ArrayList<>();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        vPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        vPager.setOffscreenPageLimit(999);
        vPager.setCurrentItem(0);
        initViewPager();
    }

    private void initViewPager() {
        UserinfoService userinfoService;
        userinfoService = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
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
                        for (FieldModel.InfoBean fieldBean : infoBeen) {
                            mTitles.add(fieldBean.getTitle());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("fieldbean", fieldBean);
                            Fragment exchangeFragment = MyTopicFragment.newInstance(
                                    getActivity(), bundle);
                            mFragmentList.add(exchangeFragment);
                        }
                    }
                });

    }



}
