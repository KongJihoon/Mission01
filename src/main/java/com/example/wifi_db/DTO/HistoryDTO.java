package com.example.wifi_db.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter

public class HistoryDTO {
    private int id;
    private double lnt;
    private double lat;
    private Date srcDttm;

}
