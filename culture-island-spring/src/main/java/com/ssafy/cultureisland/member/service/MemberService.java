package com.ssafy.cultureisland.member.service;

import com.ssafy.cultureisland.member.MemberDTO;
import com.ssafy.cultureisland.member.model.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    MemberMapper memberMapper;

    public List<HashMap<String, Object>> findAll() {
        return memberMapper.findAll();
    }

    public void joinMember(MemberDTO member) throws Exception{
        memberMapper.joinMember(member);
    }

    public void deleteMember(int id) {
        memberMapper.deleteMember(id);
    }

    public MemberDTO findById(int id) {
        return memberMapper.findById(id);
    }
}
