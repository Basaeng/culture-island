package com.ssafy.cultureisland.board.controller;

import com.ssafy.cultureisland.board.model.BoardDto;
import com.ssafy.cultureisland.board.model.BoardListDto;
import com.ssafy.cultureisland.board.model.CommentDto;
import com.ssafy.cultureisland.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/board")
public class BoardController {

    private BoardService boardService;

//    @Value("${upload.path}")
//    private String uploadPath;

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

    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.BAD_REQUEST);
        }

        String uploadPath;

        // OS 따라 구분자 분리
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")){
            uploadPath = "\\upload\\files\\island_image\\";
        } else{
            uploadPath = "/upload/files/island_image/";
        }

        try {
            // 저장할 디렉토리가 없으면 생성
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 파일 이름을 고유하게 생성
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadPath + fileName);
            Files.write(path, file.getBytes());

            System.out.println(path);
            // 클라이언트에 반환할 이미지 URL
            String imageUrl = fileName;
            System.out.println(imageUrl);
            return ResponseEntity.ok(imageUrl);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            String uploadPath;

            // OS 따라 구분자 분리
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")){
                uploadPath = "\\upload\\files\\island_image\\";
            } else{
                uploadPath ="/upload/files/island_image/";
            }

            Path file = Paths.get(uploadPath, filename);
            if (Files.exists(file)) {
                return ResponseEntity.ok(Files.readAllBytes(file));
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
