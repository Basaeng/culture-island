package com.ssafy.cultureisland.member.model;

import com.ssafy.cultureisland.board.model.BoardDto;
import com.ssafy.cultureisland.member.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface MemberMapper {

    List<HashMap<String, Object>> findAll();

    void joinMember(MemberDTO member);

    void deleteMember(int id);

    MemberDTO findById(int id);

    MemberDTO findByUsername(String username);

    List<BoardDto> getMyArticleList(int memberId);
}
