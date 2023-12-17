package com.example.wifi_db.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class BookMarkGroupDTO {
    private int id;
    private String name;
    private int sequence;
    private Date regDttm;
    private Date uptDttm;
}
