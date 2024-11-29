package spring.Dao;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import spring.Model.Student;

import java.util.List;

@Repository
public class StudentDaoImple implements StudentDao {

    private final HibernateTemplate hibernateTemplate;

    public StudentDaoImple(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public List<Student> getAllStudents() {
        return hibernateTemplate.loadAll(Student.class);
    }

    @Override
    public void deleteStudentById(Long id) {
        Student student = hibernateTemplate.get(Student.class, id);
        if (student != null) {
            hibernateTemplate.delete(student);
        }
    }
    
    public void updateStudent(Student student) {
        hibernateTemplate.update(student);
    }
    
   

}
