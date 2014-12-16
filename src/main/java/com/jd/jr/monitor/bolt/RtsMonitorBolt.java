package com.jd.jr.monitor.bolt;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.jd.jr.monitor.ConfigInit.RuleConfigInit;
import com.jd.jr.monitor.entity.Monitor_System_Config;
import com.jd.jr.monitor.entity.Monitor_System_Data_Info;
import com.jd.jr.monitor.entity.Monitor_System_Rule_Config;
import com.jd.jr.monitor.entity.Monitor_System_User_Config;
import com.jd.jr.monitor.mail.SendMail;
import com.jd.jr.monitor.mail.SendMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by anjianbing on 14-11-19.
 */
public class RtsMonitorBolt extends BaseBasicBolt {
    //系统id
    private String system_id;
    //系统消息
    private String system_msg;
    //规则配置
    private RuleConfigInit ruleConfigInit;
    //分钟默认值
    private int minute = -1;
    //搜索字符
    private String search_str = "$$$$";


    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        super.prepare(stormConf, context);
        ruleConfigInit = new RuleConfigInit();
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        system_id = tuple.getStringByField("system_id");
        system_msg = tuple.getStringByField("system_msg");
        System.out.println("system_id=================================="+system_id);
        System.out.println("system_msg=================================="+system_msg);
        if(system_id !=null && system_msg != null){
           //发送的邮件和短信内容以及规则id
            String errorStrRid = ruleConfigMatch(system_id,system_msg);
           if(errorStrRid != null && errorStrRid.length() > 0){
               //搜索字符的位置
               int index_search = errorStrRid.indexOf(search_str);
               if(index_search < 0){
                   return;
               }
               //发送邮件的内容
               String sendValue = errorStrRid.substring(0,index_search);
               //规则id
               String rid = errorStrRid.substring(index_search+search_str.length());
               //获得系统名称
               List<Monitor_System_Config> monitor_System_Config_list= ruleConfigInit.systemConfigList;
               //系统名称
               String system_name="";
               if(monitor_System_Config_list != null){
                   for(Monitor_System_Config system_rule_config : monitor_System_Config_list){
                       if(system_id.equals(system_rule_config.getSid())){
                           system_name = system_rule_config.getSname();
                           break;
                       }
                   }
               }
               //获得系统用户邮箱和手机号
               String toeamil="";
               String tophone="";
               Map<String, List<Monitor_System_User_Config>>  MonitorSystemUserConfigMap= ruleConfigInit.systemUserConfigMap;
               if(MonitorSystemUserConfigMap != null && MonitorSystemUserConfigMap.size()>0){
                   List<Monitor_System_User_Config> MonitorSystemUserConfigList = MonitorSystemUserConfigMap.get(system_id);
                   if(MonitorSystemUserConfigList != null && MonitorSystemUserConfigList.size()>0){
                       for (Monitor_System_User_Config monitor_System_User_Config : MonitorSystemUserConfigList){
                           toeamil += monitor_System_User_Config.getUemail()+",";
                           tophone += monitor_System_User_Config.getUphone()+",";
                       }
                   }
               }
               if(!"".equals(toeamil)){
                   toeamil = toeamil.substring(0,toeamil.length()-1);
                   tophone = tophone.substring(0,tophone.length()-1);
               }

            try{
                //发送邮件
                boolean flag = sendMail(toeamil,system_name+"系统异常",sendValue);
                String isemail = "1";
                if(flag){
                    isemail = "0";
                }
                //发送短信信息
                String isphone = "1";
                SendMessage sendMessage = new SendMessage(tophone ,system_name +" Exception :" + sendValue) ;
                if("1".equals(sendMessage.emitMessage())){
                    isphone = "0";
                }
                Monitor_System_Data_Info dataInfo = new Monitor_System_Data_Info(system_id,rid,isemail,isphone,sendValue);
                //插入数据
                boolean insert_falg = ruleConfigInit.insertMonitorSystemDataInfo(dataInfo);
                System.out.print("insert_falg=="+insert_falg);
            }catch (Exception e){
                e.printStackTrace();
            }

           }
        }
        //定时初始化配置文件
       //scheduleLoadConfig();
    }

    /**
     *
     * @return boolean
     */
    public boolean sendMail(String to,String subject,String content){
        SendMail sendMail = new SendMail(to,"jrdpmonitor@jd.com","58.83.206.59","jrdpmonitor","mail@Password",subject,content);
        boolean flag = sendMail.send();
        return flag;
    }

    /**
     * 规则匹配
     * return 错误日志和触发的规则id
     */
    public String ruleConfigMatch(String system_id,String system_msg){
        //发送邮件字符串
        String msg = "";
        //规则id
        String rid = "";
        //获得规则匹配的map
        Map<String, List<Monitor_System_Rule_Config>> systemRuleConfigMap= ruleConfigInit.systemRuleConfigMap;
        //获得规则的内容
        List<Monitor_System_Rule_Config> ruleConfigList = systemRuleConfigMap.get(system_id);
        if(ruleConfigList != null && ruleConfigList.size()>0){
            for(Monitor_System_Rule_Config system_rule_config : ruleConfigList){
                //传入的字符是否包含到匹配的规则内容
                if(system_rule_config.getRvalue() != null && system_msg.contains(system_rule_config.getRvalue().toLowerCase())){
                    int index_search = system_msg.indexOf(system_rule_config.getRvalue().toLowerCase());
                    String msg_end = system_msg.substring(index_search);
                    if(msg_end != null && msg_end.length() > 100){
                        msg = system_msg.substring(index_search,index_search+100);
                    }else {
                        msg = msg_end;
                    }
                    return msg+"$$$$"+system_rule_config.getRid();
                }
            }
        }
        return null;
    }

    /**
     * 定时加载配置信息
     */
    public void scheduleLoadConfig(){
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        int now = Integer.parseInt( date.split(":")[1] );
        if( minute == -1 ){
                minute = now;
        }else if( minute != now && now % 10 == 0 ){//每10分钟加载一次
            //重新加载配置文件
            ruleConfigInit = new RuleConfigInit();
            minute = now;
            System.out.println("==========reload config time:" + date);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
