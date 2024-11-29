package spring.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import spring.Dao.RegisterDao;
import spring.Model.Student;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterDao registerDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void registerStudent(Student student) {
        // Encrypt password
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        // Set default role
        student.setRole("ROLE_USER");

        // Save user
        registerDao.saveStudent(student);
    }
}
