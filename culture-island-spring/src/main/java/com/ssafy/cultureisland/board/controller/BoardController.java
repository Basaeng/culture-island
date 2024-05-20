package com.ssafy.cultureisland.board.controller;

import com.ssafy.cultureisland.board.model.BoardDto;
import com.ssafy.cultureisland.board.model.BoardListDto;
import com.ssafy.cultureisland.board.model.CommentDto;
import com.ssafy.cultureisland.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/board")
public class BoardController {

    private BoardService boardService;

    public BoardController(@Autowired BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<?> articleList(@RequestParam Map<String, String> map) {
        try {
            BoardListDto boardListDto = boardService.listArticle(map);
            return ResponseEntity.ok().body(boardListDto);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> writeArticle(@RequestBody BoardDto boardDto) {
        try {
            boardService.writeArticle(boardDto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/{articleNo}")
    public ResponseEntity<?> getArticle(@PathVariable("articleNo") int articleNo) {
        try {
            boardService.updateHit(articleNo);
            BoardDto article = boardService.getArticle(articleNo);
            return new ResponseEntity<BoardDto>(article, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @DeleteMapping("/{articleNo}")
    public ResponseEntity<?> deleteArticle(@PathVariable("articleNo") int articleNo) {
        try {
            boardService.deleteArticle(articleNo);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<?> writeComment(@RequestBody CommentDto commentDto) {
        try {
            System.out.println(commentDto);
            boardService.writeComment(commentDto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/comment/{articleNo}")
    public ResponseEntity<?> listComment(@PathVariable("articleNo") int articleNo) {
        try {
            List<CommentDto> articleList = boardService.listComment(articleNo);
            System.out.println(articleList);
            return new ResponseEntity<List<CommentDto>>(articleList, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateArticle(@RequestBody BoardDto boardDto) {
        try {
            boardService.modifyArticle(boardDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
