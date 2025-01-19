package me.elhakimi.blogy.service;

import lombok.AllArgsConstructor;
import me.elhakimi.blogy.domain.AppUser;
import me.elhakimi.blogy.repository.UserRepository;
import me.elhakimi.blogy.utils.RandomTextUtil;
import me.elhakimi.blogy.utils.SendEmail;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final SendEmail sendEmail;
    private final RandomTextUtil randomTextUtil;

    public AppUser saveUser(String email) {

            AppUser checkUser = userRepository.findUserByEmail(email);
            if (checkUser != null) {
                throw new IllegalArgumentException("User with email already exists.");
            }
            else {
                AppUser appUser = new AppUser();
                Random random = new Random();
                int code = random.nextInt(999999);
                String StringCode = String.valueOf(code);
                appUser.setEmail(email);

                appUser.setActivationCode(StringCode);
                appUser.setActivationCodeExpiresAt(LocalDateTime.now().plusMinutes(10));

                appUser.setUserCreated_at(LocalDateTime.now());

                sendEmail.sendActivationEmail(appUser.getEmail(), "Activation Code", "Your activation code is: " + StringCode);
                return userRepository.save(appUser);

            }

    }

    public AppUser verifyCode(String email, String code) {

        AppUser appUser = userRepository.findUserByEmail(email);
        if (appUser == null) {
            throw new IllegalArgumentException("User with email does not exist.");
        }

        if (appUser.getActivationCode().equals(code) && appUser.getActivationCodeExpiresAt().isAfter(LocalDateTime.now())) {
            appUser.setActivated(true);
            appUser.setActivationCode(null);
            appUser.setActivationCodeExpiresAt(null);
            return userRepository.save(appUser);
        }

        throw new IllegalArgumentException("Invalid code or code expired.");

    }

    public AppUser resendCode(String email) {

        AppUser appUser = userRepository.findUserByEmail(email);
        if (appUser == null) {
            throw new IllegalArgumentException("User with email does not exist.");
        }

        Random random = new Random();
        int code = random.nextInt(999999);
        String StringCode = String.valueOf(code);
        appUser.setActivationCode(StringCode);
        appUser.setActivationCodeExpiresAt(LocalDateTime.now().plusMinutes(10));

        sendEmail.sendActivationEmail(appUser.getEmail(), "Activation Code", "Your activation code is: " + StringCode);
        return userRepository.save(appUser);

    }

    public String login (String email) {
        AppUser user =  userRepository.findUserByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("User with email does not exist.");
        }

        if(!user.isActivated()) {
            throw new IllegalArgumentException("User is not activated.");
        }

        user.setLoginCode(email+ "_" +RandomTextUtil.generateRandomText(8)+ "_" +LocalDate.now());
        user.setActivationCodeExpiresAt(LocalDateTime.now().plusHours(24));

        userRepository.save(user);

        return user.getLoginCode();

    }

    public AppUser findUserByLoginCode(String loginCode) {
        AppUser user = userRepository.findUsersByLoginCode(loginCode);
        if (user == null) {
            throw new IllegalArgumentException("User with login code does not exist.");
        }
        return user;
    }

}
