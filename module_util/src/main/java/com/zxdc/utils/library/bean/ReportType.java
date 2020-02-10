package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/12/2.
 */

public class ReportType extends BaseBean {

    private List<ReportTypeBean> data=new ArrayList<>();

    public List<ReportTypeBean> getData() {
        return data;
    }

    public void setData(List<ReportTypeBean> data) {
        this.data = data;
    }

    public static class ReportTypeBean implements Serializable{
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
