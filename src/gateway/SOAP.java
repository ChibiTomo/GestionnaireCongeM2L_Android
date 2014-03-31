package gateway;

import data.Conge;

class SOAP extends Gateway {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean authenticate(String login, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendDemand(Conge conge) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteDemand(Conge conge) {
		// TODO Auto-generated method stub
		return false;
	}
}
