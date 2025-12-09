package com.mysite.sbb.member.entity;

import com.mysite.sbb.audit.BaseTimeEntity;
import com.mysite.sbb.member.constant.Department;
import com.mysite.sbb.member.constant.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department; // 학과

    @Column(nullable = false)
    private Boolean active; // 등록 여부
}
