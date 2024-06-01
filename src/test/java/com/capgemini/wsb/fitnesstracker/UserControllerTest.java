//NOT REQUIRED - TESTED MANUALLY WITH POSTMAN

//package com.capgemini.wsb.fitnesstracker;
//
//import com.capgemini.wsb.fitnesstracker.user.api.User;
//import com.capgemini.wsb.fitnesstracker.user.internal.UserController;
//import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
//import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
//import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
//import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@ActiveProfiles("loadInitialData")
//class UserControllerTest {
//
//    @Mock
//    private UserServiceImpl userService;
//
//    @Mock
//    private UserMapper userMapper;
//
//    @InjectMocks
//    private UserController userController;
//
//    private MockMvc mockMvc;
//
//    private User user;
//    private UserDto userDto;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//
//        user = new User("Emma", "Johnson", LocalDate.now().minusYears(28), "emma.johnson@domain.com");
//        user.setId(1L);
//        userDto = new UserDto(1L, "Emma", "Johnson", LocalDate.now().minusYears(28), "emma.johnson@domain.com");
//
//        when(userMapper.toDto(any(User.class))).thenReturn(userDto);
//        when(userMapper.toEntity(any(UserDto.class))).thenReturn(user);
//        when(userService.createUser(any(User.class))).thenReturn(user);
//        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(user);
//    }
//
//    @Test
//    void getAllUsers_shouldReturnUsers() throws Exception {
//        when(userService.findAllUsers()).thenReturn(Arrays.asList(user));
//
//        mockMvc.perform(get("/v1/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].email").value("emma.johnson@domain.com"));
//    }
//
//    @Test
//    void getUserById_shouldReturnUser() throws Exception {
//        when(userService.getUser(1L)).thenReturn(Optional.of(user));
//
//        mockMvc.perform(get("/v1/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("emma.johnson@domain.com"));
//    }
//
//    @Test
//    void getUserById_shouldReturnNotFound() throws Exception {
//        when(userService.getUser(2L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/v1/users/2"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void addUser_shouldCreateUser() throws Exception {
//        mockMvc.perform(post("/v1/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthdate\":\"1990-01-01\",\"email\":\"john.doe@example.com\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
//    }
//
//    @Test
//    void deleteUser_shouldDeleteUser() throws Exception {
//        doNothing().when(userService).deleteUser(1L);
//
//        mockMvc.perform(delete("/v1/users/1"))
//                .andExpect(status().isOk());
//
//        verify(userService, times(1)).deleteUser(1L);
//    }
//
//    @Test
//    void searchUsersByEmailPart_shouldReturnUsers() throws Exception {
//        UserEmailDto userEmailDto = new UserEmailDto(1L, "emma.johnson@domain.com");
//        when(userService.searchUsersByEmailPart("johnson")).thenReturn(Arrays.asList(userEmailDto));
//
//        mockMvc.perform(get("/v1/users/search/email?emailPart=johnson"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].email").value("emma.johnson@domain.com"));
//    }
//
//    @Test
//    void findUsersOlderThan_shouldReturnUsers() throws Exception {
//        when(userService.findUsersOlderThan(27)).thenReturn(Arrays.asList(user));
//
//        mockMvc.perform(get("/v1/users/search/age?age=27"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].email").value("emma.johnson@domain.com"));
//    }
//
//    @Test
//    void updateUser_shouldUpdateUser() throws Exception {
//        mockMvc.perform(put("/v1/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthdate\":\"1990-01-01\",\"email\":\"john.doe@example.com\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
//    }
//}
