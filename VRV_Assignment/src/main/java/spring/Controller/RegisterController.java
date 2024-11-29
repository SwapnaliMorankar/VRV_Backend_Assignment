package spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

import spring.Model.Student;
import spring.Service.RegisterService;

@RestController
@RequestMapping("/api/register")
@CrossOrigin(origins = "http://localhost:5173")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<Map<String, String>> registerStudent(@RequestBody Student student) {
        // Call the service to register the user
        registerService.registerStudent(student);
        
        // Prepare a response map with a success message
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student registered successfully!");
        
        // Return the response as JSON
        return ResponseEntity.ok(response);
    }
}
