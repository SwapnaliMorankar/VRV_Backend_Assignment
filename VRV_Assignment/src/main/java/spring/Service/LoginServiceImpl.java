package spring.Service;

import spring.Dao.RegisterDao;
import spring.Model.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class LoginServiceImpl implements LoginService, UserDetailsService {

    @Autowired
    private RegisterDao registerDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean authenticateStudent(Student student) {
        // Check if user exists in DB
        Student existingUser = registerDao.findByEmail(student.getEmail());
        if (existingUser != null && passwordEncoder.matches(student.getPassword(), existingUser.getPassword())) {
            // Set the role of the existing user
            student.setRole(existingUser.getRole());
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = registerDao.findByEmail(email);
        if (student == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(student.getEmail())
                .password(student.getPassword())
                .roles(student.getRole())  // Assume roles are correctly mapped to User entity
                .build();
    }

    @Override
    public Student findStudentByEmail(String email) {
        return registerDao.findByEmail(email);
    }
    

}
