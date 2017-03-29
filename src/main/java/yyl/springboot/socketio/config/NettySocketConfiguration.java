package yyl.springboot.socketio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;

@org.springframework.context.annotation.Configuration
public class NettySocketConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setHostname(environment.getProperty("application.socket.hostname"));
		config.setPort(environment.getRequiredProperty("application.socket.port", Integer.class));
		
		config.setAuthorizationListener(new AuthorizationListener() {
			public boolean isAuthorized(HandshakeData data) {
				//String username = data.getSingleUrlParam("username");
				//String password = data.getSingleUrlParam("password");
				return true;
			}
		});
		final SocketIOServer server = new SocketIOServer(config);
		return server;
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}
}
