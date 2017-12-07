package com.kingthy.zk;

import com.kingthy.dto.ServiceNode;


/**
 * 描述：节点连接侦听
 * @author  likejie
 * @date 2017/10/26.
 */
public class ConnServerNodeListener implements ServiceListener {
    @Override
    public void onServiceAdded(String path, ServiceNode node) {
        refresh();
    }

    @Override
    public void onServiceUpdated(String path, ServiceNode node) {
        refresh();
    }

    @Override
    public void onServiceRemoved(String path, ServiceNode node) {
        refresh();
    }
    /**
     * 从zk中获取可提供服务的机器,并以在线用户量排序
     */
    private void refresh() {
        //1.从缓存中拿取可用的长链接服务器节点
        /*List<ServiceNode> nodes = discovery.lookup(ServiceNames.CONN_SERVER);
        if (nodes.size() > 0) {
            //2.对serverNodes可以按某种规则排序,以便实现负载均衡,比如:随机,轮询,链接数量等
            this.serverNodes = nodes
                    .stream()
                    .map(this::convert)
                    .sorted(ServiceNode::compareTo)
                    .collect(Collectors.toList());
        }*/
    }
}
