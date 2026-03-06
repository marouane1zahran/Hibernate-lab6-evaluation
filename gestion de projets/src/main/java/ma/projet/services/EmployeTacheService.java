package ma.projet.services;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.EmployeTacheKey;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {

    @Override
    public boolean create(EmployeTache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(EmployeTache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(EmployeTache o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public EmployeTache findById(int id) {
        return null;
    }


    public EmployeTache findById(Employe employe, Tache tache) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        EmployeTacheKey key = new EmployeTacheKey();
        key.setEmployeId(employe.getId());
        key.setTacheId(tache.getId());

        EmployeTache et = session.get(EmployeTache.class, key);
        session.close();
        return et;
    }

    @Override
    public List<EmployeTache> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<EmployeTache> liste = session.createQuery("from EmployeTache", EmployeTache.class).list();
        session.close();
        return liste;
    }
}