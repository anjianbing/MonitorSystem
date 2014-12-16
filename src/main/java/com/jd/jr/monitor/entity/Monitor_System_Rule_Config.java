package com.jd.jr.monitor.entity;

/**
 * Created by anjianbing on 14-11-24.
 */
import java.io.Serializable;

//监控系统匹配的规则表
public class Monitor_System_Rule_Config implements Serializable{
    //规则id
    private String rid;
    //规则名称
    private String rname;
    //规则描述
    private String rdesc;
    //匹配规则的内容
    private String rvalue;
    //匹配关系
    private String rrelation;
    //状态是否有效 0有效1无效
    private String state;
    //关联的系统id
    private String sid;
    public Monitor_System_Rule_Config(String rid, String rname, String rdesc,
                                      String rvalue, String rrelation, String state, String sid) {
        super();
        this.rid = rid;
        this.rname = rname;
        this.rdesc = rdesc;
        this.rvalue = rvalue;
        this.rrelation = rrelation;
        this.state = state;
        this.sid = sid;
    }
    public Monitor_System_Rule_Config() {
        super();
    }
    public String getRid() {
        return rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }
    public String getRname() {
        return rname;
    }
    public void setRname(String rname) {
        this.rname = rname;
    }
    public String getRdesc() {
        return rdesc;
    }
    public void setRdesc(String rdesc) {
        this.rdesc = rdesc;
    }
    public String getRvalue() {
        return rvalue;
    }
    public void setRvalue(String rvalue) {
        this.rvalue = rvalue;
    }
    public String getRrelation() {
        return rrelation;
    }
    public void setRrelation(String rrelation) {
        this.rrelation = rrelation;
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
