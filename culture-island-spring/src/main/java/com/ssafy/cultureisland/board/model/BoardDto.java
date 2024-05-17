package com.ssafy.cultureisland.board.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDto {
    private int articleNo;
    private int memberId;
    private String subject;
    private String content;
    private int hit;
    private String registerTime;
//    private List<FileInfoDto> fileInfos;
}
