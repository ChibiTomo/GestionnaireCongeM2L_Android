package data;

import java.io.Serializable;
import java.util.Date;

public class Conge implements Serializable {
	private static final long serialVersionUID = 1L;

	enum Status {
		ACCEPTED, REFUSED, PENDING;
	}

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
		amount /= 1000 * 60 * 60 * 24; // convert millisecond to day
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
}
