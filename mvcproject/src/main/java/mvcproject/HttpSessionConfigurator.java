package mvcproject;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

public class HttpSessionConfigurator extends Configurator{

	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest req, HandshakeResponse res) {
		// TODO Auto-generated method stub
		HttpSession session = (HttpSession)req.getHttpSession();
		ServletContext context = session.getServletContext();
		config.getUserProperties().put("session", session);
		config.getUserProperties().put("context", context);
	}

}
