package com.company.disaster_response_app.auth_service.service;


import com.company.disaster_response_app.auth_service.dtos.LoginRequestDto;
import com.company.disaster_response_app.auth_service.dtos.SignupRequestDto;
import com.company.disaster_response_app.auth_service.dtos.UserDto;
import com.company.disaster_response_app.auth_service.entity.User;
import com.company.disaster_response_app.auth_service.enums.Role;
import com.company.disaster_response_app.auth_service.exceptions.BadRequestException;
import com.company.disaster_response_app.auth_service.exceptions.ResourceNotFoundException;
import com.company.disaster_response_app.auth_service.repository.UserRepository;
import com.company.disaster_response_app.auth_service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;


    public User signup(SignupRequestDto signupRequestDto){
        if(userRepository.existsByEmail(signupRequestDto.getEmail())){
            throw new BadRequestException("User already exists");
        }

        User user = User.builder()
                .fullName(signupRequestDto.getFullName())
                .email(signupRequestDto.getEmail())
                .password(PasswordUtils.hashPassword(signupRequestDto.getPassword()))
                .role(Role.INDIVIDUAL)
                .build();
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public String login(LoginRequestDto loginRequestDto){
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("User not found with email: "+loginRequestDto.getEmail()));

        if(!PasswordUtils.checkPassword(loginRequestDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("Incorrect password");
        }

        return jwtService.generateAccessToken(user);
    }
}
