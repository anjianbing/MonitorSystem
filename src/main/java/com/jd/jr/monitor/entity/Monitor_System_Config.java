package com.jd.jr.monitor.entity;

/**
 * Created by anjianbing on 14-11-24.
 */
import java.io.Serializable;

//监控系统描述表
public class Monitor_System_Config implements Serializable{
    //系统id
    private String sid;
    //系统名称
    private String sname;
    //系统描述
    private String sdesc;
    //状态是否有效 0有效1无效
    private String state;
    //监控类型
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Monitor_System_Config(String sid, String sname, String sdesc,
                                 String state,String type) {
        super();
        this.sid = sid;
        this.sname = sname;
        this.sdesc = sdesc;
        this.state = state;
        this.type = type;
    }
    public Monitor_System_Config() {
        super();
    }
    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getSname() {
        return sname;
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
    public String getSdesc() {
        return sdesc;
    }
    public void setSdesc(String sdesc) {
        this.sdesc = sdesc;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }


}
