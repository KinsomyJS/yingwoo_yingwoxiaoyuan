package com.yingwoo.yingwoxiaoyuan.model;


import java.util.List;

/**
 * Created by wangyu on 9/1/16.
 */

public class AcademyListModel {

    /**
     * info : [{"group":"J","data":[{"id":"1","school_id":"1","name":"计算机科学与技术"}]},{"group":"G","data":[{"id":"2","school_id":"1","name":"工业与艺术设计"}]},{"group":"W","data":[{"id":"3","school_id":"1","name":"我编不出来了"}]}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * group : J
     * data : [{"id":"1","school_id":"1","name":"计算机科学与技术"}]
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
        private String group;
        /**
         * id : 1
         * school_id : 1
         * name : 计算机科学与技术
         */

        private List<DataBean> data;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            private String id;
            private String school_id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSchool_id() {
                return school_id;
            }

            public void setSchool_id(String school_id) {
                this.school_id = school_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
