package gateway;

import java.io.Serializable;

import data.Conge;
import data.Employee;

public abstract class Gateway implements Serializable {
	private static final long serialVersionUID = 1L;

	private Employee employee;

	private static Gateway gateway = null;

	public enum TYPE {
		SOAP, JSON;
	}

	protected Gateway() {
	}

	public static Gateway getGateway() {
		return gateway;
	}

	public static Gateway getGateway(TYPE type) {
		if (gateway != null
				&& type.name().equals(gateway.getClass().getSimpleName())) {
			return gateway;
		}
		switch (type) {
			case JSON:
				gateway = new JSON();
				break;
			case SOAP:
				gateway = new SOAP();
				break;
		}
		return gateway;
	}

	public Employee getEmployee() {
		return employee;
	}

	public boolean isLogged() {
		return employee != null;
	}

	public void saveData() {
		// TODO: Save Employee in a file.
	}

	public abstract boolean authenticate(String login, String password);

	public abstract boolean sendDemand(Conge conge);

	public abstract boolean deleteDemand(Conge conge);

}
