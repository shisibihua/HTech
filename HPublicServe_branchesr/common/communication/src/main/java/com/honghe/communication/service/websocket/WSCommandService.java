package com.honghe.communication.service.websocket;

import com.honghe.communication.ioc.CommandIOC;
import com.honghe.communication.ioc.Context;
import com.honghe.communication.message.MessageFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.UUID;


/**
 * webSocket 服务
 * User: zhanghongbin
 * Date: 14-9-17
 * Time: 上午9:56
 * To change this template use File | Settings | File Templates.
 */

@ServerEndpoint(value = "/cloud/WSCommandService")
public class WSCommandService {

    private String id;
    private String mt = null;
    private boolean multiCast = false;

    public WSCommandService(){

    }
    @OnOpen
    public void open(Session session) {
        String q = session.getQueryString();
//        if(q != null && !q.equals("")){
//             String[]params = q.split("&");
//
//        }
        //if (this.mt == null) {
            this.mt = MessageFactory.Type.JSON.name();
        //}
        this.id = UUID.randomUUID().toString();
        WSSessionPool.INSTANCE().add(this.id, session);
    }


    @OnClose
    public void close() {
        WSSessionPool.INSTANCE().remove(this.id);
    }


    @OnMessage
    public void rec(String message, Session session) {
        String responseMessage = CommandIOC.execute(new Context(Context.Type.WEBSOCKET, session), mt, message);
            if (responseMessage != null) {
                if (multiCast) {
                    MessageWriterFactory.getInstance(MessageWriterFactory.Type.WS).send(responseMessage);
                } else {
                    MessageWriterFactory.getInstance(MessageWriterFactory.Type.WS).send(id, responseMessage);
                }
            }
    }




    @OnError
    public void onError(Throwable t) throws Throwable {
        WSSessionPool.INSTANCE().remove(this.id);
    }
}

//    @Override
//    protected StreamInbound createWebSocketInbound(String s, HttpServletRequest httpServletRequest) {
//        String userId = httpServletRequest.getSession().getId();
//        String mt = httpServletRequest.getParameter("mt");//消息类型
//        String multiCast = httpServletRequest.getParameter("multiCast");//多
//
//        return new WebSocketMessageInbound(userId, mt,multiCast);
//    }


//    private class WebSocketMessageInbound extends MessageInbound {
//
//        private String id;
//        private String mt;
//        private boolean multiCast;
//
//        public WebSocketMessageInbound(String id, String mt,String multiCast) {
//            this.id = id;
//            this.mt = mt;
//            if (this.mt == null) {
//                this.mt = MessageFactory.Type.JSON.name();
//            }
//            if (multiCast == null || multiCast.equals("true")) {
//                this.multiCast = false;
//            } else {
//                this.multiCast = true;
//            }
//        }
//
//        @Override
//        protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {
//
//        }
//
//        @Override
//        protected void onTextMessage(CharBuffer charBuffer) throws IOException {
//            String responseMessage = CommandIOC.execute(new Context(Context.Type.WEBSOCKET, this), mt, charBuffer.toString());
//            if (responseMessage != null) {
//                if (multiCast) {
//                    MessageWriterFactory.getInstance(MessageWriterFactory.Type.WS).send(responseMessage);
//                } else {
//                    MessageWriterFactory.getInstance(MessageWriterFactory.Type.WS).send(id, responseMessage);
//                }
//            }
//        }
//
//        /**
//         * webSocket 连接
//         *
//         * @param outbound
//         */
//        @Override
//        protected void onOpen(WsOutbound outbound) {
//            WSSessionPool.INSTANCE().add(this.id, this);
//        }
//
//        /**
//         * webSocket 关闭
//         *
//         * @param status
//         */
//        @Override
//        protected void onClose(int status) {
//            WSSessionPool.INSTANCE().remove(this.id);
//        }
//    }
//}


