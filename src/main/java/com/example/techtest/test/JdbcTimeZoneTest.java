package com.example.techtest.test;

import com.example.techtest.repository.entity.Ticket;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class JdbcTimeZoneTest {
  private final String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
  //      private final String url ="jdbc:postgresql://127.0.0.1:5432/postgres?options=-c%20timezone%3DAsia%2FShanghai",
  private final String user = "postgres";
  private final String password = "postgres";

  public static void main(String[] args) {
    setAnotherTimeZone();
    printTimeZone();

    try {
      JdbcTimeZoneTest instance = new JdbcTimeZoneTest();
      int id = instance.insert();
      instance.select(id);
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }


  }

  public Connection connect() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }

  public int insert() {
    String SQL = "INSERT INTO ticket(\"name\",start_time, end_time) "
        + "VALUES(?,?,?)";

    int id = 0;

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(SQL,
             Statement.RETURN_GENERATED_KEYS)) {
      Timestamp timestamp = Timestamp.from(Instant.now());
      pstmt.setString(1, "Henry");
      pstmt.setObject(2, OffsetDateTime.now(),Types.TIMESTAMP_WITH_TIMEZONE);
      pstmt.setTimestamp(3, timestamp);

      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
          if (rs.next()) {
            id = rs.getInt(1);
          }
        } catch (SQLException ex) {
          throw new RuntimeException(ex);
        }
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
    return id;
  }

  private void select(int id) {
    String SQL = "select id,name,start_time,end_time from ticket where  id =?";

    List<Ticket> result = new ArrayList<>();
    try (Connection conn = connect();
         PreparedStatement preparedStatement = conn.prepareStatement(SQL);
    ) {

      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {

        Integer id2 = resultSet.getInt("id");
        String name = resultSet.getString("name");

        OffsetDateTime start_time = resultSet.getObject("start_time",OffsetDateTime.class);
        Timestamp end_time = resultSet.getTimestamp("end_time");

        Ticket obj = new Ticket();
        obj.setId(id2);
        obj.setName(name);

        System.out.println("end_time timestamp:"+end_time);
        LocalDateTime endTime = end_time.toLocalDateTime();

        obj.setStartTime(start_time);
        obj.setEndTime(endTime);

        // Timestamp -> LocalDateTime
        obj.setEndTime(end_time.toLocalDateTime());

        result.add(obj);

      }

    } catch (SQLException e) {
      System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }

    result.forEach(x -> System.out.println(x));
  }

  private static void setAnotherTimeZone() {
    System.setProperty("user.timezone", "Asia/Amman");

  }

  private static void printTimeZone() {
    TimeZone timeZone = TimeZone.getDefault();
    System.out.printf("DisplayName = %s, ID = %s, offset = %s\n",
        timeZone.getDisplayName(), timeZone.getID(),
        timeZone.getOffset(Instant.now().toEpochMilli()) * 1.0 / 1000 / 3600
    );
    LocalDateTime now = LocalDateTime.now();
    System.out.println("now = " + now);
  }
}
