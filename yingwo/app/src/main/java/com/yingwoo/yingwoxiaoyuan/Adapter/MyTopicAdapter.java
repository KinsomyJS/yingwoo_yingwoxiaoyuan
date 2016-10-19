package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwoo.yingwoxiaoyuan.MyApplication;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.TopicActivity;
import com.yingwoo.yingwoxiaoyuan.model.MyTopicEntity;
import com.yingwoo.yingwoxiaoyuan.model.RegisterEntity;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangyu on 9/20/16.
 */

public class MyTopicAdapter extends RecyclerView.Adapter<MyTopicAdapter.RecyclerItemViewHolder> {

    private List<MyTopicEntity.InfoBean> mItemList;
    private AppCompatActivity activity;

    public MyTopicAdapter(AppCompatActivity activity) {
        super();
        mItemList = new ArrayList<>();
        this.activity = activity;
    }

    public void addItems(List<MyTopicEntity.InfoBean> list) {
        mItemList.addAll(list);
    }


    @Override
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(activity)
                .inflate(R.layout.item_recyclerview, parent, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder viewHolder, final int position) {
        if (mItemList.size() > 0) {

            viewHolder.tvTitle.setText(mItemList.get(position).getTopic_title());
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(20f);
            roundingParams.setRoundAsCircle(false);
            viewHolder.ivIcon.getHierarchy().setRoundingParams(roundingParams);
            viewHolder.ivIcon.setImageURI(mItemList.get(position).getTopic_img());
            viewHolder.tvGuanzhu.setText(mItemList.get(position).getTopic_like_cnt());
            viewHolder.tvTiezi.setText(mItemList.get(position).getTopic_post_cnt());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplication.getGlobalContext(), TopicActivity.class);
                    intent.putExtra("topic_id", mItemList.get(position).getTopic_id());
                    intent.putExtra("topic_name", mItemList.get(position).getTopic_title());
                    activity.startActivity(intent);
                }
            });
            viewHolder.ibStatus.setBackgroundResource(R.mipmap.weiguanzhu);
            viewHolder.ibStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.ibStatus.setEnabled(false);
                    int topic_id = Integer.parseInt(mItemList.get(position).getId());
                    confirmDialog(viewHolder, topic_id);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private SimpleDraweeView ivIcon;
        private TextView tvGuanzhu;
        private TextView tvTiezi;
        private ImageButton ibStatus;
        private int defalutValue;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            defalutValue = 1;
            tvTitle = (TextView) itemView.findViewById(R.id.topic_title);
            ivIcon = (SimpleDraweeView) itemView.findViewById(R.id.topic_icon);
            tvGuanzhu = (TextView) itemView.findViewById(R.id.topic_guanzhu_num);
            tvTiezi = (TextView) itemView.findViewById(R.id.topic_tiezi_num);
            ibStatus = (ImageButton) itemView.findViewById(R.id.ib_guanzhu);
        }
    }

    public void confirmDialog(final RecyclerItemViewHolder viewHolder, final int topic_id) {
        int value = viewHolder.defalutValue == 1 ? 0 : 1;
        if (value == 0) {
            new AlertDialog.Builder(activity).setTitle("贴子操作")
                    .setMessage("确定取消关注？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doLike(viewHolder, topic_id);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        } else {
            doLike(viewHolder, topic_id);
        }
    }


    public void doLike(final RecyclerItemViewHolder viewHolder, int topic_id) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService service = retrofit.create(UserinfoService.class);
        int value = viewHolder.defalutValue == 1 ? 0 : 1;
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
                                       message.obj = viewHolder;
                                       message.sendToTarget();
                                   }else {
                                       viewHolder.ibStatus.setEnabled(true);
                                   }
                               }
                           }
                );
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) msg.obj;
            if (viewHolder.defalutValue == 1) {
                viewHolder.ibStatus.setBackgroundResource(R.mipmap.tianjia);
                viewHolder.defalutValue = 0;
            } else {
                viewHolder.ibStatus.setBackgroundResource(R.mipmap.weiguanzhu);
                viewHolder.defalutValue = 1;
            }
            viewHolder.ibStatus.setEnabled(true);

        }
    };

}
