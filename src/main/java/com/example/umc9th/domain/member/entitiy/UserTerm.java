package com.example.umc9th.domain.member.entitiy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_terms")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_term_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id")
    private Term term;

    @Column(name = "agreed_at", nullable = false)
    private LocalDateTime agreedAt = LocalDateTime.now();
}
