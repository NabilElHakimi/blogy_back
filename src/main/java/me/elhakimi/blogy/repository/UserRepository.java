package me.elhakimi.blogy.repository;

import me.elhakimi.blogy.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findUserByEmail(String email);
    AppUser findAppUsersByLoginCode(String loginCode);

}
