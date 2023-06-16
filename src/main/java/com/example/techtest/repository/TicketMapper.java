package com.example.techtest.repository;

import com.example.techtest.repository.entity.Ticket;

public interface TicketMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(Ticket record);

  int insertSelective(Ticket record);

  Ticket selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(Ticket record);

  int updateByPrimaryKey(Ticket record);
}