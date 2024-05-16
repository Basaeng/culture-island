package com.ssafy.cultureisland.member;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString @AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private int id;
    private String email;
    private String password;
    private String name;
    private String number;

    private Date birth;
    private int gender;
    private int memberRank;

    private String imglink;
    private String intro;
}
