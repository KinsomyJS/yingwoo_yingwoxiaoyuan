package com.yingwoo.yingwoxiaoyuan.model;

import java.util.List;

/**
 * Created by wangyu on 9/20/16.
 */

public class MyTopicEntity {

    /**
     * info : [{"status":"1","id":"7","subject_id":"2","school_id":"1","title":"在南工必做的一些事","img":"http://image.zhibaizhi.com/u=4294704082,164857477&fm=21&gp=0.png","description":null,"post_cnt":"1","like_cnt":"7","top":"1","hot":"0","del":"1","sort":"6","big_img":"","create_time":"0","topic_id":"7","topic_subject_id":"2","topic_school_id":"1","topic_title":"在南工必做的一些事","topic_img":"http://image.zhibaizhi.com/u=4294704082,164857477&fm=21&gp=0.png","topic_description":null,"topic_like_cnt":"7","topic_post_cnt":"1"},{"status":"1","id":"131","subject_id":"2","school_id":"1","title":"高颜值辅导员","img":"http://obabu2buy.bkt.clouddn.com/Topic_img/1475996070224","description":"","post_cnt":"1","like_cnt":"1","top":"1","hot":"0","del":"1","sort":"131","big_img":"","create_time":"1475996220","topic_id":"131","topic_subject_id":"2","topic_school_id":"1","topic_title":"高颜值辅导员","topic_img":"http://obabu2buy.bkt.clouddn.com/Topic_img/1475996070224","topic_description":"","topic_like_cnt":"1","topic_post_cnt":"1"},{"status":"1","id":"5","subject_id":"2","school_id":"1","title":"寻找秋天的颜色","img":"http://obabu2buy.bkt.clouddn.com/Topic_img/5_1476191432184","description":"","post_cnt":"2","like_cnt":"12","top":"0","hot":"1","del":"0","sort":"8","big_img":"http://obabu2buy.bkt.clouddn.com/Topic_big_img/5_1475998812076","create_time":"0","topic_id":"5","topic_subject_id":"2","topic_school_id":"1","topic_title":"寻找秋天的颜色","topic_img":"http://obabu2buy.bkt.clouddn.com/Topic_img/5_1476191432184","topic_description":"","topic_like_cnt":"12","topic_post_cnt":"2"},{"status":"1","id":"139","subject_id":"2","school_id":"1","title":"谈一场跨越时空的异地恋","img":"http://obabu2buy.bkt.clouddn.com/Topic_img/139_1476156611234","description":"","post_cnt":"7","like_cnt":"7","top":"0","hot":"0","del":"0","sort":"139","big_img":"","create_time":"1476156266","topic_id":"139","topic_subject_id":"2","topic_school_id":"1","topic_title":"谈一场跨越时空的异地恋","topic_img":"http://obabu2buy.bkt.clouddn.com/Topic_img/139_1476156611234","topic_description":"","topic_like_cnt":"7","topic_post_cnt":"7"},{"status":"1","id":"143","subject_id":"2","school_id":"1","title":"狗粮发放现场","img":"http://obabu2buy.bkt.clouddn.com/Topic_img/1476263083862","description":"","post_cnt":"7","like_cnt":"6","top":"1","hot":"0","del":"0","sort":"141","big_img":"","create_time":"1476263083","topic_id":"143","topic_subject_id":"2","topic_school_id":"1","topic_title":"狗粮发放现场","topic_img":"http://obabu2buy.bkt.clouddn.com/Topic_img/1476263083862","topic_description":"","topic_like_cnt":"6","topic_post_cnt":"7"},{"status":"1","id":"147","subject_id":"2","school_id":"1","title":"南工最佳约会地点","img":"http://obabu2buy.bkt.clouddn.com/Topic_img/1476331630487","description":"","post_cnt":"4","like_cnt":"4","top":"1","hot":"0","del":"0","sort":"147","big_img":"","create_time":"1476331773","topic_id":"147","topic_subject_id":"2","topic_school_id":"1","topic_title":"南工最佳约会地点","topic_img":"http://obabu2buy.bkt.clouddn.com/Topic_img/1476331630487","topic_description":"","topic_like_cnt":"4","topic_post_cnt":"4"}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * status : 1
     * id : 7
     * subject_id : 2
     * school_id : 1
     * title : 在南工必做的一些事
     * img : http://image.zhibaizhi.com/u=4294704082,164857477&fm=21&gp=0.png
     * description : null
     * post_cnt : 1
     * like_cnt : 7
     * top : 1
     * hot : 0
     * del : 1
     * sort : 6
     * big_img :
     * create_time : 0
     * topic_id : 7
     * topic_subject_id : 2
     * topic_school_id : 1
     * topic_title : 在南工必做的一些事
     * topic_img : http://image.zhibaizhi.com/u=4294704082,164857477&fm=21&gp=0.png
     * topic_description : null
     * topic_like_cnt : 7
     * topic_post_cnt : 1
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
        private String status;
        private String id;
        private String subject_id;
        private String school_id;
        private String title;
        private String img;
        private Object description;
        private String post_cnt;
        private String like_cnt;
        private String top;
        private String hot;
        private String del;
        private String sort;
        private String big_img;
        private String create_time;
        private String topic_id;
        private String topic_subject_id;
        private String topic_school_id;
        private String topic_title;
        private String topic_img;
        private Object topic_description;
        private String topic_like_cnt;
        private String topic_post_cnt;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public String getTopic_subject_id() {
            return topic_subject_id;
        }

        public void setTopic_subject_id(String topic_subject_id) {
            this.topic_subject_id = topic_subject_id;
        }

        public String getTopic_school_id() {
            return topic_school_id;
        }

        public void setTopic_school_id(String topic_school_id) {
            this.topic_school_id = topic_school_id;
        }

        public String getTopic_title() {
            return topic_title;
        }

        public void setTopic_title(String topic_title) {
            this.topic_title = topic_title;
        }

        public String getTopic_img() {
            return topic_img;
        }

        public void setTopic_img(String topic_img) {
            this.topic_img = topic_img;
        }

        public Object getTopic_description() {
            return topic_description;
        }

        public void setTopic_description(Object topic_description) {
            this.topic_description = topic_description;
        }

        public String getTopic_like_cnt() {
            return topic_like_cnt;
        }

        public void setTopic_like_cnt(String topic_like_cnt) {
            this.topic_like_cnt = topic_like_cnt;
        }

        public String getTopic_post_cnt() {
            return topic_post_cnt;
        }

        public void setTopic_post_cnt(String topic_post_cnt) {
            this.topic_post_cnt = topic_post_cnt;
        }
    }
}
