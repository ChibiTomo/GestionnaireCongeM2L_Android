package activities;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();
		Intent intent;
		if (getGateway().isLogged()) {
			intent = new Intent(this, Summary.class);
		} else {
			intent = new Intent(this, Login.class);
		}
		startActivity(intent);
	}

	private void init() {
		// TODO Auto-generated method stub
	}
}
