package data;

import java.io.Serializable;

class Solde implements Serializable {
	private static final long serialVersionUID = 1L;

	private Type type;
	private int year;
	private int amount;

	public Solde(Type type, int year, int amount) {
		this.type = type;
		this.year = year;
		this.amount = amount;
	}

	public Type getType() {
		return type;
	}

	public int getYear() {
		return year;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Solde)) {
			return false;
		}

		Solde otherSolde = (Solde) o;
		return getType().equals(otherSolde.getType())
				&& getYear() == otherSolde.getYear();
	}
}
