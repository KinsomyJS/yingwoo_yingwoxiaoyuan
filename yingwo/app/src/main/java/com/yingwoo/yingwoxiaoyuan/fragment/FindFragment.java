package com.yingwoo.yingwoxiaoyuan.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.SubjectHomeActivity;
import com.yingwoo.yingwoxiaoyuan.TopicActivity;
import com.yingwoo.yingwoxiaoyuan.View.XRecyclerView;
import com.yingwoo.yingwoxiaoyuan.model.FieldModel;
import com.yingwoo.yingwoxiaoyuan.model.SubjectModdel;
import com.yingwoo.yingwoxiaoyuan.model.TopicListModel;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.ScrollUtil;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by FJS0420 on 2016/9/20.
 */

public class FindFragment extends android.support.v4.app.Fragment {

    RelativeLayout tabview;
    private RecyclerAdapter adapter;
    private UserinfoService userinfoService;
    private static final int ParentMsg = 814;
    private static final int ChildMsg = 824;
    private static final int childDatSet = 200;
    private boolean isFirst = true;
    private List<SubjectModdel.InfoBean> ParentSetData = new ArrayList<>();
    private List<SubjectModdel.InfoBean> ParentData = new ArrayList<>();
    private List<List<TopicListModel.InfoBean>> ChildDataList = new ArrayList<>();
    private List<SubjectModdel.InfoBean> mItemList = new ArrayList<>();
    private List<List<TopicListModel.InfoBean>> ChildDataSetList = new ArrayList<>();
    private int doneFlag;

    public static FindFragment newInstance(Context context, Bundle bundle) {
        FindFragment newFragment = new FindFragment();
        newFragment.setArguments(bundle);
        return newFragment;

    }

