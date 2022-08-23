package com.sparta.cloneteam2backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    private String userUsername;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = false)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    private Authority authority;


    @Builder
    public User (String username, String nickname, String password, Authority authority) {
        this.userUsername = username;
        this.userNickname = nickname;
        this.userPassword = password;
        this.authority = authority;
    }
}