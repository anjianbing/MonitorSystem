package com.jd.jr.monitor.mail;

import com.jd.mobilephonemsg.sender.ws.server.newmessageservice.Message;
import com.jd.mobilephonemsg.sender.ws.server.newmessageservice.MmsResult;
import com.jd.msg.sender.client.proxyClient.SendUmMsg;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lingchungui on 14-12-12.
 */
public class SendMessage {

    //手机号码
    public String phone_num ;

    //信息内容
    public String phone_msg ;


    public SendMessage(String phone_num, String phone_msg) {
        this.phone_num = phone_num;
        this.phone_msg = phone_msg;
    }

    public String emitMessage () {

            final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

            SendUmMsg sendUmMsg = (SendUmMsg) ctx.getBean("sendUmMsg");

            //获取手机号码、信息
            String phone_num=this.getPhone_num() ;
            String phone_msg=this.getPhone_msg() ;

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());

            Message message = new Message();
            message.setMobileNum(phone_num);
            message.setMsgContent(phone_msg);
            message.setOrderId("");
            message.setSenderNum("jrjt.data.monitor");
            message.setType("");
            MmsResult result = sendUmMsg.sendSMS(message);

            //返回值为 1  标识发送成功
            //System.out.println("return_values:"+result.getReceiptNum() + " [1:send ok]");
            //logger.info(date + "  " + phone_num + " : " + phone_msg);
        return result.getReceiptNum();

    }


    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getPhone_msg() {
        return phone_msg;
    }

    public void setPhone_msg(String phone_msg) {
        this.phone_msg = phone_msg;
    }
}
