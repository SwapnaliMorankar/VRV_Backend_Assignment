package spring.Dao;

import spring.Model.Student;

public interface RegisterDao {
    void saveStudent(Student student);
    Student findByEmail(String email);

}
