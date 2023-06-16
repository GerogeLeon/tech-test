package com.example.techtest.controller;

import com.example.techtest.repository.TicketMapper;
import com.example.techtest.repository.entity.Ticket;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@RestController
public class TicketController {
  private final TicketMapper ticketMapper;

  public TicketController(TicketMapper ticketMapper) {
    this.ticketMapper = ticketMapper;
  }

  @PostMapping("/test/ticket/add")
  public Ticket add(@RequestBody  Ticket record) {
    record.setStartTime(OffsetDateTime.now());
    record.setEndTime(LocalDateTime.now());
     ticketMapper.insertSelective(record);
    return ticketMapper.selectByPrimaryKey(record.getId());
  }

  @GetMapping("/test/ticket/detail")
  public Ticket detail(@RequestParam("id") Integer id) {
    return ticketMapper.selectByPrimaryKey(id);
  }
}