    private XRecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView =
                (XRecyclerView) inflater.inflate(R.layout.fragment_activity_new, container, false);
        tabview = (RelativeLayout) getActivity().findViewById(R.id.tabview);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doneFlag = 0;
                getParentInfo();
            }

            @Override
            public void onLoadMore() {

            }
        });
        mRecyclerView.setOnScrollListener(new ScrollUtil.inVisibleScorllListener() {
            @Override
            public void onHide() {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tabview.getLayoutParams();
                int fabBottomMargin = lp.bottomMargin;
                tabview.animate().translationY(tabview.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
            }

            @Override
            public void onShow() {
                showtabview();
            }
        });
        userinfoService = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        createItemList();
    }


    public void createItemList() {
        doneFlag = 0;
        getParentInfo();
    }


    //显示tabview
    private void showtabview() {
        tabview.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public class RecyclerAdapter extends RecyclerView.Adapter {
        public RecyclerAdapter() {
            super();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_recycle_subject, parent, false);
            return new RecyclerItemViewHolder(view);
        }

        public void setItems(List<SubjectModdel.InfoBean> list) {
            mItemList = new ArrayList<>(list);
        }

        public void setChildItems(List<List<TopicListModel.InfoBean>> list) {
            ChildDataSetList = new ArrayList<>(list);
        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
            if (mItemList.size() > 0) {
                if (viewHolder instanceof RecyclerItemViewHolder) {
                    if (mItemList.get(position).getImg().contains("http:")) {
                        ((RecyclerItemViewHolder) viewHolder).iv_subject.setImageURI(mItemList.get(position).getImg());
                    }
                    ((RecyclerItemViewHolder) viewHolder).tv_subjectname.setText(mItemList.get(position).getTitle());
                    List<TopicListModel.InfoBean> ChildData = ChildDataSetList.get(position);
                    Log.d("RecyclerAdapter2222", String.valueOf(mItemList.get(position).getImg()));
                    TopicRecycleAdapter topicRecycleAdapter = new TopicRecycleAdapter(ChildData);
                    LinearLayoutManager mLayoutMgr = new LinearLayoutManager(getActivity());
                    ((RecyclerItemViewHolder) viewHolder).recycle_topic.setLayoutManager(mLayoutMgr);
                    ((RecyclerItemViewHolder) viewHolder).recycle_topic.setAdapter(topicRecycleAdapter);
                    ((RecyclerItemViewHolder) viewHolder).subject_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), SubjectHomeActivity.class);
                            intent.putExtra("Subject", mItemList.get(position));
                            Log.d("subjectid", mItemList.get(position).getTitle());
                            startActivity(intent);
                        }
                    });
                }
            }
        }


        @Override
        public int getItemCount() {
            return mItemList.size();
        }


        private class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_subjectname;
            private SimpleDraweeView iv_subject;
            private RecyclerView recycle_topic;
            private RelativeLayout subject_layout;


            public RecyclerItemViewHolder(View itemView) {
                super(itemView);
                subject_layout = (RelativeLayout) itemView.findViewById(R.id.layout_subject);
                tv_subjectname = (TextView) itemView.findViewById(R.id.tv_subjectname);
                iv_subject = (SimpleDraweeView) itemView.findViewById(R.id.iv_subject);
                recycle_topic = (RecyclerView) itemView.findViewById(R.id.recycle_topic);
            }
        }

    }


    public class TopicRecycleAdapter extends RecyclerView.Adapter {

        private List<TopicListModel.InfoBean> infoBean;

        public TopicRecycleAdapter(List<TopicListModel.InfoBean> infoBean) {
            this.infoBean = infoBean;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_topic, parent, false);
            return new TopicRecyclerItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (infoBean.size() > 0) {
                if (infoBean.get(position).getImg().contains("http:")) {
                    RoundingParams roundingParams = RoundingParams.fromCornersRadius(20f);
                    roundingParams.setRoundAsCircle(false);
                    ((TopicRecyclerItemViewHolder) holder).iv_topic.getHierarchy().setRoundingParams(roundingParams);
                    ((TopicRecyclerItemViewHolder) holder).iv_topic.setImageURI(infoBean.get(position).getImg());
                }
                ((TopicRecyclerItemViewHolder) holder).tv_topicname.setText(infoBean.get(position).getTitle());
                ((TopicRecyclerItemViewHolder) holder).tv_postnum.setText(infoBean.get(position).getPost_cnt() + "贴子  ");
                ((TopicRecyclerItemViewHolder) holder).tv_atttentionsnum.setText(infoBean.get(position).getLike_cnt() + "关注");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), TopicActivity.class);
                        intent.putExtra("topic_id", infoBean.get(position).getId());
                        intent.putExtra("topic_name", infoBean.get(position).getTitle());
                        getContext().startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return infoBean.size();
        }

        private class TopicRecyclerItemViewHolder extends RecyclerView.ViewHolder {

            private final TextView tv_topicname;
            private final SimpleDraweeView iv_topic;
            private final TextView tv_postnum;
            private final TextView tv_atttentionsnum;

            public TopicRecyclerItemViewHolder(View itemView) {
                super(itemView);
                tv_topicname = (TextView) itemView.findViewById(R.id.tv_topicname);
                iv_topic = (SimpleDraweeView) itemView.findViewById(R.id.iv_topic);
                tv_postnum = (TextView) itemView.findViewById(R.id.tv_postnum);
                tv_atttentionsnum = (TextView) itemView.findViewById(R.id.tv_attentionssnum);
            }
        }
    }


    public void getParentInfo() {
        FieldModel.InfoBean fieldbean = (FieldModel.InfoBean) getArguments().get("fieldbean");
        userinfoService.getSubjectList(Integer.parseInt(fieldbean.getId()))
                .map(new Func1<SubjectModdel, List<SubjectModdel.InfoBean>>() {
                    @Override
                    public List<SubjectModdel.InfoBean> call(SubjectModdel subjectModdel) {
                        return subjectModdel.getInfo();
                    }
                }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SubjectModdel.InfoBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<SubjectModdel.InfoBean> infoBeen) {
                        Message msg = handler.obtainMessage();
                        ParentData.clear();
                        ParentData = infoBeen;
                        msg.what = ParentMsg;
                        msg.sendToTarget();
                    }
                });
    }

    public void getChildList(int subject_id) {
        userinfoService.getTopicList(subject_id, 1)
                .map(new Func1<TopicListModel, List<TopicListModel.InfoBean>>() {
                    @Override
                    public List<TopicListModel.InfoBean> call(TopicListModel topicListModel) {
                        return topicListModel.getInfo();
                    }
                }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TopicListModel.InfoBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TopicListModel.InfoBean> infoBeen) {
                        Message message = handler.obtainMessage();
                        message.obj = infoBeen;
                        message.what = childDatSet;
                        message.sendToTarget();
                        if (doneFlag == ParentData.size() - 1) {
                            Message msg = handler.obtainMessage();
                            msg.what = ChildMsg;
                            msg.sendToTarget();
                        }
                    }
                });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ParentMsg:
                    if (ParentData.size() > 0)
                        getChildList(Integer.parseInt(ParentData.get(doneFlag).getId()));
                    break;
                case ChildMsg:
                    if (isFirst) {
                        adapter = new RecyclerAdapter();
                        adapter.setItems(ParentSetData);
                        adapter.setChildItems(ChildDataList);
                        mRecyclerView.setLoadingMoreEnabled(false);
                        mRecyclerView.setAdapter(adapter);
                        isFirst = false;
                    } else {
                        adapter.setItems(ParentSetData);
                        adapter.setChildItems(ChildDataList);
                        adapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }
                    break;
                case childDatSet:
                    List<TopicListModel.InfoBean> infoBeen = (List<TopicListModel.InfoBean>) msg.obj;
                    if (doneFlag == 0) {
                        ParentSetData.clear();
                        ChildDataList.clear();
                    }
                    if (infoBeen.size() > 0) {
                        ParentSetData.add(ParentData.get(doneFlag));
                        ChildDataList.add(infoBeen);
                    }
                    doneFlag++;
                    if (doneFlag < ParentData.size()) {
                        getChildList(Integer.parseInt(ParentData.get(doneFlag).getId()));
                    }
                    break;
            }
        }
    };

}
