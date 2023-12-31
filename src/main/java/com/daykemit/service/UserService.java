package com.daykemit.service;


import com.daykemit.entity.Role;
import com.daykemit.entity.User;
import com.daykemit.entity.UserPrinciple;
import com.daykemit.entity.dto.UserDTO;
import com.daykemit.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    public static List<User> listUser = new ArrayList<>();
    public static List<Role> listRole = new ArrayList<>();

    public UserService() {
        listRole = roleRepository.findAll();

        String password = "$2a$10$xMq9EwZvdKUuvgiaM2T1Iuw9A1EGXVZaCIUPEwn1Isa9ffvPqNabe";
        User userKai = new User(1L, "kai", password);
        Set<Role> roleKai = new HashSet<>();
        roleKai.add(listRole.get(0));
        userKai.setRoles(roleKai);
        User userSena = new User(2L, "sena", password);
        Set<Role> roleSena = new HashSet<>();
        roleSena.add(listRole.get(1));
        userSena.setRoles(roleSena);
        listUser.add(userKai);
        listUser.add(userSena);
    }

    public List<UserDTO> findAll() {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User u : listUser) {
            userDTOS.add(toDTO(u));
        }
        return userDTOS;
    }

    public UserDTO findById(Long id) {
        for (User user : listUser) {
            if (Objects.equals(user.getId(), id)) {
                return toDTO(user);
            }
        }
        return null;
    }

    public User findByUsername(String username) {
        for (User user : listUser) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }
        return null;
    }

    public boolean add(User user) {
        for (User userExist : listUser) {
            if (Objects.equals(user.getId(), userExist.getId()) || Objects.equals(user.getUsername(), userExist.getUsername())) {
                return false;
            }
        }
        listUser.add(user);
        return true;
    }

    public void delete(int id) {
        listUser.removeIf(user -> user.getId() == id);
    }

    public UserDetails loadUserByUsername(String username) {
        for (User user : listUser) {
            if (Objects.equals(user.getUsername(), username)) {
                return UserPrinciple.build(user);
            }
        }
        return null;
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getRoles());
    }
}
