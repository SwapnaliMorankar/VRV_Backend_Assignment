package spring.Controller;

import spring.Model.Assignment;
import spring.Model.Student;
import spring.Service.JWTUtility;
import spring.Service.LoginService;
import spring.Service.AssignmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/assignments")
@CrossOrigin(origins = "http://localhost:5173") // Update for production
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private LoginService loginService;

    // Helper method to validate the token
    private ResponseEntity<?> validateToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or Invalid Token");
        }

        String jwtToken = token.substring(7); // Remove "Bearer " part
        if (!jwtToken.contains(".")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Malformed JWT Token");
        }

        String email = jwtUtility.extractEmail(jwtToken);
        Student student = loginService.findStudentByEmail(email);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        return null; // Token is valid
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAssignment(@RequestBody Assignment assignment, @RequestHeader("Authorization") String token) {
        // Validate the token
        ResponseEntity<?> tokenValidationResponse = validateToken(token);
        if (tokenValidationResponse != null) {
            return tokenValidationResponse; // Return the error response if token is invalid
        }

        // Extract the email from the token
        String email = jwtUtility.extractEmail(token.substring(7));
        Student student = loginService.findStudentByEmail(email);

        // Associate the user with the task
        assignment.setUser(student);
        assignmentService.createAssignment(assignment); // Persist the task in the database

        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAssignments(@RequestHeader("Authorization") String token) {
        // Validate the token
        ResponseEntity<?> tokenValidationResponse = validateToken(token);
        if (tokenValidationResponse != null) {
            return tokenValidationResponse; // Return the error response if token is invalid
        }

        // Extract the email from the token
        String email = jwtUtility.extractEmail(token.substring(7));
        Student student = loginService.findStudentByEmail(email);

        // Retrieve tasks for the user
        List<Assignment> assignments = assignmentService.getAssignmentsByStudent(student);
        return ResponseEntity.ok(assignments);
    }
}
