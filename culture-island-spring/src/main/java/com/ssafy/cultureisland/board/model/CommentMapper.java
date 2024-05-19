package com.ssafy.cultureisland.board.model;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    void writeComment(CommentDto commentDto) throws SQLException;
    List<CommentDto> listComment(int articleNo) throws SQLException;

}
