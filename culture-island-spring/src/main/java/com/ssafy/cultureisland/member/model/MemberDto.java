package com.ssafy.cultureisland.member.model;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString @AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String number;

    private Date birth;
    private String gender;
    private String rank;

    private String imglink;
    private String intro;
}
