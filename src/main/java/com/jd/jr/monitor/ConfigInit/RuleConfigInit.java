package com.jd.jr.monitor.ConfigInit;

import com.jd.jr.monitor.dao.DBConnDAO;
import com.jd.jr.monitor.entity.Monitor_System_Config;
import com.jd.jr.monitor.entity.Monitor_System_Data_Info;
import com.jd.jr.monitor.entity.Monitor_System_Rule_Config;
import com.jd.jr.monitor.entity.Monitor_System_User_Config;
import com.jd.jr.monitor.utils.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anjianbing on 14-11-24.
 */
public class RuleConfigInit {
    //数据库连接
    private DBConnDAO dbDao;
     //系统描述map
    public List<Monitor_System_Config> systemConfigList;
    //系统规则map
    public Map<String, List<Monitor_System_Rule_Config>> systemRuleConfigMap;
    //系统对应的联系人map
    public Map<String, List<Monitor_System_User_Config>> systemUserConfigMap;
    //获得系统名称的sql
    private String system_config_sql = "select sid,sname,sdesc from monitor_system_config where state = '0'";
    //获得系统规则的sql
    private String system_rule_config_sql="select rid,rname,rvalue,rrelation,state,sid from monitor_system_rule_config where state = '0'";
    //获得系统维护用户的sql
    private String system_user_config_sql="select uid,uname,uemail,uphone,state,sid from monitor_system_user_config where state = '0'";
    //插入监控系统捕获数据表
    private String inesrt_monitor_system_data_info_sql="insert into monitor_system_data_info (sid,rid,isemail,isphone,error_log) values (?,?,?,?,?);";

    public RuleConfigInit(){
        try {
            //初始化
            systemConfigList = getMonitorSystemConfigList();
            systemRuleConfigMap = getMonitorSystemRuleConfigMap();
            systemUserConfigMap = getMonitorSystemUserConfigMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入Monitor_System_Data_Info
     * @param monitor_system_data_info
     * @return
     */
    public boolean insertMonitorSystemDataInfo(Monitor_System_Data_Info monitor_system_data_info){
        try {
            //获得连接对象
            dbDao =  new DBConnDAO(DBUtils.getConnection());
            int count = dbDao.execute(inesrt_monitor_system_data_info_sql,monitor_system_data_info.getSid(),monitor_system_data_info.getRid(),monitor_system_data_info.getIsemail(),monitor_system_data_info.getIsphone(),monitor_system_data_info.getError_log());
            if(count > 0){
                System.out.println("插入成功!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    //获得系统配置的map
    private List<Monitor_System_Config> getMonitorSystemConfigList(){
        try {
            //获得连接对象
            dbDao =  new DBConnDAO(DBUtils.getConnection());
            //从数据查询获得系统配置的map
            List<Map<String, Object>> systemConfigObj   = dbDao.queryMap(system_config_sql);
            if(systemConfigObj != null && systemConfigObj.size()>0){
                //初始化
                systemConfigList = new ArrayList<Monitor_System_Config>();
                //定义系统配置对象
                Monitor_System_Config system_config;
                for (Map<String, Object> m : systemConfigObj) {
                    system_config = new Monitor_System_Config();
                    system_config.setSid(m.get("sid") + "");
                    system_config.setSname(m.get("sname") + "");
                    system_config.setSdesc(m.get("sdesc") + "");
                    systemConfigList.add(system_config);
                }
               return systemConfigList;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //获得系统监控规则的map
    private Map<String, List<Monitor_System_Rule_Config>> getMonitorSystemRuleConfigMap(){
        try {
            //获得连接对象
            dbDao =  new DBConnDAO(DBUtils.getConnection());
            //从数据查询获得系统配置的map
            List<Map<String, Object>> systemRuleConfigObj   = dbDao.queryMap(system_rule_config_sql);
            List<Monitor_System_Rule_Config> systemRuleConfigObjList = null;
            if(systemRuleConfigObj != null && systemRuleConfigObj.size()>0){
                //初始化
                systemRuleConfigMap =  new HashMap<String, List<Monitor_System_Rule_Config>>();
                //定义系统配置对象
                Monitor_System_Rule_Config system_rule_config;
                for (Map<String, Object> m : systemRuleConfigObj) {
                    system_rule_config = new Monitor_System_Rule_Config();
                    system_rule_config.setRid(m.get("rid") + "");
                    system_rule_config.setRname(m.get("rname") + "");
                    system_rule_config.setRvalue(m.get("rvalue") + "");
                    system_rule_config.setRrelation(m.get("rrelation") + "");
                    system_rule_config.setState(m.get("state")+"");
                    system_rule_config.setSid(m.get("sid")+"");
                    if(systemRuleConfigMap.containsKey(system_rule_config.getSid())){
                        systemRuleConfigObjList = systemRuleConfigMap.get(system_rule_config.getRid());
                    }else{
                        systemRuleConfigObjList = new ArrayList<Monitor_System_Rule_Config>();
                    }
                    systemRuleConfigObjList.add(system_rule_config);
                    systemRuleConfigMap.put(system_rule_config.getSid(),systemRuleConfigObjList);
                }
                return systemRuleConfigMap;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //获得系统维护人员的map
    private Map<String, List<Monitor_System_User_Config>> getMonitorSystemUserConfigMap(){
        try {
            //获得连接对象
            dbDao =  new DBConnDAO(DBUtils.getConnection());
            //从数据查询获得系统配置的map
            List<Map<String, Object>> systemUserConfigObj   = dbDao.queryMap(system_user_config_sql);
            List<Monitor_System_User_Config> systemUserConfigObjList = null;
            if(systemUserConfigObj != null && systemUserConfigObj.size()>0){
                //初始化
                systemUserConfigMap =  new HashMap<String, List<Monitor_System_User_Config>>();
                //定义系统配置对象
                Monitor_System_User_Config system_user_config;
                for (Map<String, Object> m : systemUserConfigObj) {
                    system_user_config = new Monitor_System_User_Config();
                    system_user_config.setUid(m.get("uid")+"");
                    system_user_config.setUname(m.get("uname")+"");
                    system_user_config.setUemail(m.get("uemail")+"");
                    system_user_config.setUphone(m.get("uphone")+"");
                    system_user_config.setState(m.get("state")+"");
                    system_user_config.setSid(m.get("sid") + "");
                    if(systemUserConfigMap.containsKey(system_user_config.getSid())){
                        systemUserConfigObjList = systemUserConfigMap.get(system_user_config.getSid());
                    }else{
                        systemUserConfigObjList = new ArrayList<Monitor_System_User_Config>();
                    }
                    systemUserConfigObjList.add(system_user_config);
                    systemUserConfigMap.put(system_user_config.getSid(),systemUserConfigObjList);
                }
                return systemUserConfigMap;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
