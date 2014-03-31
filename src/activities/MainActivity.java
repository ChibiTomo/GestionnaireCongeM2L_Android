package activities;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
	}

	private void init() {
		// TODO Auto-generated method stub

	}
}
