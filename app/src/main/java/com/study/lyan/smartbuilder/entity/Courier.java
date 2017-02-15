package com.study.lyan.smartbuilder.entity;

import java.util.List;

/**
 * Created by Lyan on 17/2/12.
 * 快递查询
 */

public class Courier {

    /**
     * resultcode : 200
     * reason : 查询物流信息成功
     * result : {"company":"韵达","com":"yd","no":"1202489499853","status":"0","list":[{"datetime":"2017-02-10 18:08:05","remark":"到达：河北平乡县公司艾村便民寄存点分部 已收件","zone":""},{"datetime":"2017-02-11 00:47:01","remark":"到达：河北石家庄分拨中心","zone":""},{"datetime":"2017-02-11 00:49:41","remark":"到达：河北石家庄分拨中心 发往：广东东莞分拨中心","zone":""},{"datetime":"2017-02-12 08:41:12","remark":"到达：广东东莞分拨中心 上级站点：河北石家庄分拨中心","zone":""},{"datetime":"2017-02-12 08:58:32","remark":"到达：广东东莞分拨中心 发往：广东揭阳分拨中心","zone":""}]}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * company : 韵达
         * com : yd
         * no : 1202489499853
         * status : 0
         * list : [{"datetime":"2017-02-10 18:08:05","remark":"到达：河北平乡县公司艾村便民寄存点分部 已收件","zone":""},{"datetime":"2017-02-11 00:47:01","remark":"到达：河北石家庄分拨中心","zone":""},{"datetime":"2017-02-11 00:49:41","remark":"到达：河北石家庄分拨中心 发往：广东东莞分拨中心","zone":""},{"datetime":"2017-02-12 08:41:12","remark":"到达：广东东莞分拨中心 上级站点：河北石家庄分拨中心","zone":""},{"datetime":"2017-02-12 08:58:32","remark":"到达：广东东莞分拨中心 发往：广东揭阳分拨中心","zone":""}]
         */

        private String company;
        private String com;
        private String no;
        private String status;
        private List<ListBean> list;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * datetime : 2017-02-10 18:08:05
             * remark : 到达：河北平乡县公司艾村便民寄存点分部 已收件
             * zone :
             */

            private String datetime;
            private String remark;
            private String zone;

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }
        }
    }
}
