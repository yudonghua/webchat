package websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.servlet.http.HttpSession;

import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;
import net.sf.json.JSONObject;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */


@ServerEndpoint(value="/websocket",configurator=GetHttpSessionConfigurator.class)
public class WebSocketTest {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();
    private static Map<String,Session> map= new HashMap<String,Session>();
    private static String online=null;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private HttpSession httpSession;
    

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @param config
     * @throws java.io.IOException
     */
    @OnOpen
    public void onOpen(Session session,EndpointConfig config) throws IOException{
        this.httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        map.put(this.httpSession.getAttribute("username").toString(), session);
        System.out.println( this.httpSession.getAttribute("username"));
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        online="online:";
        for (String in : map.keySet()) {
        //    String str = map.get(in);//得到每个key多对用value的值
            online+=" "+in;
        }
        for(WebSocketTest item: webSocketSet){
            item.sendMessage(online);
        }
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     * @throws java.io.IOException
     */
    @OnClose
    public void onClose() throws IOException{
        webSocketSet.remove(this);  //从set中删除
        map.remove(this.httpSession.getAttribute("username").toString());
        subOnlineCount(); 
        online="online:";
        for (String in : map.keySet()) {
        //    String str = map.get(in);//得到每个key多对用value的值
            online+=" "+in;
        }
        for(WebSocketTest item: webSocketSet){
            item.sendMessage(online);
        }
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     * @throws java.io.IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("来自客户端的消息:" + message);
        JSONObject jsonObject = JSONObject.fromObject(message);
        if(jsonObject.get("to")!=null){
            String to = jsonObject.get("to").toString();
            String mess = jsonObject.get("mess").toString();
            Session fs;
            if((fs=map.get(to))!=null){
                System.out.println("发送成功");
                fs.getBasicRemote().sendText(message);
                jsonObject.replace("name", "to:"+jsonObject.get("to").toString());
                session.getBasicRemote().sendText(jsonObject.toString());
            }else{
                jsonObject.replace("name", "to:"+jsonObject.get("to").toString());
                jsonObject.replace("mess", "发送失败:"+jsonObject.get("mess").toString());
                System.out.println(jsonObject.toString());
                session.getBasicRemote().sendText(jsonObject.toString());
            }
        }else{
            for(WebSocketTest item: webSocketSet){
                try {
                    item.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        
        //群发消息
        
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }
    
}