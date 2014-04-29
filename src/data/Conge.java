package data;

import java.io.Serializable;
import java.util.Date;

public class Conge implements Serializable {
	private static final long serialVersionUID = 1L;

	Integer id;
	private Type type;
	private Status status;
	private Date start;
	private Date end;
	private Employee employee;

	Conge(Type type, Employee employee) {
		this.type = type;
		this.employee = employee;
		status = Status.PENDING;
	}

	Conge(Type type, Employee employee, Date start, Date end) {
		this(type, employee);
		this.start = start;
		this.end = end;
	}

	public double getAmount(Date start, Date end) {
		double amount = end.getTime() - start.getTime();
		amount /= 60 * 60 * 24 * 1000; // convert millisecond to day
		return Math.ceil(amount * 100) / 100;
	}

	public double getAmount() {
		return getAmount(getStart(), getEnd());
	}

	public Type getType() {
		return type;
	}

	void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	void setStart(Date start) {
		if (start == null) {
			return;
		}
		Date end = getEnd();
		@SuppressWarnings("deprecation")
		int year = start.getYear() + 1900;
		if (end != null
				&& (start.compareTo(end) > 0 || !employee.hasEnought(year,
						getType(), getAmount(start, end)))) {
			return;
		}

		this.start = start;
	}

	public Date getStart() {
		return start;
	}

	void setEnd(Date end) {
		if (end == null) {
			return;
		}
		Date start = getEnd();
		@SuppressWarnings("deprecation")
		int year = end.getYear() + 1900;
		if (start != null
				&& (start.compareTo(end) > 0 || !employee.hasEnought(year,
						getType(), getAmount(start, end)))) {
			return;
		}

		this.end = end;
	}

	public Date getEnd() {
		return end;
	}

	public Integer getID() {
		return id;
	}

	void setID(Integer id) {
		this.id = id;
	}

	public void delete() {
		employee.remove(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Conge)) {
			return false;
		}
		Conge c = (Conge) obj;

		boolean isIDCorrect = false;
		if (id == null && c.id == null) {
			isIDCorrect = true;
		} else if (id != null && c.id != null && id.equals(c.id)) {
			isIDCorrect = true;
		}
		return isIDCorrect && type.compareTo(c.type) == 0
				&& end.getTime() == c.end.getTime()
				&& start.getTime() == c.start.getTime()
				&& employee.equals(c.employee);
	}

	@Override
	public String toString() {
		return "Conge { id:" + id + ", type: " + type + ", status: " + status
				+ ", start: " + start.getTime() + ", end: " + end.getTime()
				+ ", employee: " + employee + " }";
	}
}
