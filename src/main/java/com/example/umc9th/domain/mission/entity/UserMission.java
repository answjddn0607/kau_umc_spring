package com.example.umc9th.domain.mission.entity;

import com.example.umc9th.domain.member.entitiy.User;
import com.example.umc9th.domain.mission.enums.UserMissionStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_missions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_mission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserMissionStatus status = UserMissionStatus.IN_PROGRESS;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
