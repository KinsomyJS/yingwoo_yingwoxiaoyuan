package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwoo.yingwoxiaoyuan.PhotoViewActivity;
import com.yingwoo.yingwoxiaoyuan.PostActivity;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.fragment.TopicFragment;
import com.yingwoo.yingwoxiaoyuan.model.PostModel;
import com.yingwoo.yingwoxiaoyuan.model.RegisterEntity;
import com.yingwoo.yingwoxiaoyuan.util.weibogridview.view.FeedGridView;
import com.yingwoo.yingwoxiaoyuan.utils.HttpControl;
import com.yingwoo.yingwoxiaoyuan.utils.ImageUtil;
import com.yingwoo.yingwoxiaoyuan.utils.TImeUtiil;
import com.yingwoo.yingwoxiaoyuan.utils.UrlUtils;
import com.yingwoo.yingwoxiaoyuan.utils.UserInsertHelper;
import com.yingwoo.yingwoxiaoyuan.utils.UserinfoService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by FJS0420 on 2016/9/2.
 */

public class TopicRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<PostModel.InfoBean> topicModelList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private int HOME_ITEM = 1;
    private int position;
    private TopicFragment topicFragment;
    private static final int LikeFlage = 924;
    private static final int deleteFlag = 97;
    WindowManager wm;

    int screenwidth = 1080;
    int screenheight = 1920;

    public TopicRecycleAdapter(Context context, TopicFragment topicFragment) {
        this.context = context;
        this.topicFragment = topicFragment;
        layoutInflater = LayoutInflater.from(context);
        wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        screenwidth = wm.getDefaultDisplay().getWidth() - 60;
        screenheight = wm.getDefaultDisplay().getHeight();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_refreshthing_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            final PostModel.InfoBean topicModel = topicModelList.get(position);
            holder.itemView.setOnClickListener(this);
            ((TopicRecycleAdapter.MyViewHolder) holder).tv_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PostActivity.class);
                    intent.putExtra("top_id", topicModel.getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            if (topicModel.getImg().contains("http")) {
                String[] urls = topicModel.getImg().split(",");
                if (urls.length == 1) {
                    ((MyViewHolder) holder).gridView.setVisibility(View.GONE);
                    Object[] imgInfo = ImageUtil.downloadImageFormat(urls[0]);
                    float picheight = (float) imgInfo[2];
                    float picwidth = (float) imgInfo[1];
                    String img_url = imgInfo[0] + "?imageMogr2/auto-orient/thumbnail/!50p";
                    final List<String> bigimgurls = new ArrayList<>();
                    bigimgurls.add(imgInfo[0] + "?imageMogr2/auto-orient/thumbnail/!90p");
                    ViewGroup.LayoutParams params = ((MyViewHolder) holder).singleImg.getLayoutParams();
                    params.width = screenwidth;
                    params.height = screenwidth;
                    ((MyViewHolder) holder).singleImg.setLayoutParams(params);
                    ((MyViewHolder) holder).singleImg.setVisibility(View.VISIBLE);
                   /* ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(img_url))
                            .setAutoRotateEnabled(true)
                            .build();
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .build();*/
                    ((MyViewHolder) holder).singleImg.setImageURI(Uri.parse(img_url));
                    ((MyViewHolder) holder).singleImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent();
                            intent.setClass(context, PhotoViewActivity.class);
                            intent.putExtra("page", position);
                            intent.putExtra("photo_list", (Serializable) bigimgurls);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    ((TopicRecycleAdapter.MyViewHolder) holder).singleImg.setVisibility(View.GONE);
                    List<String> imgurls = new ArrayList<>();
                    final List<String> bigimgurls = new ArrayList<>();
                    for (int i = 0; i < urls.length; i++) {
                        urls[i] = (String) ImageUtil.downloadImageFormat(urls[i])[0];
                        bigimgurls.add(urls[i] + "?imageMogr2/auto-orient/thumbnail/!60p");
                        if (i <= 8) {
                            imgurls.add(urls[i] + "?imageMogr2/auto-orient/thumbnail/!40p");
                        }
                    }
                    ((TopicRecycleAdapter.MyViewHolder) holder).gridView.setVisibility(View.VISIBLE);
                    ((TopicRecycleAdapter.MyViewHolder) holder).gridView.setPhotoAdapter(topicFragment.getActivity(),imgurls);

                }
            } else if (!topicModel.getImg().contains("http")) {
                ((MyViewHolder) holder).gridView.setVisibility(View.GONE);
                ((MyViewHolder) holder).singleImg.setVisibility(View.GONE);
            }
            if (!topicModel.getContent().isEmpty()) {
                ((MyViewHolder) holder).tv_content.setVisibility(View.VISIBLE);
                if (topicModel.getContent().contains("href")) {
                    ((MyViewHolder) holder).tv_content = UrlUtils.handleHtmlText(((MyViewHolder) holder).tv_content,
                            topicModel.getContent());
                } else {
                    ((MyViewHolder) holder).tv_content = UrlUtils.handleText(((MyViewHolder) holder).tv_content, topicModel
                            .getContent());
                }
            } else if (topicModel.getContent().isEmpty())
                ((MyViewHolder) holder).tv_content.setVisibility(View.GONE);

            String userheadurl = topicModel.getUser_face_img();
            if (userheadurl != null) {
                Uri imgUri = Uri.parse(userheadurl);
                ((MyViewHolder) holder).iv_userhead.setImageURI(imgUri);
            }
            ((MyViewHolder) holder).defalutValue = Integer.parseInt(topicModel.getUser_post_like());
            ((MyViewHolder) holder).btn_like.setBackgroundResource(((MyViewHolder) holder).defalutValue == 1 ? R.mipmap.heart_red : R.mipmap.heart_gray);
            ((MyViewHolder) holder).tv_commentnum.setText(topicModel.getReply_cnt());
            ((MyViewHolder) holder).tv_likenum.setText(topicModel.getLike_cnt());
            ((MyViewHolder) holder).tv_releasetime.setText(TImeUtiil.getStandardDate(topicModel.getCreate_time()));
            ((MyViewHolder) holder).tv_userid.setText(topicModel.getUser_name());

            String title = topicModel.getTopic_title() == null ? "新鲜事" : topicModel.getTopic_title();
            ((MyViewHolder) holder).topic_title.setText(title);
            ((MyViewHolder) holder).btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MyViewHolder) holder).btn_like.setEnabled(false);
                    int post_id = Integer.parseInt(topicModel.getId());
                    doLike((MyViewHolder) holder, post_id);
                }
            });
            ((MyViewHolder) holder).ibMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPosition(position);
                    topicFragment.showPop();
                }
            });
            holder.itemView.setTag(topicModelList.get(position));
        }
    }


    @Override
    public int getItemCount() {
        return topicModelList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (PostModel.InfoBean) v.getTag());
        }
    }

    public void addData(List<PostModel.InfoBean> data) {
        topicModelList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return HOME_ITEM;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_content;
        FeedGridView gridView;
        SimpleDraweeView iv_userhead;
        TextView tv_userid;
        TextView tv_releasetime;
        TextView tv_likenum;
        TextView tv_commentnum;
        TextView topic_title;
        SimpleDraweeView singleImg;
        ImageView btn_like;
        int defalutValue;
        ImageButton ibMore;

        public MyViewHolder(View view) {
            super(view);
            defalutValue = 0;
            ibMore = (ImageButton) itemView.findViewById(R.id.iv_morefunc);
            btn_like = (ImageView) itemView.findViewById(R.id.btn_like);
            singleImg = (SimpleDraweeView) itemView.findViewById(R.id.single_image);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            gridView = (FeedGridView) view.findViewById(R.id.gridview);
            iv_userhead = (SimpleDraweeView) view.findViewById(R.id.iv_userhead);
            tv_userid = (TextView) view.findViewById(R.id.tv_userId);
            tv_releasetime = (TextView) view.findViewById(R.id.tv_releasetime);
            tv_likenum = (TextView) view.findViewById(R.id.tv_likenum);
            tv_commentnum = (TextView) view.findViewById(R.id.tv_commentnum);
            topic_title = (TextView) view.findViewById(R.id.topic_title);
        }

    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, PostModel.InfoBean data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void clearData() {
        topicModelList.clear();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LikeFlage) {
                MyViewHolder viewHolder = (MyViewHolder) msg.obj;
                int like_num = Integer.parseInt(viewHolder.tv_likenum.getText().toString());
                if (viewHolder.defalutValue == 0) {
                    viewHolder.btn_like.setBackgroundResource(R.mipmap.heart_red);
                    viewHolder.tv_likenum.setText((like_num + 1) + "");
                    viewHolder.defalutValue = 1;
                } else {
                    viewHolder.btn_like.setBackgroundResource(R.mipmap.heart_gray);
                    viewHolder.tv_likenum.setText((like_num - 1) + "");
                    viewHolder.defalutValue = 0;
                }
                viewHolder.btn_like.setEnabled(true);
            } else if (msg.what == deleteFlag) {
                topicFragment.cancelConfirmPop();
                topicModelList.remove(getPosition());
                notifyDataSetChanged();
            }
        }
    };


    private void setPosition(int position) {
        this.position = position;
    }

    private int getPosition() {
        return position;
    }

    public View.OnClickListener Pop_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_btn:
                    topicFragment.cancelPop();
                    topicFragment.showConfirmPop();
                    break;
                case R.id.copy_btn:
                    // 得到剪贴板管理器
                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(topicModelList.get(getPosition()).getContent().trim());
                    topicFragment.cancelPop();
                    break;
                case R.id.report_btn:
                    Toast.makeText(context, "这是举报按钮", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.comfirm_btn:
                    deletePost(Integer.parseInt(topicModelList.get(getPosition()).getId()));
                    break;
            }
        }
    };


    public void deletePost(int post_id) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService service = retrofit.create(UserinfoService.class);
        service.deletePost(post_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("HomePageRecycleAdapter", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("HomePageRecycleAdapter", "Error");
                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        if (registerEntity.getStatus() == 1) {
                            if (UserInsertHelper.isUserId(context, Integer.parseInt(topicModelList.get(getPosition()).getUser_id()))) {
                                Message message = handler.obtainMessage();
                                message.what = deleteFlag;
                                message.sendToTarget();
                            } else {
                                topicFragment.cancelConfirmPop();
                                Toast.makeText(context, "您没有权限做此操作", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    public void doLike(final MyViewHolder viewHolder, int post_id) {
        int value = viewHolder.defalutValue == 1 ? 0 : 1;
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService service = retrofit.create(UserinfoService.class);
        service.post_like(post_id, value)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEntity>() {
                               @Override
                               public void onCompleted() {
                                   Log.d("HomePageRecycleAdapter", "Completed");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.d("HomePageRecycleAdapter", "Error");
                               }

                               @Override
                               public void onNext(RegisterEntity registerEntity) {
                                   if (registerEntity.getStatus() == 1) {
                                       Message message = handler.obtainMessage();
                                       message.what = LikeFlage;
                                       message.obj = viewHolder;
                                       message.sendToTarget();
                                   }
                               }
                           }
                );
    }
}
