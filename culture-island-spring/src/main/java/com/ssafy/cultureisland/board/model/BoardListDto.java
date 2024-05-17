package com.ssafy.cultureisland.board.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardListDto {
    private List<BoardDto> articles;
    private int currentPage;
    private int totalPageCount;
}
