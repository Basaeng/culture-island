package com.ssafy.cultureisland.board.service;

import com.ssafy.cultureisland.board.model.BoardDto;
import com.ssafy.cultureisland.board.model.BoardListDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface BoardService {
    BoardListDto listArticle(Map<String,String> map) throws Exception;
    void writeArticle(BoardDto boardDto) throws Exception;

}
