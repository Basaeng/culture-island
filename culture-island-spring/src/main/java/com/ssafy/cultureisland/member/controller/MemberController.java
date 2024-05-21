package com.ssafy.cultureisland.member.controller;

import com.ssafy.cultureisland.board.model.BoardDto;
import com.ssafy.cultureisland.board.model.BoardListDto;
import com.ssafy.cultureisland.board.service.BoardService;
import com.ssafy.cultureisland.member.AuthRequest;
import com.ssafy.cultureisland.member.AuthResponse;
import com.ssafy.cultureisland.member.MemberDTO;
import com.ssafy.cultureisland.member.model.ResponseDTO;
import com.ssafy.cultureisland.member.service.MemberService;
import com.ssafy.cultureisland.util.jwtutil.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "*" }, maxAge = 60000)
@RestController
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public MemberController(MemberService memberService, BoardService boardService, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.memberService = memberService;
        this.boardService = boardService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResultCode("s0001");
        responseDTO.setRes(memberService.findAll());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        MemberDTO member = memberService.findById(id);
        
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMemberDetails(@RequestHeader HttpHeaders headers) {
        String currentUserName = memberService.getAuthenticUsername(headers);
        System.out.println("currentName: "+ currentUserName);
        MemberDTO member = memberService.findByUsername(currentUserName);
        System.out.println("member: " + member);
        if (member == null) {
            return new ResponseEntity<>(new ResponseDTO(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(member);
    }

    @GetMapping("/myarticles")
    public ResponseEntity<?> articleListById(@RequestParam Map<String, String> map, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String username = userDetails.getUsername(); // JWT에서 추출된 사용자 ID
            MemberDTO member = memberService.findByUsername(username);
            String memberId = String.valueOf(member.getId());
            System.out.println("memberId:" + memberId);
            map.put("memberId", memberId);
            BoardListDto boardListDto = boardService.listArticle(map);
            System.out.println(boardListDto);
            return ResponseEntity.ok().body(boardListDto);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Error : " + e.getMessage());
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        System.out.println(authRequest);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            String token = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

//    @GetMapping("/myarticles")
//    public ResponseEntity<?> getMyArticles(@RequestHeader HttpHeaders headers) {
//
//        String currentUserName = memberService.getAuthenticUsername(headers);
//        System.out.println("currentName: "+ currentUserName);
//        MemberDTO member = memberService.findByUsername(currentUserName);
//
//        if (member == null) {
//            return new ResponseEntity<>(new ResponseDTO(), HttpStatus.UNAUTHORIZED);
//        }
//
//        try {
//            List<BoardDto> articleList = memberService.getMyArticleList(member.getId());
//            System.out.println(articleList);
//            return new ResponseEntity<>(articleList, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new ResponseDTO(), HttpStatus.UNAUTHORIZED);
//        }
//    }

}
