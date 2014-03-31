package activities;

import gateway.Gateway;
import android.content.Intent;
import android.os.Bundle;

public abstract class Activity extends android.app.Activity {
	private static final String EXTRA_GATEWAY = "net.thomiasyannis.gestionnairecongem2l.GATEWAY";
	private static final Gateway.TYPE TYPE = Gateway.TYPE.JSON;
	private Gateway gateway;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intend = getIntent();
		gateway = (Gateway) intend.getSerializableExtra(EXTRA_GATEWAY);
		if (gateway == null) {
			gateway = Gateway.getGateway(TYPE);
		}
	}

	@Override
	public void startActivity(Intent intent) {
		intent.putExtra(EXTRA_GATEWAY, this.gateway);
		super.startActivity(intent);
	}
}
