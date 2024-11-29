package spring.Service;

import spring.Dao.AssignmentDao;
import spring.Model.Assignment;
import spring.Model.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignmentServiceImple implements AssignmentService {

    @Autowired
    private AssignmentDao assignmentDao;

    @Override
    @Transactional
    public void createAssignment(Assignment assignment) {
        this.assignmentDao.saveAssignment(assignment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Assignment> getAssignmentsByStudent(Student student) {
        return this.assignmentDao.findByStudent(student);
    }
}
