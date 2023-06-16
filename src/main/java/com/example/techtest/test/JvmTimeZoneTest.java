package com.example.techtest.test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.TimeZone;

public class JvmTimeZoneTest {

  public static void main(String[] args) {
//    System.setProperty("user.timezone", "Asia/Amman");
    TimeZone timeZone = TimeZone.getDefault();
    System.out.printf("DisplayName = %s, ID = %s, offset = %s\n",
        timeZone.getDisplayName(),timeZone.getID(),
        timeZone.getOffset(Instant.now().toEpochMilli())*1.0/1000/3600
    );
    LocalDateTime nowLocal=LocalDateTime.now();
    System.out.println("nowLocal = " + nowLocal);
    var nowOff= OffsetDateTime.now();
    System.out.println("nowOff = " + nowOff);

  }
}
