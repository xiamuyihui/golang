package com.kingthy.zk;


import com.google.common.base.Charsets;
import com.kingthy.conf.WsConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 *  注册netty-websockete服务到zookeeper实现集群及负载均衡
 *  @author   likejie  2017/10/25.
 */
public class ZkClient {

    private static final Logger LOG= LoggerFactory.getLogger(ZkClient.class);

    private CuratorFramework client;
    /**
     * 缓存
     */
    private TreeCache cache;

    public static final ZkClient I = getInsatnce();

    private ZkClient() {
        init();
    }
    private synchronized static ZkClient getInsatnce() {
        return I == null ? new ZkClient() : I;
    }
    public void init(){

        try {
            client = CuratorFrameworkFactory.newClient(WsConfig.ZK.getServerAddress(),
                    new ExponentialBackoffRetry(WsConfig.ZK.getRestry().getBaseSleepTimeMs(),WsConfig.ZK.getRestry().getMaxRetries()));
            client.getCuratorListenable().addListener(new NodeEventListener());
            client.start();
            //初始化缓存
            initLocalCache(WsConfig.ZK.getWatchPath());
            LOG.info("zookeeper连接成功！");
        }catch (Exception ex){
            LOG.error(ex.toString());
        }
    }
    /**
     * 判断路径是否存在
     * @param path
     * @return
     */
    public boolean isExisted(final String path) {
        try {
            return null != client.checkExists().forPath(path);
        } catch (Exception ex) {
            LOG.error("isExisted:{}", path, ex);
            throw new RuntimeException(ex);
        }
    }
    /**
     * 创建持久化节点
     *
     * @param path
     * @param value
     */
    public void createPersistNode(final String path, final String value) {
        try {
            if (isExisted(path)) {
                updateNode(path, value);
            } else {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, value.getBytes(Charsets.UTF_8));
            }
        } catch (Exception ex) {
            LOG.error("registerPersist:{},{}", path, value, ex);
            throw new RuntimeException(ex);

        }
    }
    /**
     * 创建临时节点
     *
     * @param path
     * @param value
     */
    public void createEphemeralNode(final String path, final String value) {
        try {
            if (isExisted(path)) {
                deleteNode(path);
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, value.getBytes(Charsets.UTF_8));
        } catch (Exception ex) {
            LOG.error("registerEphemeral:{}", path, ex);
            throw new RuntimeException(ex);
        }
    }
    /**
     * 更新数据
     *
     * @param path
     * @param value
     */
    public void updateNode(final String path, final String value) {
        try {
            client.inTransaction().check().forPath(path).and().setData().forPath(path, value.getBytes(Charsets.UTF_8)).and().commit();
        } catch (Exception ex) {
            LOG.error("registerEphemeral:{}", path, ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * 删除节点
     *
     * @param path
     */
    public void deleteNode(final String path) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception ex) {
            LOG.error("registerEphemeral:{}", path, ex);
            throw new RuntimeException(ex);
        }
    }
    /**
     * 获取子节点
     * @param path
     * @return
     */
    public List<String> getChildrenKeys(final String path) {
        try {
            if (!isExisted(path)) {return Collections.emptyList();}
            List<String> result = client.getChildren().forPath(path);
            result.sort(Comparator.reverseOrder());
            return result;
        } catch (Exception ex) {
            LOG.error("getChildrenKeys:{}", path, ex);
            return Collections.emptyList();
        }
    }
    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @throws Exception
     */
    public void addWatch(String node, boolean isSelf) throws Exception {
        if (isSelf) {
            getClient().getData().watched().forPath(node);
        }
        else {
            getClient().getChildren().watched().forPath(node);
        }
    }


    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @param watcher
     * @throws Exception
     */
    public void addWatch(String node, boolean isSelf, Watcher watcher) throws Exception {
        if (isSelf) {
            getClient().getData().usingWatcher(watcher).forPath(node);
        }
        else {
            getClient().getChildren().usingWatcher(watcher).forPath(node);
        }
    }


    /**
     * 增加监听
     *
     * @param node
     * @param isSelf
     *            true 为node本身增加监听 false 为node的子节点增加监听
     * @param watcher
     * @throws Exception
     */
    public void addWatch(String node, boolean isSelf, CuratorWatcher watcher) throws Exception {
        if (isSelf) {
            getClient().getData().usingWatcher(watcher).forPath(node);
        }
        else {
            getClient().getChildren().usingWatcher(watcher).forPath(node);
        }
    }

    /**
     *
     * 服务发现
     */
    public void subscribe(String path) throws Exception {
        //订阅某一个服务
        createPersistNode(path,path);
        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        // 在初始化的时候就进行缓存监听
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable()
                .addListener((CuratorFramework client, PathChildrenCacheEvent event) -> {
                    // 重新获取子节点
                    List<String> children = client.getChildren().forPath(path);
                    System.out.println("客户端数量"+children.size());
                    // 排序一下子节点
                    Collections.sort(children);
                    // 子节点重新缓存起来
                    //data.put(nodeName, children);
                });
    }
    /**
     *
     *本地缓存
     */
    private void initLocalCache(String watchRootPath) throws Exception {
        cache = new TreeCache(client, watchRootPath);
        cache.start();
    }
    public void registerListener(TreeCacheListener listener) {
        cache.getListenable().addListener(listener);
    }
    /**
     * 销毁资源
     */
    public void destory() {
        if (client != null) {
            client.close();
        }
    }
    /**
     * 获取client
     *
     * @return
     */
    public CuratorFramework getClient() {
        return client;
    }
}
