package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwoo.yingwoxiaoyuan.MyApplication;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.TopicActivity;
import com.yingwoo.yingwoxiaoyuan.model.RegisterEntity;
import com.yingwoo.yingwoxiaoyuan.model.TopicListModel;
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

public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.RecyclerItemViewHolder> {

    private List<TopicListModel.InfoBean> mItemList;
    private Context context;

    public TopicListAdapter(Context context) {
        super();
        mItemList = new ArrayList<>();
        this.context = context;
    }

    public void setItems(List<TopicListModel.InfoBean> list) {
        mItemList = new ArrayList<>(list);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context)
                .inflate(R.layout.item_recyclerview, parent, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerItemViewHolder viewHolder, final int position) {
        if (mItemList.size() > 0) {

            viewHolder.tvTitle.setText(mItemList.get(position).getTitle());
            viewHolder.ivIcon.setImageURI(mItemList.get(position).getImg());
            viewHolder.tvGuanzhu.setText(mItemList.get(position).getLike_cnt());
            viewHolder.tvTiezi.setText(mItemList.get(position).getPost_cnt());
            Log.d("topiclistadapter", mItemList.size() + "");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplication.getGlobalContext(), TopicActivity.class);
                    intent.putExtra("topic_id", mItemList.get(position).getId());
                    intent.putExtra("topic_name", mItemList.get(position).getTitle());
                    context.startActivity(intent);
                }
            });
            viewHolder.defalutValue = Integer.parseInt(mItemList.get(position).getUser_topic_like());
            viewHolder.ibStatus.setBackgroundResource(viewHolder.defalutValue == 1 ? R.mipmap.weiguanzhu : R.mipmap.tianjia);
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
            defalutValue = 0;
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
            new AlertDialog.Builder(context).setTitle("贴子操作")
                    .setMessage("确定取消关注？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doLike(viewHolder,topic_id);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        } else {
            doLike(viewHolder,topic_id);
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
