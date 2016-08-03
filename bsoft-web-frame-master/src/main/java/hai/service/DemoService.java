package hai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import hai.model.Person;
import ctd.annotation.RpcClass;
import ctd.util.annotation.RpcService;

/**
 * @author shenwb
 * @date 2016-3-29
 * @describe
 */
@RpcClass(serviceID = "demoService")
public class DemoService {

	private static List<Person> persons = new ArrayList<Person>();

	@RpcService
	public Person getMyInfo(Map<String, Object> arg) {

		String num = String.valueOf(arg.get("num"));

		List<Person> list = getAllData();
		for (int i = 0; i < list.size(); i++) {
			Person p = list.get(i);
			if (num.equals(p.getNum())) {
				return p;
			}
		}

		return null;
	}

	@RpcService
	public List<Person> getAllData() {
		if (persons == null || persons.size() == 0) {
			List<Person> list = new ArrayList<Person>();
			Person person = new Person();
			person.setNum("001");
			person.setName("小明");
			person.setAge(18);
			person.setDept("");
			person.setTelphone("");
			list.add(person);
			Person person2 = new Person();
			person2.setNum("002");
			person2.setName("小张");
			person2.setAge(19);
			person2.setDept("");
			person2.setTelphone("");
			list.add(person2);
			persons = list;
		}
		return persons;
	}

	@RpcService
	public String saveData(Person arg) {

		for (int i = 0; i < persons.size(); i++) {
			Person p = persons.get(i);
			if (arg.getNum().equals(p.getNum())) {
				p.setName(arg.getName());
				p.setAge(arg.getAge());
				p.setDept(arg.getDept());
				p.setTelphone(arg.getTelphone());

				String str = JSONObject.toJSONString(p);
				return str;
			}
		}
		return null;

	}

}
