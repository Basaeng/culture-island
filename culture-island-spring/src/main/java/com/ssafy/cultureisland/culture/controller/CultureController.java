package com.ssafy.cultureisland.culture.controller;

import com.ssafy.cultureisland.culture.model.CultureDTO;
import com.ssafy.cultureisland.culture.model.LikeDTO;
import com.ssafy.cultureisland.culture.model.LikeResponseDTO;
import com.ssafy.cultureisland.culture.service.CultureService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/culture")
public class CultureController {

    private final CultureService cultureService;

    @Autowired
    public CultureController(CultureService cultureService) {
        this.cultureService = cultureService;
    }

    @GetMapping("/check-culture/{codename}/{title}/{date}")
    public ResponseEntity<?> checkCulture(@PathVariable String codename, @PathVariable String title, @PathVariable String date) {
        System.out.println(codename + " " + title + " " + date);
        boolean exists = cultureService.checkCultureExists(codename, title, date);
        System.out.println("cultureexists = " + exists);
        return ResponseEntity.ok().body(Collections.singletonMap("exists", exists));
    }

    @PostMapping("/add-culture")
    public ResponseEntity<?> addCulture(@RequestBody CultureDTO culture) {
        System.out.println(culture);
        cultureService.addCulture(culture);
        return ResponseEntity.ok().body("Culture added successfully");
    }

    @GetMapping("/check_like/{memberId}/{codename}/{title}/{date}")
    public ResponseEntity<?> checkLike(@PathVariable String memberId, @PathVariable String codename, @PathVariable String title, @PathVariable String date) {
        System.out.println("memberId = " + memberId);
        System.out.println("codename = " + codename);
        System.out.println("title = " + title);
        System.out.println("date = " + date);
        LikeResponseDTO likeResponseDTO = cultureService.checkLikeExists(memberId, codename, title, date);
        System.out.println("likeResponseDTO = " + likeResponseDTO);
        return ResponseEntity.ok().body(Collections.singletonMap("likeResponse", likeResponseDTO));
    }

    @PostMapping("/like")
    public ResponseEntity<?> addLike(@RequestBody LikeDTO like) {
        System.out.println(like);
        cultureService.addLikeByMemberId(like);
        return ResponseEntity.ok().body("Culture added successfully");
    }

    @DeleteMapping("/like/{memberId}")
    public ResponseEntity<?> deleteLike(@PathVariable String memberId) {
        cultureService.deleteLikeByMemberId(memberId);
        return ResponseEntity.ok().body("Culture deleted successfully");
    }

    @GetMapping("/like/{memberId}")
    public ResponseEntity<?> getLikeByMemberId(@PathVariable String memberId) {
        System.out.println("memberId: " + memberId);
        List<LikeDTO> likedShows = cultureService.getLikeByMemberId(memberId);
        return ResponseEntity.ok().body(likedShows);
    }
}
