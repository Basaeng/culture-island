package com.ssafy.cultureisland.culture.model;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CultureMapper {
    public boolean checkCultureExists(String codename, String title, String date);

    void addCulture(CultureDTO culture);

    LikeResponseDTO checkLikeExists(String memberId, String cultureCodename, String cultureTitle, String cultureDate);

    void addLike(LikeDTO like);

    List<LikeDTO> getLikeByMemberId(String memberId);

    void deleteLikeByMemberId(String memberId);
}
