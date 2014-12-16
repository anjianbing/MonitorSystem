package com.jd.jr.monitor.utils;


import com.jd.jr.monitor.constant.SystemConstant;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 功能描述：
 * 同步任务需要访问的数据源，都在这里配置。
 * 后期有新的业务线，直接在此处添加即可。
 * 注意事项：
 * 后期项目如果需要迁移到其他地方，请先申请好相关数据库访问权限。
 * 目前访问数据库的权限，基本上在etl06上。
 * 否则系统无法正常运行
 * 系统维护：
 * 凌春桂 lingchungui@jd.com
 * 安监兵 anjianbing@jd.com
 * 毛祥溢 maoxiangyi@jd.com
 */
public class DBUtils {
    //统一监控平台数据库
    private static DataSource jrDataMonitorSource;

    static {
        //统一监控平台数据库
        jrDataMonitorSource = new ComboPooledDataSource(SystemConstant.JR_DATA_MONITOR_SOURCE);
    }

    /**
     * 通过传入c3p0配置的链接库字符串获得连接
     * @param sourceName
     * @return
     * @throws Exception
     */
    public static Connection getConnection(String sourceName) throws Exception {
        if (StringUtils.isBlank(sourceName)) {
            return null;
        }
        if (SystemConstant.JR_DATA_MONITOR_SOURCE.equals(sourceName)) {
            return jrDataMonitorSource.getConnection();
        }else {
            return jrDataMonitorSource.getConnection();
        }
    }



    /**
     *  默认获得统一监控平台数据库连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        return getConnection(SystemConstant.JR_DATA_MONITOR_SOURCE);
    }



}
