package com.jd.jr.monitor.topology;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import com.jd.jr.monitor.bolt.RtsMonitorBolt;
import com.jd.jr.monitor.spout.MetaSpout;
import com.jd.jr.monitor.spout.RandomSpout;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.utils.ZkUtils;

/**
 * Created by anjianbing on 14-11-19.
 */
public class MonitorTopology {
    public static void main(String[] args) {
//        String zkAddress = "172.23.144.106:2181,172.23.144.107:2181,172.23.144.108:2181";
//        String group = "rtsGroupMonitor";
//        String topic = "jr_log_monitor";
        TopologyBuilder builder = new TopologyBuilder();
//        final MetaClientConfig metaClientConfig = new MetaClientConfig();
//        final ZkUtils.ZKConfig zkConfig = new ZkUtils.ZKConfig();
        // 设置zookeeper地址
//        zkConfig.zkConnect = zkAddress;
//        metaClientConfig.setZkConfig(zkConfig);
//        builder.setSpout("spout", new MetaSpout(metaClientConfig,new ConsumerConfig( group ), new StringScheme()), 10);
        //设置spout
        builder.setSpout("spout", new RandomSpout(new StringScheme()),2);
        builder.setBolt("process", new RtsMonitorBolt(), 10).localOrShuffleGrouping("spout"); //160
        Config conf = new Config();
        conf.put(Config.NIMBUS_HOST, "localhost");   /*指定nimbus的host*/
        conf.put(Config.NIMBUS_THRIFT_PORT, "");   /*指定nimbus的port*/
        // Set the consume topic
//        conf.put(MetaSpout.TOPIC, topic);
        conf.setDebug(true);
//        conf.put(MetaSpout.FETCH_MAX_SIZE, 1024*1024);
        conf.setNumWorkers(1);
        conf.setMaxSpoutPending(1000);
        // 任务永不过期
        conf.setMessageTimeoutSecs(2147483647);
        try {
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
