package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwoo.yingwoxiaoyuan.PhotoViewActivity;
import com.yingwoo.yingwoxiaoyuan.PostActivity;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.model.PostDetailEntity;
import com.yingwoo.yingwoxiaoyuan.model.PostListEntity.InfoBean;
import com.yingwoo.yingwoxiaoyuan.model.ReplyEntity;
import com.yingwoo.yingwoxiaoyuan.utils.ImageUtil;
import com.yingwoo.yingwoxiaoyuan.utils.ListViewUtil;
import com.yingwoo.yingwoxiaoyuan.utils.TImeUtiil;
import com.yingwoo.yingwoxiaoyuan.utils.UrlUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangyu on 8/29/16.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AppCompatActivity context;
    private ListPostReplyItemAdapter replyAdapter;
    private List<InfoBean> postData;
    private PostDetailEntity.InfoBean topBean;
    private InfoBean CurrentInfoBean;
    private List<List<ReplyEntity.InfoBean>> replyData;
    private PostImageItemAdapter postImageItemAdapter;
    private View.OnClickListener popUpClickListener = null;
    private int POST_TOP = -1;
    private int POST_ITEM = 1;

    public PostRecyclerAdapter(AppCompatActivity context) {
        this.context = context;
        this.replyData = new ArrayList<>();
        this.postData = new ArrayList<>();
        notifyDataSetChanged();
    }


    public int getIndex(String reply_id) {
        for (int i = 0; i < postData.size(); i++) {
            if (postData.get(i).getId().equals(reply_id)) {
                return i;
            }
        }
        return -99;
    }

    public void setData(PostDetailEntity.InfoBean topBean, List<List<ReplyEntity.InfoBean>> replyData, List<InfoBean> postData) {
        this.topBean = topBean;
        this.replyData = new ArrayList<>(replyData);
        this.postData = new ArrayList<>(postData);
    }

    public void addData(List<List<ReplyEntity.InfoBean>> replyData, List<InfoBean> postData) {
        this.replyData.addAll(replyData);
        this.postData.addAll(postData);
        notifyDataSetChanged();
    }

    public void updateReply(int index, List<ReplyEntity.InfoBean> infoBeen) {
        replyData.set(index, infoBeen);
        notifyDataSetChanged();
    }

    public boolean isNoData(int index){
        if (replyData!=null){
            if (replyData.get(index).size()<1)
                return true;
        }
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == POST_TOP) {
            holder = new TopViewHolder(LayoutInflater.from(context).inflate(R.layout.list_post_top_item, parent, false));

        } else {
            holder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_post_item, parent, false));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;
        String content = null;
        String releaseTime = null;
        String face_img = null;
        String user_name = null;
        String img_urls = null;
        if (holder instanceof TopViewHolder) {
            ((TopViewHolder) holder).topicLable.setText(topBean.getTopic_title() == null ? "新鲜事" : topBean.getTopic_title());
            img_urls = topBean.getImg();
            user_name = topBean.getUser_name();
            releaseTime = topBean.getCreate_time();
            content = topBean.getContent();
            face_img = topBean.getUser_face_img();
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).iv_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentInfoBean(postData.get(position - 1));
                    if (PostRecyclerAdapter.this.context instanceof PostActivity)
                        ((PostActivity) PostRecyclerAdapter.this.context).showSoftInput();
                }
            });
//                ((ItemViewHolder) holder).iv_like.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((ItemViewHolder) holder).iv_like.setEnabled(false);
//                        int reply_id = Integer.parseInt(postData.get(position - 2).getId());
//                        doLike((ItemViewHolder) holder, reply_id);
//                    }
//                });
            InfoBean infoBean = postData.get(position - 1);
