package com.ssafy.cultureisland.culture.service;

import com.ssafy.cultureisland.culture.model.CultureDTO;
import com.ssafy.cultureisland.culture.model.CultureMapper;
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
}
