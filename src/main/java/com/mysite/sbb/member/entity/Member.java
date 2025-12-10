package com.mysite.sbb.member.entity;

import com.mysite.sbb.audit.BaseTimeEntity;
import com.mysite.sbb.member.constant.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;    // ID

    @Column(unique = true, nullable = false)
    private String username; // 이름

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(unique = true, nullable = false)
    private String email; // 이메일

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender; // 성별

    private String profileImage;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean active; // 등록 여부

    //    private String naverId; 고민 중


}

