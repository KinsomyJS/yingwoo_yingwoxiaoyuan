package com.yingwoo.yingwoxiaoyuan.model;


import java.util.List;

/**
 * Created by wangyu on 9/1/16.
 */

public class SchoolListModel {


    /**
     * info : [{"group":"N","data":[{"id":"1","name":"南京工业大学"},{"id":"2","name":"南京审计大学"}]},{"group":"T","data":[{"id":"3","name":"天津工业大学"}]},{"group":"B","data":[{"id":"4","name":"北京工业大学"}]},{"group":"H","data":[{"id":"5","name":"哈尔滨工业大学"}]}]
     * status : 1
     * url :
     */

    private int status;
    private String url;
    /**
     * group : N
     * data : [{"id":"1","name":"南京工业大学"},{"id":"2","name":"南京审计大学"}]
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
         * name : 南京工业大学
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
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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
