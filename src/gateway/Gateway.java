package gateway;

import java.io.Serializable;

import data.Employee;

public abstract class Gateway implements Serializable {
	private static final long serialVersionUID = 1L;

	private Employee employee;

	public enum TYPE {
		SOAP, JSON;
	}

	public static Gateway getGateway(TYPE type) {
		Gateway gateway = null;
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

}
