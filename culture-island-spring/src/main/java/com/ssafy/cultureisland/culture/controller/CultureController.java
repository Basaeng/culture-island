package com.ssafy.cultureisland.culture.controller;

import com.ssafy.cultureisland.culture.model.CultureDTO;
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

    @GetMapping("check-culture/{codename}/{title}/{date}")
    public ResponseEntity<?> check(@PathVariable String codename, @PathVariable String title, @PathVariable String date) {
        boolean exists = cultureService.checkCultureExists(codename, title, date);
        return ResponseEntity.ok().body(Collections.singletonMap("exists", exists));
    }

    @PostMapping("/add-culture")
    public ResponseEntity<?> addCulture(@RequestBody CultureDTO culture) {
        System.out.println(culture);
        cultureService.addCulture(culture);
        return ResponseEntity.ok().body("Culture added successfully");
    }
}
