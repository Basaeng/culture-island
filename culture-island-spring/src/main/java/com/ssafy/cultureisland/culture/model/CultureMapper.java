package com.ssafy.cultureisland.culture.model;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CultureMapper {
    public boolean checkCultureExists(String codename, String title, String date);

    void addCulture(CultureDTO culture);

    boolean checkLikeExists(String memberId, String cultureCodename, String cultureTitle, String cultureDate);

    void addLike(LikeDTO like);
}
