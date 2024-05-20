package com.ssafy.cultureisland.culture.controller;

import com.ssafy.cultureisland.culture.model.CultureDTO;
import com.ssafy.cultureisland.culture.model.LikeDTO;
import com.ssafy.cultureisland.culture.service.CultureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
        boolean exists = cultureService.checkCultureExists(codename, title, date);
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
        boolean exists = cultureService.checkLikeExists(memberId, codename, title, date);
        return ResponseEntity.ok().body(Collections.singletonMap("exists", exists));
    }

    @PostMapping("/add_like")
    public ResponseEntity<?> addLike(@RequestBody LikeDTO like) {
        System.out.println(like);
        cultureService.addLikeByMemberId(like);
        return ResponseEntity.ok().body("Culture added successfully");
    }
}
