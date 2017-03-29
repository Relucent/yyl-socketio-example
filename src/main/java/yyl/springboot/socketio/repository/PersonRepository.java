package yyl.springboot.socketio.repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import yyl.springboot.socketio.bean.Person;

@Repository
public class PersonRepository implements InitializingBean {

	private ConcurrentMap<Long, Person> persons = new ConcurrentHashMap<>();

	public Person getById(Long id) {
		return clone(persons.get(id));
	}

	public void save(Person person) {
		if (person != null) {
			persons.put(person.getId(), person);
		}
	}

	public void afterPropertiesSet() throws Exception {
		innerPerson(1L, "用户壹");
		innerPerson(2L, "用户贰");
		innerPerson(3L, "用户叁");
		innerPerson(4L, "用户肆");
		innerPerson(5L, "用户伍");
		innerPerson(6L, "用户陆");
	}

	protected void innerPerson(Long id, String name) {
		Person person = new Person();
		person.setId(id);
		person.setName(name);
		persons.putIfAbsent(id, person);
	}

	protected Person clone(Person person) {
		if (person == null) {
			return null;
		}
		Person copy = new Person();
		copy.setId(person.getId());
		copy.setName(person.getName());
		copy.setConnected(person.isConnected());
		copy.setSessionId(person.getSessionId());
		return copy;
	}
}
