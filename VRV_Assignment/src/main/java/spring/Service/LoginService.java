package spring.Service;

import org.springframework.security.core.userdetails.UserDetails;

import spring.Model.Student;

public interface LoginService {
    boolean authenticateStudent(Student student);
    UserDetails loadUserByUsername(String email);
    Student findStudentByEmail(String email); // To fetch a Student entity directly
}
