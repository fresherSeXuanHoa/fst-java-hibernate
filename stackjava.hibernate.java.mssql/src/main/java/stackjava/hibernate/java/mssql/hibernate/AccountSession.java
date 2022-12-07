package stackjava.hibernate.java.mssql.hibernate;

import org.hibernate.Session;

import stackjava.hibernate.java.mssql.entity.Account;
import stackjava.hibernate.java.mssql.util.HibernateUtils;

public class AccountSession {
	private Session session;

	public AccountSession() {
		super();
		session = HibernateUtils.getSessionFactory().openSession();
	}

	public Long save(Account account) {
		try {
			session.getTransaction().begin();
			session.save(account);
			session.getTransaction().commit();
			return account.getId();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Account findById(Long id) {
		try {
			return session.find(Account.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return null;
	}

	public Account update(Account account) {
		try {
			session.getTransaction().begin();
			Account result = findById(account.getId());
			if (result != null) {
				session.update(account);
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
			Account account = findById(id);
			if (account != null) {
				session.delete(account);
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
