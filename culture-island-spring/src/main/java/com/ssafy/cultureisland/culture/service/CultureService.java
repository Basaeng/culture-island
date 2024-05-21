package com.ssafy.cultureisland.culture.service;

import com.ssafy.cultureisland.culture.model.CultureDTO;
import com.ssafy.cultureisland.culture.model.CultureMapper;
import com.ssafy.cultureisland.culture.model.LikeDTO;
import com.ssafy.cultureisland.culture.model.LikeResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public LikeResponseDTO checkLikeExists(String memberId, String codename, String title, String date) {
        return cultureMapper.checkLikeExists(memberId, codename, title, date);
    }

    public void addLikeByMemberId(LikeDTO like) {
        cultureMapper.addLike(like);
    }

    public List<LikeDTO> getLikeByMemberId(String memberId) {
        return cultureMapper.getLikeByMemberId(memberId);
    }

    public void deleteLikeByMemberId(String memberId) {
        cultureMapper.deleteLikeByMemberId(memberId);
    }
}
