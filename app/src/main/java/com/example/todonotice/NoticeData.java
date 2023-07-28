package com.example.todonotice;

import androidx.appcompat.app.AppCompatActivity;

public class NoticeData extends AppCompatActivity {

    private int profile_img;
    private int like_iv;
    private int comment_button;
    private int more_button;
    private String user_id_tv;
    private String date_tv;

    // Alt + Insert
    // Constructor, Getter/Setter
    public NoticeData(int profile_img, int like_iv, int comment_button, int more_button, String user_id_tv, String date_tv) {
        this.profile_img = profile_img;
        this.like_iv = like_iv;
        this.comment_button = comment_button;
        this.more_button = more_button;
        this.user_id_tv = user_id_tv;
        this.date_tv = date_tv;
    }

    public NoticeData(int contentLayoutId, int profile_img, int like_iv, int comment_button, int more_button, String user_id_tv, String date_tv) {
        super(contentLayoutId);
        this.profile_img = profile_img;
        this.like_iv = like_iv;
        this.comment_button = comment_button;
        this.more_button = more_button;
        this.user_id_tv = user_id_tv;
        this.date_tv = date_tv;
    }

    public int getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(int profile_img) {
        this.profile_img = profile_img;
    }

    public int getLike_iv() {
        return like_iv;
    }

    public void setLike_iv(int like_iv) {
        this.like_iv = like_iv;
    }

    public int getComment_button() {
        return comment_button;
    }

    public void setComment_button(int comment_button) {
        this.comment_button = comment_button;
    }

    public int getMore_button() {
        return more_button;
    }

    public void setMore_button(int more_button) {
        this.more_button = more_button;
    }

    public String getUser_id_tv() {
        return user_id_tv;
    }

    public void setUser_id_tv(String user_id_tv) {
        this.user_id_tv = user_id_tv;
    }

    public String getDate_tv() {
        return date_tv;
    }

    public void setDate_tv(String date_tv) {
        this.date_tv = date_tv;
    }
}
