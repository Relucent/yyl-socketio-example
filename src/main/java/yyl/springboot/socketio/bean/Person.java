package yyl.springboot.socketio.bean;

import java.util.UUID;

public class Person {

	private Long id;
	private String name;
	private boolean connected;
	private UUID sessionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public UUID getSessionId() {
		return sessionId;
	}

	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}
}
