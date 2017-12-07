package com.kingthy.conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.impl.ConfigBeanImpl;

import java.io.File;
import java.util.concurrent.TimeUnit;


/**
 * netty配置
 * Created by likejie on 2017/10/27.
 */
public interface NConfg {

    Config cfg = load();

    static Config load() {
        try {
            Config config = ConfigFactory.load();//扫描加载所有可用的配置文件
            String custom_conf = "mp.conf";//加载自定义配置, 值来自jvm启动参数指定-Dmp.conf
            if (config.hasPath(custom_conf)) {
                File file = new File(config.getString(custom_conf));
                if (file.exists()) {
                    Config custom = ConfigFactory.parseFile(file);
                    config = custom.withFallback(config);
                }
            }
            return config;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    /**
     * 消息推送服务总配置
     */
    interface  server {

        Config cfg = NConfg.cfg.getObject("server").toConfig();

        String host= cfg.getString("host");

        int port=cfg.getInt("port");

        boolean isCluster=cfg.getBoolean("isCluster");

        int max_heartbeat = cfg.getInt("max-heartbeat");

        /*int session_expired_time = (int) cfg.getDuration("session-expired-time").getSeconds();

        int max_heartbeat = (int) cfg.getDuration("max-heartbeat", TimeUnit.MILLISECONDS);

        int max_packet_size = (int) cfg.getMemorySize("max-packet-size").toBytes();

        int min_heartbeat = (int) cfg.getDuration("min-heartbeat", TimeUnit.MILLISECONDS);

        long compress_threshold = cfg.getBytes("compress-threshold");

        int max_hb_timeout_times = cfg.getInt("max-hb-timeout-times");

        String epoll_provider = cfg.getString("epoll-provider");*/

        /**
         * zookeeper配置
         */
        interface zk{

            Config cfg = server.cfg.getObject("zk").toConfig();

            //int sessionTimeoutMs = (int) cfg.getDuration("sessionTimeoutMs", TimeUnit.MILLISECONDS);

            String watch_path = cfg.getString("watch-path");

            //int connectionTimeoutMs = (int) cfg.getDuration("connectionTimeoutMs", TimeUnit.MILLISECONDS);

            //String namespace = cfg.getString("namespace");

            //String digest = cfg.getString("digest");

            String server_address = cfg.getString("server-address");

            interface retry {

                Config cfg = zk.cfg.getObject("retry").toConfig();

                int maxRetries = cfg.getInt("maxRetries");

                int baseSleepTimeMs = (int) cfg.getDuration("baseSleepTimeMs", TimeUnit.MILLISECONDS);

                int maxSleepMs = (int) cfg.getDuration("maxSleepMs", TimeUnit.MILLISECONDS);
            }
        }
        /**
         * redis配置
         */
       interface redis{
            Config cfg = server.cfg.getObject("redis").toConfig();
            /*String password = cfg.getString("password");
            String clusterModel = cfg.getString("cluster-model");
            String sentinelMaster = cfg.getString("sentinel-master");

            static boolean isCluster() {
                return "cluster".equals(clusterModel);
            }
            static boolean isSentinel() {
                return "sentinel".equals(clusterModel);
            }
            static <T> T getPoolConfig(Class<T> clazz) {
                return ConfigBeanImpl.createInternal(cfg.getObject("config").toConfig(), clazz);
            }*/
        }
    }


}
