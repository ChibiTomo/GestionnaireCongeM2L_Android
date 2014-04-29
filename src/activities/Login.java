package activities;

import gateway.Gateway;
import net.yannisthomias.gestionnairecongem2l.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
	}

	public void authenticate(View view) {
		EditText loginView = (EditText) findViewById(R.id.login);
		String login = loginView.getText().toString();
		EditText pwdView = (EditText) findViewById(R.id.password);
		String password = pwdView.getText().toString();
		Gateway gateway = getGateway();
		gateway.authenticate(login, password);
		if (gateway.authenticate(login, password)) {
			gateway.saveLocal();
			Intent intent = new Intent(this, StartActivity.class);
			finish();
			startActivity(intent);
		} else {
			TextView errorView = (TextView) findViewById(R.id.errorMessage);
			errorView.setText(gateway.getErrorMessage());
			gateway.clearError();
		}
	}
}
