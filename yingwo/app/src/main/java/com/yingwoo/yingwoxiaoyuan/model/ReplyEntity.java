package com.yingwoo.yingwoxiaoyuan.model;

import java.util.List;

/**
 * Created by wangyu on 9/7/16.
 */

public class ReplyEntity {

    /**
     * info : [{"id":"183","post_reply_id":"322","post_comment_id":"0","post_comment_user_id":"0","user_id":"114","content":"民工","create_time":"1473505713","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR","commented_user_name":null},{"id":"184","post_reply_id":"322","post_comment_id":"0","post_comment_user_id":"0","user_id":"114","content":"民工敏敏","create_time":"1473505731","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR","commented_user_name":null},{"id":"186","post_reply_id":"322","post_comment_id":"0","post_comment_user_id":"0","user_id":"114","content":"民工敏敏吸引xz","create_time":"1473505792","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR","commented_user_name":null},{"id":"187","post_reply_id":"322","post_comment_id":"0","post_comment_user_id":"0","user_id":"114","content":"民工","create_time":"1473505968","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR","commented_user_name":null},{"id":"188","post_reply_id":"322","post_comment_id":"0","post_comment_user_id":"0","user_id":"114","content":"龙","create_time":"1473506092","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR","commented_user_name":null},{"id":"190","post_reply_id":"322","post_comment_id":"0","post_comment_user_id":"0","user_id":"114","content":"跌","create_time":"1473508285","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR","commented_user_name":null},{"id":"196","post_reply_id":"322","post_comment_id":"114","post_comment_user_id":"114","user_id":"114","content":"名您","create_time":"1473512916","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR","commented_user_name":"人不如故"},{"id":"197","post_reply_id":"322","post_comment_id":"114","post_comment_user_id":"114","user_id":"114","content":"尽可能","create_time":"1473512955","del":"0","user_name":"人不如故","user_face_img":"http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR","commented_user_name":"人不如故"},{"id":"198","post_reply_id":"322","post_comment_id":"114","post_comment_user_id":"117","user_id":"117","content":"并","create_time":"1473513003","del":"0","user_name":"哈哈哈","user_face_img":null,"commented_user_name":"哈哈哈"},{"id":"199","post_reply_id":"322","post_comment_id":"114","post_comment_user_id":"117","user_id":"117","content":"黎明","create_time":"1473513105","del":"0","user_name":"哈哈哈","user_face_img":null,"commented_user_name":"哈哈哈"}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * id : 183
     * post_reply_id : 322
     * post_comment_id : 0
     * post_comment_user_id : 0
     * user_id : 114
     * content : 民工
     * create_time : 1473505713
     * del : 0
     * user_name : 人不如故
     * user_face_img : http://obabu2buy.bkt.clouddn.com/FkWA2QHrDiPqxr5qv0Fjar4p2rkR
     * commented_user_name : null
     */

    private List<InfoBean> info;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        private String id;
        private String post_reply_id;
        private String post_comment_id;
        private String post_comment_user_id;
        private String user_id;
        private String content;
        private String create_time;
        private String del;
        private String user_name;
        private String user_face_img;
        private Object commented_user_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPost_reply_id() {
            return post_reply_id;
        }

        public void setPost_reply_id(String post_reply_id) {
            this.post_reply_id = post_reply_id;
        }

        public String getPost_comment_id() {
            return post_comment_id;
        }

        public void setPost_comment_id(String post_comment_id) {
            this.post_comment_id = post_comment_id;
        }

        public String getPost_comment_user_id() {
            return post_comment_user_id;
        }

        public void setPost_comment_user_id(String post_comment_user_id) {
            this.post_comment_user_id = post_comment_user_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_face_img() {
            return user_face_img;
        }

        public void setUser_face_img(String user_face_img) {
            this.user_face_img = user_face_img;
        }

        public Object getCommented_user_name() {
            return commented_user_name;
        }

        public void setCommented_user_name(Object commented_user_name) {
            this.commented_user_name = commented_user_name;
        }
    }
}
