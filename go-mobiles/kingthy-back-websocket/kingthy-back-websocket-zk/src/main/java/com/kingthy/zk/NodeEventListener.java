package com.kingthy.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 节点事件监听
 * @author   likejie on 2017/10/25.
 */
public final class NodeEventListener implements CuratorListener {
    @Override
    public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
        System.out.println(event.toString() + ".......................");
        final WatchedEvent watchedEvent = event.getWatchedEvent();
        if (watchedEvent != null) {
            System.out.println(watchedEvent.getState() + "=======================" + watchedEvent.getType());
            if (watchedEvent.getState() == KeeperState.SyncConnected) {
                switch (watchedEvent.getType()) {
                    case NodeCreated:
                        // TODO
                        System.out.println("该节点已经创建！");
                        break;
                    case NodeChildrenChanged:
                        // TODO
                        break;
                    case NodeDataChanged:
                        // TODO
                        break;
                    default:
                        break;
                }
                System.out.println("注册zookeeper成功！");
            }
        }
    }
}
