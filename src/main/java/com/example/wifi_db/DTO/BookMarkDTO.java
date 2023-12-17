package com.example.wifi_db.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter

public class BookMarkDTO {
    private int id;
    private int groupId;
    private String mgrNo;
    private Date regDttm;
}
