package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.enums.SystemRoleName;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.LoginDto;
import uz.pdp.clickup.payload.RegisterDto;
import uz.pdp.clickup.payload.VerifyEmailDto;
import uz.pdp.clickup.repository.UserRepository;
import uz.pdp.clickup.security.JwtProvider;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        return optionalUser.orElse(null);
    }

    /* * * * * * * *
     * Register User *
     *  * * * * * * * */
    public ApiResponse registerUser(RegisterDto registerDto) {

        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("User already exists!", false);

        User user = new User(
                registerDto.getFullName(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()),
                SystemRoleName.SYSTEM_USER
        );

        String code = (new Random().nextInt(1000) + 100) + "";
        user.setEmailCode(code);

        userRepository.save(user);

        boolean sendEmail = sendEmail(user.getEmail(), code);
        if (!sendEmail)
            return new ApiResponse("Error in email", false);

        return new ApiResponse("User registered!", true);
    }

    public boolean sendEmail(String sendingEmail, String emailCode) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Alisher@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Confirm account!");
            mailMessage.setText(emailCode);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /*
     * Login
     * */
    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            ));

            User principal = (User) authenticate.getPrincipal();

            String token = jwtProvider.generateToken(principal.getUsername());
            return new ApiResponse("Your token: " + token, true);
        } catch (Exception e) {
            return new ApiResponse("username or password is wrong!", false);
        }
    }


    /*
    * VerifyEmail
    * */
    public ApiResponse verifyEmail(VerifyEmailDto verifyEmailDto) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(verifyEmailDto.getEmail(), verifyEmailDto.getCode());
        if (optionalUser.isEmpty())
            return new ApiResponse("Email code is wrong!", false);

        User user = optionalUser.get();
        user.setEmailCode(null);
        user.setEnabled(true);
        userRepository.save(user);
        return new ApiResponse("User confirmed!", true);
    }
}
