package spring.Dao;

import java.util.List;

import spring.Model.Student;

public interface StudentDao {
    List<Student> getAllStudents();
    void deleteStudentById(Long id);
    void updateStudent(Student student);
}

