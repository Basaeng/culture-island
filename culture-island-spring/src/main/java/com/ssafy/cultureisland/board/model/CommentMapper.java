package com.ssafy.cultureisland.board.model;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    void writeComment(CommentDto commentDto) throws SQLException;
//    List<BoardDto> listComment(Map<String,Object> param) throws SQLException;

}
