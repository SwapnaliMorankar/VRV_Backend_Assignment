package spring.Service;

import spring.Dao.StudentDao;
import spring.Model.Student;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImple implements StudentService {

    private final StudentDao studentDao;

    public StudentServiceImple(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    @Override
    public void deleteStudentById(Long id) {
        studentDao.deleteStudentById(id);
    }
    @Override
    public void updateStudent(Student student) {
        studentDao.updateStudent(student);
    }
    


}
