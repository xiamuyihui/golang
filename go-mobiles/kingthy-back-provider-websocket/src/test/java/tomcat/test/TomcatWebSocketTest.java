package tomcat.test;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 最大连接数测试
 * Created by likejie on 2017/6/13.
 */
@ClientEndpoint
public class TomcatWebSocketTest {

    // 线程池对象
    private static ExecutorService threadpool = Executors.newFixedThreadPool(10);
    private String deviceId;

    private Session session;
    private String uri;

    public TomcatWebSocketTest() {

    }

    public TomcatWebSocketTest(String uri, String deviceId) {
        this.deviceId = deviceId;
        this.uri=uri;
    }

    protected boolean start() {
        WebSocketContainer Container = ContainerProvider.getWebSocketContainer();
        System.out.println("Connecting to " + uri);
        try {
            session = Container.connectToServer(TomcatWebSocketTest.class, URI.create(uri));
            System.out.println("count: " + deviceId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void main(String[] args) {

        //线程1
        threadpool.execute(new Runnable()
        {

            @Override
            public void run()
            {
                System.out.println("线程一开始执行！");
                String uri1 = "ws://localhost:8123/websocket-pclogin";
                for (int i = 1; i < 50000; i++) {
                    TomcatWebSocketTest wSocketTest = new TomcatWebSocketTest(uri1,String.valueOf(i));
                    if (!wSocketTest.start()) {

                        System.out.println("启动uri1失败！");
                        break;
                    }
                }
                System.out.println("线程一执行完毕！");
            }
        });
        //线程2
        threadpool.execute(new Runnable()
        {

            @Override
            public void run()
            {
                System.out.println("线程二开始执行！");
                String uri2 = "ws://localhost:8123/websocket-pclogin";
                for (int i = 1; i < 50000; i++) {
                    TomcatWebSocketTest wSocketTest = new TomcatWebSocketTest(uri2,String.valueOf(i));
                    if (!wSocketTest.start()) {
                        System.out.println("启动uri2失败！");
                        break;
                    }
                }
                System.out.println("线程二执行完毕！");
            }
        });

        //经过测试最大连接数10000

    }
}