package stackjava.hibernate.java.mssql.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import stackjava.hibernate.java.mssql.entity.Employee;
import stackjava.hibernate.java.mssql.util.HibernateUtils;

public class EmployeeSession {
	private Session session;

	public EmployeeSession() {
		super();
		session = HibernateUtils.getSessionFactory().openSession();
	}

	public Long save(Employee employee) {
		try {
			session.getTransaction().begin();
			session.save(employee);
			session.getTransaction().commit();
			return employee.getId();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Employee> findAll() {
		try {
			return session.createCriteria(Employee.class).addOrder(Order.desc("name")).list();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Employee findById(Long id) {
		try {
			return session.find(Employee.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Employee> findByName(String name) {
		try {
			return session.createCriteria(Employee.class).add(Restrictions.like("fullName", "%" + name + "%")).list();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Employee update(Employee employee) {
		try {
			session.getTransaction().begin();
			Employee result = findById(employee.getId());
			if (result != null) {
				session.update(employee);
			}
			session.getTransaction().commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Long delete(Long id) {
		try {
			session.getTransaction().begin();
			Employee employee = findById(id);
			if (employee != null) {
				session.delete(employee);
			}
			session.getTransaction().commit();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}
}
