package me.elhakimi.blogy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String loginCode ;
    private LocalDateTime loginCodeExpiresAt ;
    private String activationCode ;
    private LocalDateTime activationCodeExpiresAt ;
    private LocalDateTime userCreated_at ;
    private boolean isActivated ;

}
