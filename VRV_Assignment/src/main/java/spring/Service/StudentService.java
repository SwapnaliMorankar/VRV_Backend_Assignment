package spring.Service;
import java.util.List;

import spring.Model.Student;

public interface StudentService {
    List<Student> getAllStudents();
    void deleteStudentById(Long id);
    void updateStudent(Student student);
    
}

