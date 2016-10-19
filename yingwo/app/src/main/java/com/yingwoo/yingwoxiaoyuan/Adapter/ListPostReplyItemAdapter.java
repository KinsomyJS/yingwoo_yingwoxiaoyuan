package com.yingwoo.yingwoxiaoyuan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yingwoo.yingwoxiaoyuan.PostActivity;
import com.yingwoo.yingwoxiaoyuan.R;
import com.yingwoo.yingwoxiaoyuan.WebviewActivity;
import com.yingwoo.yingwoxiaoyuan.model.ReplyEntity.InfoBean;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ListPostReplyItemAdapter extends RecyclerView.Adapter<ListPostReplyItemAdapter.MyViewHolder> implements View.OnClickListener {
    private ReplyItemClickListener replyClickListener = null;
    private AppCompatActivity context;
    private List<InfoBean> reply_data;

    public ListPostReplyItemAdapter(AppCompatActivity context, List<InfoBean> reply_data) {
        this.context = context;
        this.reply_data = reply_data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_post_reply_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //评论人的姓名和ID
        holder.itemView.setOnClickListener(this);
        String commentName = reply_data.get(position).getUser_name();
        String commentId = reply_data.get(position).getUser_id();
        //被评论的人的ID
        String post_comment_id = reply_data.get(position).getPost_comment_id();
        //评论人的ID和姓名
        String post_comment_user_name = (String) reply_data.get(position).getCommented_user_name();
        String post_comment_user_id = reply_data.get(position).getPost_comment_user_id();
        String all_comment = "";
        all_comment += commentName;
        String reply_content = reply_data.get(position).getContent();
        if (PostActivity.topBean.getUser_id().equals(commentId)) {
            all_comment += "[louzhu]";
        }
        if (post_comment_user_name != null && !post_comment_user_name.equals("")) {
            all_comment += "回复" + post_comment_user_name;
        }
        all_comment += " : " + reply_content;

        SpannableStringBuilder spannable = new SpannableStringBuilder(all_comment);
        Pattern pattern = Pattern.compile("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&#%_\\./-~-]*)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(all_comment);


        CharacterStyle post_user_name = new ForegroundColorSpan(context.getResources().getColor(R.color.teal_a700));
        spannable.setSpan(post_user_name, 0, commentName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (PostActivity.topBean.getUser_id().equals(commentId)) {
            Drawable drawable = context.getResources().getDrawable(R.mipmap.louzhubiaoqian);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            ImageSpan louzhu = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
            spannable.setSpan(louzhu, commentName.length(), commentName.length() + "[louzhu]".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (post_comment_user_name != null && !post_comment_user_name.equals("")) {

        }
        while (matcher.find()) {
            String url = matcher.group();
            int start = all_comment.indexOf(url);
            if (start >= 0) {
                int end = start + url.length();
//                sp.setSpan(new MyUrlSpan(url), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                spannable.setSpan(getClickableSpan(url), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        holder.tv_content.setText(spannable);
        holder.itemView.setTag(reply_data.get(position));
    }

    private static ClickableSpan getClickableSpan(final String url) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                /**
                 浏览器打开
                 */
                /*Uri uri = Uri.parse(url);
                Context context = widget.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
                context.startActivity(intent);*/
                /**
                 * webview打开
                 */
                Log.d("urlllllll",url);
                Context context = widget.getContext();
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("url",url);
                context.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };
    }


    @Override
    public int getItemCount() {
        return reply_data.size();
    }

    @Override
    public void onClick(View v) {
        if (replyClickListener != null) {
            //注意这里使用getTag方法获取数据
            replyClickListener.onItemClick(v, (InfoBean) v.getTag());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.reply_content);
        }
    }

    //define interface
    public static interface ReplyItemClickListener {
        void onItemClick(View view, InfoBean data);
    }

    public void setOnItemClickListener(ReplyItemClickListener listener) {
        this.replyClickListener = listener;
    }

}
