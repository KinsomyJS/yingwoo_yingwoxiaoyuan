package com.yingwoo.yingwoxiaoyuan.model;

import java.util.List;

/**
 * Created by FJS0420 on 2016/9/26.
 */

public class RecommendTopicModel {

    /**
     * info : [{"id":"1","field_id":"1","title":"校事","description":"校园生活","status":"1","img":"http://obabu2buy.bkt.clouddn.com/icon/icon.png","top":"2","del":"0","topic_list":[{"id":"1","subject_id":"1","school_id":"1","title":"大学排名 南工有多强","img":"http://obabu2buy.bkt.clouddn.com/icon/bagua@3x.png","description":null,"post_cnt":"0","like_cnt":"2","status":"1","top":"1","hot":"0","del":"0","sort":"0","big_img":""},{"id":"2","subject_id":"1","school_id":"1","title":"百团大战","img":"http://obabu2buy.bkt.clouddn.com/icon/gongke@3x.png","description":null,"post_cnt":"0","like_cnt":"1","status":"1","top":"1","hot":"1","del":"0","sort":"0","big_img":""}]},{"id":"2","field_id":"1","title":"情感","description":"校园生活","status":"1","img":"http://obabu2buy.bkt.clouddn.com/icon/icon.png","top":"6","del":"0","topic_list":[{"id":"4","subject_id":"2","school_id":"1","title":"情商为负的同学有多讨厌","img":"http://obabu2buy.bkt.clouddn.com/icon/hot@3x.png","description":null,"post_cnt":"2","like_cnt":"0","status":"1","top":"1","hot":"0","del":"0","sort":"0","big_img":""}]}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * id : 1
     * field_id : 1
     * title : 校事
     * description : 校园生活
     * status : 1
     * img : http://obabu2buy.bkt.clouddn.com/icon/icon.png
     * top : 2
     * del : 0
     * topic_list : [{"id":"1","subject_id":"1","school_id":"1","title":"大学排名 南工有多强","img":"http://obabu2buy.bkt.clouddn.com/icon/bagua@3x.png","description":null,"post_cnt":"0","like_cnt":"2","status":"1","top":"1","hot":"0","del":"0","sort":"0","big_img":""},{"id":"2","subject_id":"1","school_id":"1","title":"百团大战","img":"http://obabu2buy.bkt.clouddn.com/icon/gongke@3x.png","description":null,"post_cnt":"0","like_cnt":"1","status":"1","top":"1","hot":"1","del":"0","sort":"0","big_img":""}]
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
        private String field_id;
        private String title;
        private String description;
        private String status;
        private String img;
        private String top;
        private String del;
        /**
         * id : 1
         * subject_id : 1
         * school_id : 1
         * title : 大学排名 南工有多强
         * img : http://obabu2buy.bkt.clouddn.com/icon/bagua@3x.png
         * description : null
         * post_cnt : 0
         * like_cnt : 2
         * status : 1
         * top : 1
         * hot : 0
         * del : 0
         * sort : 0
         * big_img :
         */

        private List<TopicListBean> topic_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getField_id() {
            return field_id;
        }

        public void setField_id(String field_id) {
            this.field_id = field_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public List<TopicListBean> getTopic_list() {
            return topic_list;
        }

        public void setTopic_list(List<TopicListBean> topic_list) {
            this.topic_list = topic_list;
        }

        public static class TopicListBean {
            private String id;
            private String subject_id;
            private String school_id;
            private String title;
            private String img;
            private Object description;
            private String post_cnt;
            private String like_cnt;
            private String status;
            private String top;
            private String hot;
            private String del;
            private String sort;
            private String big_img;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSubject_id() {
                return subject_id;
            }

            public void setSubject_id(String subject_id) {
                this.subject_id = subject_id;
            }

            public String getSchool_id() {
                return school_id;
            }

            public void setSchool_id(String school_id) {
                this.school_id = school_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public String getPost_cnt() {
                return post_cnt;
            }

            public void setPost_cnt(String post_cnt) {
                this.post_cnt = post_cnt;
            }

            public String getLike_cnt() {
                return like_cnt;
            }

            public void setLike_cnt(String like_cnt) {
                this.like_cnt = like_cnt;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
            }

            public String getHot() {
                return hot;
            }

            public void setHot(String hot) {
                this.hot = hot;
            }

            public String getDel() {
                return del;
            }

            public void setDel(String del) {
                this.del = del;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getBig_img() {
                return big_img;
            }

            public void setBig_img(String big_img) {
                this.big_img = big_img;
            }
        }
    }
}
