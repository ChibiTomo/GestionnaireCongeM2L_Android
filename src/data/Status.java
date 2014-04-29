package data;

import java.util.HashMap;

public enum Status {
	PENDING(1), ACCEPTED(2), REFUSED(3);

	private static HashMap<Integer, Status> map = new HashMap<Integer, Status>(
			3);

	static {
		for (Status type : Status.values()) {
			map.put(type.id, type);
		}
	}
	int id;

	private Status(int id) {
		this.id = id;
	}

	public static Status get(int id) {
		return map.get(id);
	}
}