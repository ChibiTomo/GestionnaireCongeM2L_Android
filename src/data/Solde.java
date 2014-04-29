package data;

import java.io.Serializable;

public class Solde implements Serializable {
	private static final long serialVersionUID = 1L;

	private Type type;
	private int year;
	private int amount;
	private Employee employee;

	Solde(Type type, Integer year, Integer amount, Employee employee) {
		this.type = type;
		this.year = year;
		this.amount = amount;
		this.employee = employee;
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

	public Employee getEmployee() {
		return employee;
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
