package com.dream.lmy.mydream.databaselp;

import java.sql.Date;

/**
 * LitePal中,每一张表都对应一个Model
 * <p>
 * 表中每一列都对应模型类中的一个字段
 */
public class News {

    // LitePal支持的数据类型 int short  long  float  double  boolean  String  Date

    private int id;

    private String title;

    private String content;

    private Date publisDate;

    private int commentCount;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublisDate() {
        return publisDate;
    }

    public void setPublisDate(Date publisDate) {
        this.publisDate = publisDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }


}
