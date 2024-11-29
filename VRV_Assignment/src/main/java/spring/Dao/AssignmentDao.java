package spring.Dao;

import java.util.List;

import spring.Model.Assignment;
import spring.Model.Student;

public interface AssignmentDao {
    void saveAssignment(Assignment assignment);              // Create or update a assignment
    List<Assignment> findByStudent(Student student);  // Retrieve assignments by student
}
