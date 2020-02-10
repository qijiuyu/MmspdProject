package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/28.
 */

public class Comment extends BaseBean {

    private List<CommentBean> data=new ArrayList<>();

    public List<CommentBean> getData() {
        return data;
    }

    public void setData(List<CommentBean> data) {
        this.data = data;
    }

    public static class CommentBean implements Serializable{
        private int commentid;
        private String content;
        private String createtime;
        private String imgs;
        private int isreplay;
        private int spucount;
        private String spuname;
        private int star;
        private String username;
        private String video;

        public int getCommentid() {
            return commentid;
        }

        public void setCommentid(int commentid) {
            this.commentid = commentid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public int getIsreplay() {
            return isreplay;
        }

        public void setIsreplay(int isreplay) {
            this.isreplay = isreplay;
        }

        public int getSpucount() {
            return spucount;
        }

        public void setSpucount(int spucount) {
            this.spucount = spucount;
        }

        public String getSpuname() {
            return spuname;
        }

        public void setSpuname(String spuname) {
            this.spuname = spuname;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }
}
