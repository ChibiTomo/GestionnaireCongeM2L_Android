package data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.util.SparseArray;

public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<Conge> conges;
	private SparseArray<Map<Type, Solde>> soldes;

	public Employee() {
		soldes = new SparseArray<Map<Type, Solde>>();
	}

	public Conge createConge(Type type) {
		return new Conge(type, this);
	}

	public Conge createConge(Type type, Date start, Date end) {
		return new Conge(type, this, start, end);
	}

	public void addConge(Conge conge) {
		if (!conges.contains(conge)) {
			conges.add(conge);
		}
	}

	public Collection<Conge> getConges() {
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

	public SparseArray<Map<Type, Solde>> getSoldes() {
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
}
