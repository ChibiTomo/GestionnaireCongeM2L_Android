package activities;

import gateway.Gateway;
import net.yannisthomias.gestionnairecongem2l.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
	}

	public void authenticate(View view) {
		// TODO: Get login and password.
		String login = null;
		String password = null;
		Gateway gateway = getGateway();
		if (gateway.authenticate(login, password)) {
			Intent intent = new Intent(this, Summary.class);
			intent.putExtra(EXTRA_EMPLOYEE, gateway.getEmployee());
			startActivity(intent);
		} else {
			// TODO: Not connected. error message.
		}
	}
}
