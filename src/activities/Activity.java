package activities;

import gateway.Gateway;
import android.content.Intent;
import android.os.Bundle;

public abstract class Activity extends android.app.Activity {
	protected static final String EXTRA_GATEWAY = "net.thomiasyannis.gestionnairecongem2l.GATEWAY";
	protected static final String EXTRA_EMPLOYEE = "net.thomiasyannis.gestionnairecongem2l.EMPLOYEE";

	private static final Gateway.TYPE GATEWAY_TYPE = Gateway.TYPE.JSON;
	private Gateway gateway;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intend = getIntent();
		gateway = (Gateway) intend.getSerializableExtra(EXTRA_GATEWAY);
		if (gateway == null) {
			gateway = Gateway.getGateway(GATEWAY_TYPE);
		}
	}

	@Override
	public void startActivity(Intent intent) {
		intent.putExtra(EXTRA_GATEWAY, gateway);
		super.startActivity(intent);
	}

	public Gateway getGateway() {
		return gateway;
	}
}
