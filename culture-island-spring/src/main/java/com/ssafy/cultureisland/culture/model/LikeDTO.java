package com.ssafy.cultureisland.culture.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {
    private int id;
    private int memberId;
    private String cultureTitle;
    private String cultureDate;
    private String cultureCodename;
    private String mainImg;
}
