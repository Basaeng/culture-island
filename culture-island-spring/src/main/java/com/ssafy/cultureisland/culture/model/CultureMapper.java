package com.ssafy.cultureisland.culture.model;

import org.springframework.stereotype.Repository;

@Repository
public interface CultureMapper {
    public boolean checkCultureExists(String codename, String title, String date);

    void addCulture(CultureDTO culture);
}
