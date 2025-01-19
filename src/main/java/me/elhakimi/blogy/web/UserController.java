package me.elhakimi.blogy.web;

import lombok.AllArgsConstructor;
import me.elhakimi.blogy.domain.AppUser;
import me.elhakimi.blogy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String email) {
        if(userService.saveUser(email) != null) {
            return ResponseEntity.ok("User registered successfully");
        }

        return ResponseEntity.badRequest().body("User already exists");

    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String email, @RequestParam String code) {
        if(userService.verifyCode(email, code) != null) {
            return ResponseEntity.ok("User verified successfully");
        }

        return ResponseEntity.badRequest().body("Invalid code or code expired");
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resend(@RequestParam String email) {
        if(userService.resendCode(email) != null) {
            return ResponseEntity.ok("Activation code sent successfully");
        }

        return ResponseEntity.badRequest().body("User does not exist");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email) {
        String user = userService.login(email);
        if(user != null) {
            return ResponseEntity.ok(user );
        }
        return ResponseEntity.badRequest().body("Invalid email or password");
    }

}
