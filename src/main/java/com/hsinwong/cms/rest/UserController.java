package com.hsinwong.cms.rest;

import com.hsinwong.cms.bean.Role;
import com.hsinwong.cms.bean.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}/roles")
    public List<Role> getUserRoles(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/{id}/roles/{roleId}")
    public Role findRole(@PathVariable Long id, @PathVariable Long roleId) {
        return null;
    }
}
