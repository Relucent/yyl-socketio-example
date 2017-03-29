package yyl.springboot.socketio.component;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

import yyl.springboot.socketio.bean.Message;
import yyl.springboot.socketio.bean.Person;
import yyl.springboot.socketio.repository.PersonRepository;

@Component
public class MessageEventHandler {

	public static final String PERSON_ID = "personId";

	@Autowired
	private SocketIOServer server;

	@Autowired
	private PersonRepository personRepository;

	/** 客户端发起连接 */
	@OnConnect
	public void onConnect(SocketIOClient client) {
		Long personId = Long.parseLong(client.getHandshakeData().getSingleUrlParam(PERSON_ID));
		Person person = personRepository.getById(personId);

		System.out.println("onConnect->" + personId);

		if (person == null) {
			return;
		}

		person.setConnected(true);
		person.setSessionId(client.getSessionId());
		personRepository.save(person);
	}

	/** 客户端断开连接 */
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		Long personId = Long.parseLong(client.getHandshakeData().getSingleUrlParam(PERSON_ID));

		System.out.println("onDisconnect->" + personId);

		Person person = personRepository.getById(personId);
		if (person == null) {
			return;
		}
		person.setConnected(false);
		person.setSessionId(null);
		personRepository.save(person);
	}

	/** 接收到消息后 */
	@OnEvent(value = "messageEvent")
	public void onEvent(SocketIOClient client, AckRequest request, Message data) {
		Long to = data.getTo();
		Person person = personRepository.getById(to);

		System.out.println("onEvent{from[" + data.getFrom() + "] >> to[" + data.getTo() + "] -> " + data.getContent() + "}");

		if (person != null && person.isConnected()) {
			UUID uuid = person.getSessionId();
			Message sendData = new Message();
			sendData.setTo(data.getTo());
			sendData.setFrom(data.getFrom());
			sendData.setContent(data.getContent());
			client.sendEvent("messageEvent", sendData);
			server.getClient(uuid).sendEvent("messageEvent", sendData);
		}
	}
}