//                ((ItemViewHolder) holder).defalutValue = Integer.parseInt(infoBean.getUser_post_like());
//                ((ItemViewHolder) holder).iv_like.setBackgroundResource(((HomePageRecycleAdapter.MyViewHolder) holder).defalutValue == 1 ? R.mipmap.heart_red : R.mipmap.heart_gray);

            if (infoBean.getUser_name().equals(topBean.getUser_name())) {
                ((ItemViewHolder) holder).louzhu_icon.setVisibility(View.VISIBLE);
            } else {
                ((ItemViewHolder) holder).louzhu_icon.setVisibility(View.GONE);
            }
            img_urls = infoBean.getImg();
            user_name = infoBean.getUser_name();
            releaseTime = infoBean.getCreate_time();
            content = infoBean.getContent();
            face_img = infoBean.getUser_face_img();
            ((ItemViewHolder) holder).like_num.setText(postData.get(position - 1).getLike_cnt());
            ((ItemViewHolder) holder).comment_num.setText(postData.get(position - 1).getComment_cnt());
            if (replyData.get(position - 1) != null && replyData.get(position - 1).size() > 0) {
                ((ItemViewHolder) holder).reply_layout.setVisibility(View.VISIBLE);
                replyAdapter = new ListPostReplyItemAdapter(this.context, replyData.get(position - 1));
                ((ItemViewHolder) holder).reply_list.setLayoutManager(new LinearLayoutManager(this.context));
                ((ItemViewHolder) holder).reply_list.setAdapter(replyAdapter);
                replyAdapter.setOnItemClickListener(new ListPostReplyItemAdapter.ReplyItemClickListener() {
                    @Override
                    public void onItemClick(View view, ReplyEntity.InfoBean data) {
                        setCurrentInfoBean(postData.get(position - 1));
                        if (PostRecyclerAdapter.this.context instanceof PostActivity) {
                            ((PostActivity) PostRecyclerAdapter.this.context).showSoftInput();
                            ((PostActivity) PostRecyclerAdapter.this.context).setCurrentInfoBean(data);
                        }
                    }
                });
            } else {
                ((ItemViewHolder) holder).reply_list.setVisibility(View.GONE);
            }

        }

        if (!content.isEmpty())

        {
            myHolder.tvContent.setVisibility(View.VISIBLE);
            myHolder.tvContent.setText(content);
            if (content.contains("href")) {
                myHolder.tvContent = UrlUtils.handleHtmlText(((MyViewHolder) holder).tvContent, content);
            } else {
                myHolder.tvContent = UrlUtils.handleText(((MyViewHolder) holder).tvContent, content);
            }
        } else

        {
            myHolder.tvContent.setVisibility(View.GONE);
        }

        if (face_img != null)

        {
            myHolder.face_img.setImageURI(face_img);
        } else

        {
            myHolder.face_img.setImageURI(ImageUtil.resourceIdToUri(context, R.mipmap.touxiang));
        }

        if (img_urls.contains("http"))

        {
            myHolder.listView.setVisibility(View.VISIBLE);
            setImage(myHolder.listView, img_urls);
        } else

        {
            myHolder.listView.setVisibility(View.GONE);
        }

        myHolder.tvLouCeng.setText("第" + (position) + "楼");
        setPopup(myHolder.ibMore);
        myHolder.userName.setText(user_name);
        myHolder.time.setText(TImeUtiil.getStandardDate(releaseTime));

    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return POST_TOP;
        } else {
            return POST_ITEM;
        }

    }

    @Override
    public int getItemCount() {
        return postData.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvLouCeng;
        SimpleDraweeView header;
        TextView userName;
        ImageButton ibMore;
        SimpleDraweeView face_img;
        TextView time;
        ListView listView;
        TextView louzhu_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            louzhu_icon = (TextView) itemView.findViewById(R.id.tv_louzhu);
            time = (TextView) itemView.findViewById(R.id.tv_releasetime);
            face_img = (SimpleDraweeView) itemView.findViewById(R.id.iv_userhead);
            userName = (TextView) itemView.findViewById(R.id.tv_userName);
            header = (SimpleDraweeView) itemView.findViewById(R.id.iv_userhead);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvLouCeng = (TextView) itemView.findViewById(R.id.tv_louceng);
            ibMore = (ImageButton) itemView.findViewById(R.id.ib_Command);
            listView = (ListView) itemView.findViewById(R.id.ImageList);
        }
    }

    public class HeadViewHolder extends MyViewHolder {
        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ItemViewHolder extends MyViewHolder {
        ImageView iv_reply;
        ImageView iv_like;
        RecyclerView reply_list;
        TextView like_num;
        TextView comment_num;
        LinearLayout reply_layout;
        int defalutValue;
//        boolean isFirst = true;

        public ItemViewHolder(View itemView) {
            super(itemView);
            defalutValue = 0;
            reply_layout = (LinearLayout) itemView.findViewById(R.id.reply_layout);
            iv_reply = (ImageView) itemView.findViewById(R.id.post_reply);
            iv_like = (ImageView) itemView.findViewById(R.id.iv_like);
            reply_list = (RecyclerView) itemView.findViewById(R.id.post_reply_list);
            like_num = (TextView) itemView.findViewById(R.id.tv_likenum);
            comment_num = (TextView) itemView.findViewById(R.id.tv_commentnum);
        }
    }


    public class TopViewHolder extends MyViewHolder {
        private TextView topicLable;

        public TopViewHolder(View itemView) {
            super(itemView);
            topicLable = (TextView) itemView.findViewById(R.id.tv_topiclabel);
        }

    }

    public void setImage(ListView listView, String imageUrl) {
        if (!imageUrl.contains("http")) {
            listView.setVisibility(View.GONE);
            return;
        }

        String[] urls = imageUrl.split(",");
        final List<String> bigimgurls = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            urls[i] = (String) ImageUtil.downloadImageFormat(urls[i])[0];
            bigimgurls.add(urls[i]+ "?imageMogr2/auto-orient/thumbnail/!90p");
            urls[i] = urls[i] + "?imageMogr2/auto-orient/thumbnail/!50p";
        }
        List<String> data = Arrays.asList(urls);
        postImageItemAdapter = new PostImageItemAdapter(context, data);
        listView.setAdapter(postImageItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(context, PhotoViewActivity.class);
                intent.putExtra("page", position);
                intent.putExtra("photo_list", (Serializable) bigimgurls);
                context.startActivity(intent);
            }
        });
        ListViewUtil.setListViewHeightBasedOnChildren(listView);
    }

    public void setData(List<InfoBean> postData, List<List<ReplyEntity.InfoBean>> replyData) {
        this.postData = postData;
        this.replyData = replyData;
    }

    public void setPopup(ImageButton imageButton) {
        if (popUpClickListener != null) {
            imageButton.setOnClickListener(popUpClickListener);
        }
    }

    public void setPopUpClickListener(View.OnClickListener onClickListener) {
        popUpClickListener = onClickListener;
    }

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            HomePageRecycleAdapter.MyViewHolder viewHolder = (HomePageRecycleAdapter.MyViewHolder) msg.obj;
//            int like_num = Integer.parseInt(viewHolder.tv_likenum.getText().toString());
//            if (viewHolder.defalutValue == 0) {
//                viewHolder.btn_like.setBackgroundResource(R.mipmap.heart_red);
//                viewHolder.tv_likenum.setText((like_num + 1) + "");
//                viewHolder.defalutValue = 1;
//            } else {
//                viewHolder.btn_like.setBackgroundResource(R.mipmap.heart_gray);
//                viewHolder.tv_likenum.setText((like_num - 1) + "");
//                viewHolder.defalutValue = 0;
//            }
//            viewHolder.btn_like.setEnabled(true);
//
//        }
//    };
//
//
//    public void doLike(final ItemViewHolder viewHolder, int post_id) {
//        int value = viewHolder.defalutValue == 1 ? 0 : 1;
//        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
//        UserinfoService service = retrofit.create(UserinfoService.class);
//        service.post_like(post_id, value)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<RegisterEntity>() {
//                               @Override
//                               public void onCompleted() {
//                                   if (BuildConfig.DEBUG)
//                                       Log.d("HomePageRecycleAdapter", "Completed");
//                               }
//
//                               @Override
//                               public void onError(Throwable e) {
//                                   if (BuildConfig.DEBUG) Log.d("HomePageRecycleAdapter", "Error");
//                               }
//
//                               @Override
//                               public void onNext(RegisterEntity registerEntity) {
//                                   if (registerEntity.getStatus() == 1) {
//                                       Message message = handler.obtainMessage();
//                                       message.obj = viewHolder;
//                                       message.sendToTarget();
//                                   }
//                               }
//                           }
//                );
//    }


    public InfoBean getCurrentInfoBean() {
        return CurrentInfoBean;
    }

    public void setCurrentInfoBean(InfoBean currentInfoBean) {
        CurrentInfoBean = currentInfoBean;
    }


}
