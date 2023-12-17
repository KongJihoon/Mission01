package com.example.wifi_db.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class WifiDTO {

    private double distance;        // 거리
    public String mgrNo;            // 관리번호
    public String wrdofc;           // 자치구
    public String mainNm;           // 와이파이명
    public String adres1;           // 도로명주소
    public String adres2;           // 상세주소
    public String instl_Floor;      // 설치위치(층)
    public String instl_Ty;         // 설치유형
    public String instl_Mby;        // 설치기관
    public String svc_Se;           // 서비스구분
    public String cmcWr;            // 망종류
    public int cnstc_Year;          // 설치년도
    public String inout_Door;       // 실내외구분
    public String reMars3;          // wifi접속환경
    public double lat;              // Y좌표
    public double lnt;              // X좌표
    public String work_Dttm;        // 작업일자



}
