package com.yingwoo.yingwoxiaoyuan.utils;

import com.yingwoo.yingwoxiaoyuan.model.AcademyListModel;
import com.yingwoo.yingwoxiaoyuan.model.FieldModel;
import com.yingwoo.yingwoxiaoyuan.model.HotlistModel;
import com.yingwoo.yingwoxiaoyuan.model.LoginEntity;
import com.yingwoo.yingwoxiaoyuan.model.MyTopicEntity;
import com.yingwoo.yingwoxiaoyuan.model.PostDetailEntity;
import com.yingwoo.yingwoxiaoyuan.model.PostListEntity;
import com.yingwoo.yingwoxiaoyuan.model.PostModel;
import com.yingwoo.yingwoxiaoyuan.model.RegisterEntity;
import com.yingwoo.yingwoxiaoyuan.model.ReplyEntity;
import com.yingwoo.yingwoxiaoyuan.model.SchoolListModel;
import com.yingwoo.yingwoxiaoyuan.model.SubjectModdel;
import com.yingwoo.yingwoxiaoyuan.model.TopicBaseInfo;
import com.yingwoo.yingwoxiaoyuan.model.TopicListModel;
import com.yingwoo.yingwoxiaoyuan.model.UserOnlineInfoEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by FJS0420 on 2016/8/31.
 */

public interface UserinfoService {
    String BASE_URL = "http://yw.zhibaizhi.com/yingwophp/api/v1/";


    //注册 请求短信验证码接口
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Sms/Send")
    Observable<RegisterEntity> smsSend(@Field("mobile") String mobile);

    //注册 请求短信验证码接口
    @GET("getRegisterSMS")
    Call<String> requestVerify1(@Query("mobile") String mobile);

    //注册 验证短信接口
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Sms/Check")
    Observable<RegisterEntity> verifySms(@Field("mode") String mode, @Field("mobile") String mobile, @Field("code") String code);

    //注册接口    @Headers("X-Requested-With:XMLHttpRequest")

    @Headers("X-Requested-With:XMLHttpRequest")
    @FormUrlEncoded
    @POST("User/Register")
    Observable<RegisterEntity> register(@Field("password") String password, @Field("mobile") String mobile);

    //登录接口
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("User/Login")
    Observable<LoginEntity> login(@Field("mobile") String mobile, @Field("password") String password);


    //删除帖子
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/del")
    Observable<RegisterEntity> deletePost(@Field("post_id") int post_id);


    //获取学校列表
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("school/school_list_by_group")
    Observable<SchoolListModel> getSchools();

    //获取专业列表
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("school/academy_list_by_group")
    Observable<AcademyListModel> getAcademyList(@Field("school_id") String school_id);

    //修改用户个人信息
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("User/Update")
    Observable<RegisterEntity> updateUserInfo(@Field("name") String name, @Field("grade") String grade, @Field("signature") String signature, @Field("sex") int sex, @Field("face_img") String img_url, @Field("school_id") String school_id, @Field("academy_id") String academy_id);

    //完善个人信息
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("User/Base_info")
    Observable<RegisterEntity> updateBaseInfo(@Field("name") String name, @Field("grade") String grade, @Field("signature") String signature, @Field("sex") int sex, @Field("face_img") String img_url, @Field("school_id") String school_id, @Field("academy_id") String academy_id);

    //获取用户信息
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("User/info")
    Observable<UserOnlineInfoEntity> getUser();


    //获取帖子
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/reply_list")
    Observable<PostListEntity> getPost(@Field("post_id") int post_id, @Field("start_id") int start_id);

    //获取帖子回复
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/reply")
    Observable<String> build_reply(@Field("post_id") int post_id, @Field("img") String img, @Field("content") String content);


    //首页列表
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/index")
    Observable<PostModel> getAllList(@Field("start_id") int start_id, @Field("filter") int filter);

    //我的帖子
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/my_list")
    Observable<PostModel> getMyPost(@Field("start_id") int start_id);

    //获取帖子列表
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/get_list")
    Observable<PostModel> getPostList(@FieldMap Map<String, Object> post_list_map);


    //发布帖子
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/add_new")
    Observable<String> releasePost(@Field("topic_id") int topic_id, @Field("content") String content, @Field("img") String imgurls);

    //帖子评论列表
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/Comment_list")
    Observable<ReplyEntity> comment_list(@FieldMap Map<String, Integer> reply_map);

    //帖子评论
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/Comment")
    Observable<String> post_comment(@FieldMap Map<String, Object> reply_map);

    //修改信息
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("User/Update")
    Observable<RegisterEntity> update_info(@FieldMap Map<String, Object> info_map);

    //获取领域列表
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Field/get_list")
    Observable<FieldModel> getFieldList();

    //获取主题列表
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Subject/get_list")
    Observable<SubjectModdel> getSubjectList(@Field("field_id") int field_id);

    //话题获取列表
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Topic/get_list")
    Observable<TopicListModel> getTopicList(@Field("subject_id") int subject_id, @Field("recommended") int recommended);


    //获取我的话题列表
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Topic/like_list")
    Observable<MyTopicEntity> getMyTopicList(@Field("field_id") int field_id);

    //获取我的话题列表
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST(" Topic/like")
    Observable<RegisterEntity> TopicLike(@Field("topic_id") int topic_id, @Field("value") int value);

    //获取话题基本信息
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Topic/detail")
    Observable<TopicBaseInfo> getTopicDetail(@Field("topic_id") int topic_id);


    //获取话题基本信息
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/detail")
    Observable<PostDetailEntity> getPostDetail(@Field("post_id") int post_id);

    //帖子喜欢
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/like")
    Observable<RegisterEntity> post_like(@Field("post_id") int post_id, @Field("value") int value);

    //更贴喜欢
    @FormUrlEncoded
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Post/like")
    Observable<RegisterEntity> reply_like(@Field("reply_id") int reply_id, @Field("value") int value);


    //热点话题轮播
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("Topic/hot_list")
    Observable<HotlistModel> gethotlist();

}
