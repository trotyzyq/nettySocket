这是一个基于netty实现的socket工具包，主要是为了解决socket客户端和服务端协作的问题。
### 第一步: 引入jar包
[下载链接](http://www.insistself.cn:9002/oss/downloadFile?path=/ossFile/dc5c5580-9566-4bc7-9d9b-fb1426fccffc-2018-12-25%2009:42:23.jar)


### 第二步: 根目录配置文件(命名为netty.properties)
```
nettyWebsocket.port=6789
```

### 第三步: 定义Rooter 继承HandlerRooter
```
@Component
public class SRooter extends HandlerRooter {

    public void fullHandlerMap() {
        handlerMap.put("0000",  new LoginHandler());
    }

    public void fullMethodMap() {
        methodMap.put("0000","login");
    }
}
```
LoginHandler就是需要你处理的业务类，需要实现Handler接口。    
login是你需要调用的方法
如：
```
public class LoginHandler implements Handler {
    public void login(ChannelHandlerContext ctx, String req) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        System.out.println("登录");
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        TextWebSocketFrame tws = new TextWebSocketFrame(timestamp.toString());
                        Global.group.writeAndFlush(tws);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
```

### 第四步：提交你的业务数据
以json数据格式提交{"xId":"00000000","userName":"zyq",password:"123456"}
注意：这边的xId是写死的，如果需要改变，可以下载源码后修改。同样的，也可以将它变为两个字段进行
传输。（很简单的源码）