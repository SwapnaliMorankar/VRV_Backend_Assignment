package spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.Model.Student;
import spring.Service.JWTUtility;
import spring.Service.LoginService;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:5173") // React app origin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JWTUtility jwtUtility;

    @PostMapping
    public ResponseEntity<String> loginStudent(@RequestBody Student student) {
        boolean isAuthenticated = loginService.authenticateStudent(student);
        
        if (isAuthenticated) {
            // Generate JWT Token
            String token = jwtUtility.generateToken(student.getEmail(), student.getRole());

            // Return response with the token
            return ResponseEntity.ok("Login successful! Token: " + token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
}
