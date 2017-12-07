package com.kingthy.server;


import com.alibaba.fastjson.JSON;
import com.kingthy.dto.ServiceNode;
import com.kingthy.zk.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author likejie
 * @date 2017/11/1
 */
@Component
public class ZkServiceRegister {

    private void register(ServiceNode node) {
        if (node.isPersistent()) {
            ZkClient.I.createPersistNode(node.getPath(), JSON.toJSONString(node));
        } else {
            ZkClient.I.createEphemeralNode(node.getPath(), JSON.toJSONString(node));
        }
    }
    /**注册服务到zookeeper**/
    public void registerZookeeper(){

        /**注册主节点（永久节点）**/
        ServiceNode parentNode=new ServiceNode();
        parentNode.setPersistent(true);
        parentNode.setName("netty-server");
        parentNode.setPath(NettyConstants.ZK_CLUSTER_PATH);
        register(parentNode);

        /**注册子节点（临时节点）**/
        String ip=getInetAddress(true);
        ServiceNode childNode=new ServiceNode();
        childNode.setNodeId(UUID.randomUUID().toString());
        childNode.setHost(ip);
        childNode.setPersistent(false);
        childNode.setName("netty-server:"+ip);
        childNode.setPath(NettyConstants.ZK_CLUSTER_PATH+"/"+ ip);
        register(childNode);
    }
    /**
     * 只获取第一块网卡绑定的ip地址
     *
     * @param getLocal 局域网IP
     * @return ip
     */
    public static String getInetAddress(boolean getLocal) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                Enumeration<InetAddress> addresses = interfaces.nextElement().getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address.isLoopbackAddress()){
                        continue;
                    }
                    if (address.getHostAddress().contains(":")){
                        continue;
                    }
                    if (getLocal) {
                        if (address.isSiteLocalAddress()) {
                            return address.getHostAddress();
                        }
                    } else {
                        if (!address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
                            return address.getHostAddress();
                        }
                    }
                }
            }
            return getLocal ? "127.0.0.1" : null;
        } catch (Throwable e) {
            return getLocal ? "127.0.0.1" : null;
        }
    }
}
