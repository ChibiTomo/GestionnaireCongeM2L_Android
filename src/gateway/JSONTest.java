package gateway;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JSONTest {

	public static final Map<String, String> map = new HashMap<String, String>();

	private static final String AUTHENTICATE_RESULT = "auth.json";
	private static final String ADD_DEMAND_RESULT = "add_demand.json";
	private static final String DELETE_DEMAND_RESULT = "delete_demand.json";
	private static final String REFRESH_RESULT = "refresh.json";

	static {
		try {
			BufferedReader reader = null;
			try {
				HashMap<String, String> m = new HashMap<String, String>();
				m.put(Gateway.JSON_ENDPOINT_AUTHENTICATE, AUTHENTICATE_RESULT);
				m.put(Gateway.JSON_ENDPOINT_ADD_DEMAND, ADD_DEMAND_RESULT);
				m.put(Gateway.JSON_ENDPOINT_DELETE_DEMAND, DELETE_DEMAND_RESULT);
				m.put(Gateway.JSON_ENDPOINT_REFRESH, REFRESH_RESULT);

				for (String key : m.keySet()) {
					String filename = m.get(key);
					File file = new File(filename);
					Charset charset = Charset.forName("US-ASCII");
					Path path = FileSystems.getDefault()
							.getPath(file.getPath());
					reader = Files.newBufferedReader(path, charset);

					String str = "";
					String line = null;
					while ((line = reader.readLine()) != null) {
						str += line;
					}
					map.put(key, str);
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
