package activities;

import gateway.Gateway;
import net.yannisthomias.gestionnairecongem2l.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import data.Employee;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		summary(new View(getApplicationContext()));
	}

	public void logout(View view) {
		finish();

		Gateway.removeLocal(getApplicationContext().getFilesDir()
				.getAbsolutePath());
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
	}

	public void summary(View view) {
		setContentView(R.layout.main_layout);

		Gateway gateway = getGateway();
		Employee emp = gateway.getEmployee();
		String empName = emp.getFirstname() + " " + emp.getLastname();

		TextView empNameView = (TextView) findViewById(R.id.employeeName);
		empNameView.setText(empName);
	}

	public void newDemand(View view) {
		setContentView(R.layout.new_demand_layout);
	}

	public void myDemands(View view) {
		setContentView(R.layout.my_demands_layout);
	}

	public void leave(View view) {
		finish();
	}
}
