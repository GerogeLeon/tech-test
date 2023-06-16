package com.example.techtest.repository.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class Ticket {
  private Integer id;

  private String name;

  private OffsetDateTime startTime;

  private LocalDateTime endTime;
}