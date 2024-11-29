package spring.Dao;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import spring.Model.Student;

@Repository
public class RegisterDaoImpl implements RegisterDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    @Transactional
    public void saveStudent(Student student) {
        hibernateTemplate.save(student);
    }
    
    @Override
    public Student findByEmail(String email) {
        return (Student) hibernateTemplate.execute(session -> {
            return session.createQuery("FROM Student WHERE email = :email", Student.class)
                          .setParameter("email", email)
                          .uniqueResult();
        });
    }

}


