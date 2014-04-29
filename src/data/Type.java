package data;

import java.util.HashMap;

public enum Type {
	CP(1), RTT(2);

	private static HashMap<Integer, Type> map = new HashMap<Integer, Type>();

	static {
		for (Type type : Type.values()) {
			map.put(type.id, type);
		}
	}
	int id;

	private Type(int id) {
		this.id = id;
	}

	public static Type get(int id) {
		return map.get(id);
	}

	public int toInt() {
		return id;
	}
}