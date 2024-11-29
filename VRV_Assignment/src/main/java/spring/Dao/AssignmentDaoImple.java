package spring.Dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.Model.Assignment;
import spring.Model.Student;

import java.util.List;

@Repository
public class AssignmentDaoImple implements AssignmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveAssignment(Assignment assignment) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(assignment); // Save a new task or update an existing one
    }

    @Override
    public List<Assignment> findByStudent(Student student) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Assignment a WHERE a.student = :student";
        return session.createQuery(hql, Assignment.class)
                      .setParameter("student", student)
                      .getResultList();
    }
}
