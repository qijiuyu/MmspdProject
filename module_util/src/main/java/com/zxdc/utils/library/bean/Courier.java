package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/12/4.
 */

public class Courier extends BaseBean {

    private List<CourierBean> data;

    public List<CourierBean> getData() {
        return data;
    }

    public void setData(List<CourierBean> data) {
        this.data = data;
    }

    public static class CourierBean implements Serializable{
        private String code;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
