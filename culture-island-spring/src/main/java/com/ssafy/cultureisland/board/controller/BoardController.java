package com.ssafy.cultureisland.board.controller;

import com.ssafy.cultureisland.board.model.BoardDto;
import com.ssafy.cultureisland.board.model.BoardListDto;
import com.ssafy.cultureisland.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping(value="board")
public class BoardController {

    private BoardService boardService;

    public BoardController(@Autowired BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<?> articleList(@RequestParam Map<String,String> map) {
        try {
            BoardListDto boardListDto = boardService.listArticle(map);
            return ResponseEntity.ok().body(boardListDto);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/{articleNo}")
    public ResponseEntity<BoardDto> getArticle(@PathVariable("articlNo") int articleNo) {
//        boardService.

        return null;
    }


    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
