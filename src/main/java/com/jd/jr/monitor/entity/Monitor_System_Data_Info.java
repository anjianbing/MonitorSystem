package com.jd.jr.monitor.entity;

/**
 * Created by anjianbing on 14-11-24.
 */
import java.io.Serializable;



/**
 * 插入监控系统捕获数据表
 */
public class Monitor_System_Data_Info implements Serializable{
    //主键id
    private String did;
    //系统id
    private String sid;
    //规则id
    private String rid;
    //是否发送邮件 0发送成功1失败
    private String isemail;
    //是否发送短信 0发送成功1失败
    private String isphone;
    //捕获到的错误日志
    private String error_log;

    public Monitor_System_Data_Info(String sid, String rid, String isemail, String isphone, String error_log) {
        this.sid = sid;
        this.rid = rid;
        this.isemail = isemail;
        this.isphone = isphone;
        this.error_log = error_log;
    }

    public Monitor_System_Data_Info(String did, String sid, String rid, String isemail, String isphone, String error_log) {
        this.did = did;
        this.sid = sid;
        this.rid = rid;
        this.isemail = isemail;
        this.isphone = isphone;
        this.error_log = error_log;
    }

    public Monitor_System_Data_Info() {
        super();
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getIsemail() {
        return isemail;
    }

    public void setIsemail(String isemail) {
        this.isemail = isemail;
    }

    public String getIsphone() {
        return isphone;
    }

    public void setIsphone(String isphone) {
        this.isphone = isphone;
    }

    public String getError_log() {
        return error_log;
    }

    public void setError_log(String error_log) {
        this.error_log = error_log;
    }
}
