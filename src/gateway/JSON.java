package gateway;

import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import data.Conge;
import data.Employee;
import data.Solde;
import data.Status;
import data.Type;

class JSON extends Gateway {
	private static final long serialVersionUID = 1L;

	public static boolean DEBUG = false;

	@Override
	public boolean authenticate(String login, String password) {
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("login", login));
			nvps.add(new BasicNameValuePair("password", password));
			String json = sendRequest(JSON_ENDPOINT_AUTHENTICATE, nvps);
			// System.out.println(json);

			JSONObject json_o = JSONObject.fromObject(json);
			JSONObject empJson = json_o.getJSONObject("employee");
			employee = createEmployee(empJson);

			JSONArray congesJson = empJson.getJSONArray("conges");
			Field congesField = Employee.class.getDeclaredField("conges");
			congesField.setAccessible(true);
			congesField.set(employee,
					json2CongeCollection(employee, congesJson));

			JSONObject soldesJson = empJson.getJSONObject("soldes");
			Field soldesField = Employee.class.getDeclaredField("soldes");
			soldesField.setAccessible(true);
			soldesField.set(employee, json2SoldeMap(soldesJson));
		} catch (Exception e) {
			setError(e.getMessage());
			e.printStackTrace();
		}
		return !isInError() && employee != null;
	}

	@Override
	public boolean refresh() {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", "" + employee.getID()));
		try {
			sendRequest(JSON_ENDPOINT_REFRESH, nvps);
		} catch (Exception e) {
			setError(e.getMessage());
		}
		return !isInError();
	}

	@Override
	public boolean sendDemand(Conge conge) {
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("start_t", ""
					+ conge.getStart().getTime()));
			nvps.add(new BasicNameValuePair("end_t", ""
					+ conge.getEnd().getTime()));
			nvps.add(new BasicNameValuePair("type", ""
					+ conge.getType().toInt()));
			nvps.add(new BasicNameValuePair("employee_id", ""
					+ employee.getID()));

			String json = sendRequest(JSON_ENDPOINT_ADD_DEMAND, nvps);
			JSONObject json_o = JSONObject.fromObject(json);
			int id = json_o.getInt("id");

			Method m = conge.getClass().getDeclaredMethod("setID",
					Integer.class);
			m.setAccessible(true);
			m.invoke(conge, Integer.valueOf(id));
		} catch (Exception e) {
			setError(e.getMessage());
		}
		return !isInError();
	}

	@Override
	public boolean deleteDemand(Conge conge) {
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("id", "" + conge.getID()));
			sendRequest(JSON_ENDPOINT_DELETE_DEMAND, nvps);
			conge.delete();
		} catch (Exception e) {
			setError(e.getMessage());
		}
		return !isInError();
	}

	private String sendRequest(String url, List<NameValuePair> nvps)
			throws Exception {
		if (DEBUG) {
			return testSendRequest(url);
		}

		HttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost(url);
		String json = "";

		try {
			post.setEntity(new UrlEncodedFormEntity(nvps)); // translate then
															// add form

			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();

			StringWriter writer = new StringWriter();
			IOUtils.copy(entity.getContent(), writer, "UTF-8");
			json = writer.toString();
			JSONObject json_o = JSONObject.fromObject(json);
			if (json_o.containsKey("error")) {
				throw new Exception(json_o.getString("error"));
			}
		} catch (Exception e) {
			setError(e.getMessage());
			System.err.println("ERROR: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		return json;
	}

	private String testSendRequest(String url) {
		return JSONTest.map.get(url);
	}

	private Employee createEmployee(JSONObject empJson) throws Exception {
		Constructor<Employee> employeeConstructor = Employee.class
				.getDeclaredConstructor();
		employeeConstructor.setAccessible(true);

		Employee emp = employeeConstructor.newInstance();
		String[] fnames = { "id", "lastname", "firstname" };
		for (String fname : fnames) {
			if (empJson.containsKey(fname)) {
				Field field = Employee.class.getDeclaredField(fname);
				field.setAccessible(true);
				field.set(emp, empJson.get(fname));
			}
		}
		return emp;
	}

	private Map<Integer, Map<Type, Solde>> json2SoldeMap(JSONObject soldesJson)
			throws Exception {
		HashMap<Integer, Map<Type, Solde>> soldes = new HashMap<Integer, Map<Type, Solde>>();

		Iterator<?> years = soldesJson.keys();
		while (years.hasNext()) {
			Integer year = Integer.valueOf((String) years.next());
			JSONObject typeSoldeJson = soldesJson
					.getJSONObject(year.toString());

			Iterator<?> types = typeSoldeJson.keys();
			HashMap<Type, Solde> map = new HashMap<Type, Solde>();
			while (types.hasNext()) {
				String typeID = (String) types.next();
				Type type = Type.get(Integer.parseInt(typeID));
				Constructor<Solde> soldeConstructor = Solde.class
						.getDeclaredConstructor(Type.class, Integer.class,
								Integer.class, Employee.class);
				soldeConstructor.setAccessible(true);
				Solde solde = soldeConstructor.newInstance(type, year,
						typeSoldeJson.getInt(typeID), employee);

				map.put(type, solde);
			}
			soldes.put(year, map);
		}
		return soldes;
	}

	private Collection<Conge> json2CongeCollection(Employee emp,
			JSONArray congesJson) throws Exception {
		ArrayList<Conge> conges = new ArrayList<Conge>();

		for (int i = 0; i < congesJson.size(); i++) {
			JSONObject congeJson = congesJson.getJSONObject(i);
			Conge conge = json2Conge(emp, congeJson);
			conges.add(conge);
		}

		return conges;
	}

	private Conge json2Conge(Employee emp, JSONObject congeJson)
			throws Exception {
		Constructor<Conge> congeConstructor = Conge.class
				.getDeclaredConstructor(Type.class, Employee.class, Date.class,
						Date.class);
		congeConstructor.setAccessible(true);

		Type type = Type.get(congeJson.getInt("type"));
		Date start = new Date(congeJson.getLong("start") * 1000);
		Date end = new Date(congeJson.getLong("end") * 1000);
		Conge conge = congeConstructor.newInstance(type, emp, start, end);

		Field id = Conge.class.getDeclaredField("id");
		id.setAccessible(true);
		id.set(conge, congeJson.getInt("id"));

		Field status = Conge.class.getDeclaredField("status");
		status.setAccessible(true);
		status.set(conge, Status.get(congeJson.getInt("status")));
		return conge;
	}
}
