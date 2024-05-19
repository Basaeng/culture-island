package com.ssafy.cultureisland.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {
    private int commentNo;
    private int parentNo;
    private int depth;
    private String comment;
    private String registerTime;
    private int articleNo;
    private int memberNo;
    private String name;

}
