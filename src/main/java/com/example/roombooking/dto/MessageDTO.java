package com.example.roombooking.dto;


import java.util.Date;

public class MessageDTO {

    private Long id;
    public String type;
    public Date timeStamp;
    public String roomNo;
}


//    Utskrift per rum ska se ut såhär:
//
//        RUM 30
//
//        Dörren öppnad 2022-12-12 18:11:04
//
//        Dörren stängd 2022-12-12 18:11:06
//
//        Dörren öppnad 2022-12-13 08:11:06
//
//        Dörren öppnad 2022-12-13 10:11:04
//
//        Städning påbörjat av Per Persson 2022-12-13 10:11:06
//
//        Dörren stängd 2022-12-12 10:11:14
//
//        Städning avslutat av Per Persson 2022-12-13 12:11:06
//
//        Dörren öppnad 2022-12-13 12:11:20
//
//        Dörren stängd 2022-12-13 12:11:24