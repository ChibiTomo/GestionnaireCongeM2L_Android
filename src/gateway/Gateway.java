package gateway;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import data.Conge;
import data.Employee;

public abstract class Gateway implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String HOST_URL = "http://192.168.0.22/GestionnaireCongeM2L";
	private static final String ENDPOINT_ROOT = HOST_URL + "/endpoints";

	private static final String JSON_ROOT = ENDPOINT_ROOT + "/json";
	// private static final String SOAP_ROOT = ENDPOINT_ROOT + "/soap";

	private static final String ENDPOINT_AUTHENTICATE = "/auth.php";
	private static final String ENDPOINT_ADD_DEMAND = "/add_demand.php";
	private static final String ENDPOINT_DELETE_DEMAND = "/delete_demand.php";
	private static final String ENDPOINT_REFRESH = "/refresh.php";

	protected static final String JSON_ENDPOINT_AUTHENTICATE = JSON_ROOT
			+ ENDPOINT_AUTHENTICATE;
	protected static final String JSON_ENDPOINT_ADD_DEMAND = JSON_ROOT
			+ ENDPOINT_ADD_DEMAND;
	protected static final String JSON_ENDPOINT_DELETE_DEMAND = JSON_ROOT
			+ ENDPOINT_DELETE_DEMAND;
	protected static final String JSON_ENDPOINT_REFRESH = JSON_ROOT
			+ ENDPOINT_REFRESH;

	protected static final String SAVE_FILE = "save_file.sav";

	protected Employee employee;
	protected String errorMessage = "";
	protected boolean inError = false;

	private static Gateway gateway = null;
	private String rootDir = "";

	public enum TYPE {
		SOAP, JSON;
	}

	public static Gateway createGateway(TYPE type) {
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

	public boolean isInError() {
		return inError;
	}

	protected void setError(String message) {
		inError = true;
		errorMessage = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void clearError() {
		inError = false;
		errorMessage = "";
	}

	public void setRootDir(String path) {
		rootDir = path;
	}

	public String getRootDir() {
		return rootDir;
	}

	public void saveLocal() {
		try {
			ObjectOutputStream oos = null;
			try {
				File file = new File(getRootDir(), SAVE_FILE);
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(this);
				oos.flush();
			} finally {
				if (oos != null) {
					oos.close();
				}
			}
		} catch (Exception e) {
			setError("Error while saving to file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static Gateway loadLocal(String rootDir) {
		try {
			ObjectInputStream ois = null;
			try {
				File file = new File(rootDir, SAVE_FILE);
				if (!file.exists()) {
					return null;
				}
				FileInputStream fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				Gateway gateway = (Gateway) ois.readObject();

				return gateway;
			} finally {
				if (ois != null) {
					ois.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void removeLocal(String rootDir) {
		File file = new File(rootDir, SAVE_FILE);
		file.delete();
	}

	@Override
	public String toString() {
		return "Gateway " + this.getClass().getSimpleName() + "{employee: "
				+ employee + ", inError: " + inError + ", errorMessage: "
				+ errorMessage + ", rootDir: " + rootDir + "}";
	}

	public abstract boolean authenticate(String login, String password);

	public abstract boolean refresh();

	public abstract boolean sendDemand(Conge conge);

	public abstract boolean deleteDemand(Conge conge);

}
