package com.jd.jr.monitor.entity;

/**
 * Created by anjianbing on 14-11-24.
 */
import java.io.Serializable;

//监控系统维护用户人员表
public class Monitor_System_User_Config implements Serializable{
    //用户id
    private String uid;
    //用户名称
    private String uname;
    //用户邮箱
    private String uemail;
    //用户手机号
    private String uphone;
    //用户状态是否有效
    private String state;
    //系统id
    private String sid;
    public Monitor_System_User_Config(String uid, String uname, String uemail,
                                      String uphone, String state, String sid) {
        super();
        this.uid = uid;
        this.uname = uname;
        this.uemail = uemail;
        this.uphone = uphone;
        this.state = state;
        this.sid = sid;
    }
    public Monitor_System_User_Config() {
        super();
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUemail() {
        return uemail;
    }
    public void setUemail(String uemail) {
        this.uemail = uemail;
    }
    public String getUphone() {
        return uphone;
    }
    public void setUphone(String uphone) {
        this.uphone = uphone;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }

}
