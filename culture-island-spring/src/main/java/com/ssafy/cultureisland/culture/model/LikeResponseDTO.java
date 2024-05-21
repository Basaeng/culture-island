package com.ssafy.cultureisland.culture.model;

import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseDTO {
    private boolean isExists;
    private int id;
}
