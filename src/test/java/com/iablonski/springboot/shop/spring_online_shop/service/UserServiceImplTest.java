package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.repository.UserRepository;
import com.iablonski.springboot.shop.spring_online_shop.entity.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.UserDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class UserServiceImplTest {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl userService;
    private MailSenderService mailSenderService;

    @BeforeEach
    public void setUp() {
        System.out.println("Before each test");
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userRepository = Mockito.mock(UserRepository.class);
        mailSenderService = Mockito.mock(MailSenderService.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder, mailSenderService);
    }

    @Test
    public void checkFindByName() {
        //have
        String name = "Alex";
        User expectedUser = User.builder().id(1L).name(name).build();
        System.out.println(expectedUser);
        Mockito.when(userRepository.findFirstByName(Mockito.anyString())).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findUserByName(name);
        System.out.println(actualUser);

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void checkFindByNameExact() {
        //have
        String name = "Alex";
        User expectedUser = User.builder().id(1L).name(name).build();

        Mockito.when(userRepository.findFirstByName(Mockito.eq(name))).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findUserByName(name);
        User rndUser = userService.findUserByName(UUID.randomUUID().toString());

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);

        Assertions.assertNull(rndUser);

    }

    @Test
    public void checkSaveIncorrectPassword() {
        //have
        UserDTO userDto = UserDTO.builder()
                .password("password")
                .passwordConfirmation("another")
                .build();

        //execute
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.save(userDto);
            }
        });

    }

    @Test
    public void checkSave() {
        //have
        UserDTO userDto = UserDTO.builder()
                .username("name")
                .email("email")
                .password("password")
                .passwordConfirmation("password")
                .build();

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        //execute
        boolean result = userService.save(userDto);

        //check
        Assertions.assertTrue(result);
        Mockito.verify(passwordEncoder).encode(Mockito.anyString());
        Mockito.verify(userRepository).save(Mockito.any());
    }
}
