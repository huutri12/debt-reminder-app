package com.daykemit.service;

import com.daykemit.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public ResponseEntity findAll() {
        return ResponseEntity.ok(repository.findAll());
    }
}
