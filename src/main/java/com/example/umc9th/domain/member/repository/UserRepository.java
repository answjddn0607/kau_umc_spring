package com.example.umc9th.domain.member.repository;

import com.example.umc9th.domain.member.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

}