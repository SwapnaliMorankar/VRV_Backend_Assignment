package spring.Service;

import java.util.List;

import spring.Model.Assignment;
import spring.Model.Student;

public interface AssignmentService {
    void createAssignment(Assignment assignment);  // Method to create a new Assignment
    List<Assignment> getAssignmentsByStudent(Student student);  // Method to Assignment tasks by Student
}
