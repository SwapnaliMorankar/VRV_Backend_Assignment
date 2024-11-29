package spring.Controller;

import spring.Model.Student;

import spring.Service.JWTUtility;
import spring.Service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JWTUtility jwtUtility;

    // Helper method to validate the token
    private ResponseEntity<?> validateToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or Invalid Token");
        }

        String jwtToken = token.substring(7); // Remove "Bearer " part
        String email = jwtUtility.extractEmail(jwtToken);

        if (email == null || jwtUtility.isTokenExpired(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is expired or invalid");
        }

        // Add logic to check if the email belongs to an admin
        String role = jwtUtility.extractRole(jwtToken);
        if (!"ROLE_ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        return null; // Token is valid
    }

    // Get all students
    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents(@RequestHeader("Authorization") String token) {
        ResponseEntity<?> tokenValidationResponse = validateToken(token);
        if (tokenValidationResponse != null) {
            return tokenValidationResponse; // Return the error response if token is invalid
        }

        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // Delete a student by ID
    @DeleteMapping("/students/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        ResponseEntity<?> tokenValidationResponse = validateToken(token);
        if (tokenValidationResponse != null) {
            return tokenValidationResponse; // Return the error response if token is invalid
        }

        studentService.deleteStudentById(id);
        return ResponseEntity.ok("Student with ID " + id + " deleted successfully.");
    }

    // Update student details
    @PutMapping("/students/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student updateStudent, @RequestHeader("Authorization") String token) {
        ResponseEntity<?> tokenValidationResponse = validateToken(token);
        if (tokenValidationResponse != null) {
            return tokenValidationResponse; // Return the error response if token is invalid
        }

        Student existingUser = studentService.getAllStudents()
                                       .stream()
                                       .filter(user -> user.getId().equals(id))
                                       .findFirst()
                                       .orElse(null);

        if (existingUser == null) {
            return ResponseEntity.status(404).body("Student with ID " + id + " not found.");
        }

        // Update user details
        existingUser.setName(updateStudent.getName());
        existingUser.setEmail(updateStudent.getEmail());
        existingUser.setRole(updateStudent.getRole());

        studentService.updateStudent(existingUser);
        return ResponseEntity.ok("Student with ID " + id + " updated successfully.");
    }
}
