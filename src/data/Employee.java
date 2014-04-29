package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Conge> conges;
	private Map<Integer, Map<Type, Solde>> soldes;

	private String lastname;

	private String firstname;

	private int id;

	Employee() {
		conges = new ArrayList<Conge>();
		soldes = new HashMap<Integer, Map<Type, Solde>>();
	}

	public Conge createConge(Type type) {
		Conge conge = new Conge(type, this);
		addConge(conge);
		return conge;
	}

	public Conge createConge(Type type, Date start, Date end) {
		Conge conge = new Conge(type, this, start, end);
		addConge(conge);
		return conge;
	}

	private void addConge(Conge conge) {
		if (!conges.contains(conge)) {
			conges.add(conge);
		}
	}

	public List<Conge> getConges() {
		return conges;
	}

	public void putSolde(Solde solde) {
		if (solde == null) {
			return;
		}

		Map<Type, Solde> yearSoldes = soldes.get(solde.getYear());
		if (yearSoldes == null) {
			yearSoldes = new HashMap<Type, Solde>();
			soldes.put(solde.getYear(), yearSoldes);
		}

		yearSoldes.put(solde.getType(), solde);
	}

	public Solde getSolde(int year, Type type) {
		Map<Type, Solde> yearSoldes = soldes.get(year);
		if (yearSoldes == null) {
			return null;
		}

		return yearSoldes.get(type);
	}

	public Map<Integer, Map<Type, Solde>> getSoldes() {
		return soldes;
	}

	public boolean hasEnought(int year, Type type, double askedAmount) {
		Solde soldeN = getSolde(year, type);
		Solde soldeN1 = getSolde(year + 1, type);
		int amount = 0;

		if (soldeN != null) {
			amount += soldeN.getAmount();
		}
		if (soldeN1 != null) {
			amount += soldeN1.getAmount();
		}

		return amount >= askedAmount;
	}

	public String getLastname() {
		return lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public static Employee getTest() {
		Employee emp = new Employee();
		emp.id = 1;
		emp.lastname = "Thomias";
		emp.firstname = "Yannis";

		Conge c1 = new Conge(Type.CP, emp, new Date(1398117600L * 1000),
				new Date(1398290399L * 1000));
		c1.id = 1;
		emp.addConge(c1);

		Conge c2 = new Conge(Type.RTT, emp, new Date(1398290400L * 1000),
				new Date(1398463199L * 1000));
		c2.id = 2;
		c2.setStatus(Status.ACCEPTED);
		emp.addConge(c2);

		Conge c3 = new Conge(Type.CP, emp, new Date(1398549600L * 1000),
				new Date(1398722399L * 1000));
		c3.id = 3;
		c3.setStatus(Status.REFUSED);
		emp.addConge(c3);

		Conge c4 = new Conge(Type.CP, emp, new Date(1398204000L * 1000),
				new Date(1398290340L * 1000));
		c4.id = 4;
		emp.addConge(c4);

		emp.putSolde(new Solde(Type.CP, 2013, 3, emp));
		emp.putSolde(new Solde(Type.CP, 2014, 9, emp));
		emp.putSolde(new Solde(Type.CP, 2015, 20, emp));
		emp.putSolde(new Solde(Type.RTT, 2013, 5, emp));
		emp.putSolde(new Solde(Type.RTT, 2014, 12, emp));
		emp.putSolde(new Solde(Type.RTT, 2015, 10, emp));

		return emp;
	}

	public void remove(Conge conge) {
		conges.remove(conge);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Employee)) {
			return false;
		}
		Employee e = (Employee) obj;
		return id == e.id;
	}

	@Override
	public String toString() {
		return "Employee{ id: " + id + ", lastname: " + lastname
				+ ", firstname: " + firstname + " }";
	}
}
