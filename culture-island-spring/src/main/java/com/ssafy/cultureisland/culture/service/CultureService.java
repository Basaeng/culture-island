package com.ssafy.cultureisland.culture.service;

import com.ssafy.cultureisland.culture.model.CultureDTO;
import com.ssafy.cultureisland.culture.model.CultureMapper;
import com.ssafy.cultureisland.culture.model.LikeDTO;
import org.springframework.stereotype.Service;

@Service
public class CultureService {

    private final CultureMapper cultureMapper;

    public CultureService(CultureMapper cultureMapper) {
        this.cultureMapper = cultureMapper;
    }

    public boolean checkCultureExists(String codename, String title, String date) {
        return cultureMapper.checkCultureExists(codename, title, date);
    }

    public void addCulture(CultureDTO culture) {
        cultureMapper.addCulture(culture);
    }

    public boolean checkLikeExists(String memberId, String codename, String title, String date) {
        return cultureMapper.checkLikeExists(memberId, codename, title, date);
    }

    public void addLikeByMemberId(LikeDTO like) {
        cultureMapper.addLike(like);
    }
}
