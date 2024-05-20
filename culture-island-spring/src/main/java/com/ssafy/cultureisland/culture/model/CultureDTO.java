package com.ssafy.cultureisland.culture.model;


import lombok.*;

@Getter @Setter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class CultureDTO {
    private Long id;
    private String title;
    private String date;
    private String codename;
    private String guname;
    private String place;
    private String useTrgt;
    private String useFee;
    private String startDate;
    private double log;
    private double lat;
    private String isFree;
    private String hmpgAddr;
    private double score;
}
