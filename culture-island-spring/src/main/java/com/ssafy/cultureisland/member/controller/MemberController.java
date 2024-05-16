package com.ssafy.cultureisland.member.controller;

import com.ssafy.cultureisland.member.MemberDTO;
import com.ssafy.cultureisland.member.model.ResponseDTO;
import com.ssafy.cultureisland.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/member")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResultCode("s0001");
        responseDTO.setRes(memberService.findAll());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        MemberDTO member = memberService.findById(id);
        
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> joinMember(@RequestBody MemberDTO memberDTO) throws Exception {
        try {
            memberService.joinMember(memberDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable int id) throws Exception {
        memberService.deleteMember(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
