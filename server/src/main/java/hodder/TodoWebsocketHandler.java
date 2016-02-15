package hodder;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class TodoWebsocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        Application.sessions.add(session);
        Application.update(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int something, String somethingElse) {
        Application.sessions.remove(session);
    }
}
