package ch.teko.ntb.dal.mysql;

import ch.teko.ntb.dal.interfaces.INoticeDal;
import ch.teko.ntb.model.Note;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antic-software-ing on 08.09.2017.
 */
public class NoticeMySqlDal implements INoticeDal {

  private static final String TABLE_NOTICE = "notice";
  private static final String TABLE_USER_NOTICE = "user_notice";
  private MySqlConnector mySqlConnector;

  public NoticeMySqlDal() {
    try {
      mySqlConnector = MySqlConnector.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Note> getNotes(int userId) throws Exception {
    Connection connection;
    List<Note> noticeList = new ArrayList<>();
    boolean error = false;
    String errorMsg = "";

    try {
      connection = mySqlConnector.getConnection();
      String query = "SELECT * FROM " + TABLE_NOTICE + " n"
          + " JOIN " + TABLE_USER_NOTICE + " un"
          + " ON un.notice_id=n.id"
          + " where un.user_id=(?)";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, userId);

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        int dbNoticeId = rs.getInt("n.id");
        String dbNoticeTitle = rs.getString("n.title");
        String dbNoticeText = rs.getString("n.text");

        noticeList.add(new Note(dbNoticeId, dbNoticeTitle, dbNoticeText));
      }

      rs.close();
      preparedStatement.close();
    } catch (SQLException e) {
      error = true;
      errorMsg = "Sql getNotes error.";
      e.printStackTrace();
    } finally {
      try {
        mySqlConnector.closeConnection();
      } catch (SQLException e) {
        error = true;
        errorMsg = "Sql getNotes: close connection error.";
        e.printStackTrace();
      }

      if (error) {
        throw new Exception(errorMsg);
      }

      return noticeList;
    }
  }

  @Override
  public Note getNote(int userid, int noteId) throws Exception {
    Connection connection;
    Note note = null;
    boolean error = false;
    String errorMsg = "";

    try {
      connection = mySqlConnector.getConnection();
      String query = "SELECT * FROM " + TABLE_NOTICE + " n"
          + " JOIN " + TABLE_USER_NOTICE + " un"
          + " ON un.notice_id=n.id"
          + " where un.user_id=(?) AND n.id=(?)";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, userid);
      preparedStatement.setInt(2, noteId);

      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        int dbNoteId = rs.getInt("n.id");
        String dbNoteTitle = rs.getString("n.title");
        String dbNoteText = rs.getString("n.text");

        LocalDateTime dbNoticeCreatedAt = rs.getTimestamp("n.created_at").toLocalDateTime();

        note = new Note(dbNoteId, dbNoteTitle, dbNoteText, dbNoticeCreatedAt);
      } else {
        error = true;
        errorMsg = "note doesn't exist";
      }

      rs.close();
      preparedStatement.close();
    } catch (SQLException e) {
      error = true;
      errorMsg = "Sql get note error.";
      e.printStackTrace();
    } finally {
      try {
        mySqlConnector.closeConnection();
      } catch (SQLException e) {
        error = true;
        errorMsg = "Sql get note: close connection error.";
        e.printStackTrace();
      }

      if (error) {
        throw new Exception(errorMsg);
      }

      return note;
    }
  }

  @Override
  public Note addNote(int userId, Note notice) throws Exception {
    Connection connection = null;
    boolean error = false;
    String errorMsg = "";
    int noticeId = 0;

    try {
      connection = mySqlConnector.getConnection();
      // begin transaction
      connection.setAutoCommit(false);

      // insert into table notice
      String query = "INSERT INTO " + TABLE_NOTICE + "(title, text, created_at) VALUES(?, ?, NOW())";
      PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, notice.getTitle());
      preparedStatement.setString(2, notice.getText());
      preparedStatement.execute();

      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        noticeId = rs.getInt(1);
        notice.setId(noticeId);
      } else {
        error = true;
        errorMsg = "Sql addNote error. Note doesn't added.";
      }

      // insert into table user_notice
      query = "INSERT INTO " + TABLE_USER_NOTICE + "(user_id, notice_id) VALUES(?, ?)";
      preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, userId);
      preparedStatement.setInt(2, notice.getId());
      preparedStatement.execute();

      rs = preparedStatement.getGeneratedKeys();
      if (!rs.next()) {
        error = true;
        errorMsg = "Sql addNote error. Note doesn't added.";
      }

      rs.close();
      preparedStatement.close();
      connection.commit();
    } catch (SQLException e) {
      error = true;
      errorMsg = "Sql addNote error";
      e.printStackTrace();
    } finally {
      if (error) {
        connection.rollback();
      }
      connection.setAutoCommit(true);

      try {
        mySqlConnector.closeConnection();
      } catch (SQLException e) {
        e.printStackTrace();
        error = true;
        errorMsg = "Sql addNote: close connection error";
      }

      if (error) {
        throw new Exception(errorMsg);
      }

      return notice;
    }
  }

  @Override
  public void updateNote(int userId, Note notice) throws Exception {
    Connection connection = null;
    boolean error = false;
    String errorMsg = "";

    try {
      connection = mySqlConnector.getConnection();

      // der user darf nur die notices ändern die auch ihm gehören.
      // um dies sicherzustellen hol ich mir die notice_id durch die unterabfrage auf die tabelle notice_user
      String query = "UPDATE " + TABLE_NOTICE + " SET title=?, text=? WHERE id="
          + "(SELECT notice_id from " + TABLE_USER_NOTICE + " un"
          + " WHERE un.user_id=? AND un.notice_id=?)";
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, notice.getTitle());
      preparedStatement.setString(2, notice.getText());
      preparedStatement.setInt(3, userId);
      preparedStatement.setInt(4, notice.getId());
      int rs = preparedStatement.executeUpdate();
      if (rs == 0) {
        error = true;
        errorMsg = "Sql updateNote error. Note doesn't changed.";
      }

      preparedStatement.close();
    } catch (SQLException e) {
      error = true;
      errorMsg = "Sql updateNote error";
      e.printStackTrace();
    } finally {
      try {
        mySqlConnector.closeConnection();
      } catch (SQLException e) {
        e.printStackTrace();
        error = true;
        errorMsg = "Sql updateNote: close connection error";
      }

      if (error) {
        throw new Exception(errorMsg);
      }
    }
  }

  @Override
  public void deleteNote(int userId, int noticeId) throws Exception {
    Connection connection = null;
    boolean error = false;
    String errorMsg = "";

    try {
      connection = mySqlConnector.getConnection();
      // der user darf nur die notices löschen die auch ihm gehören.
      // um dies sicherzustellen hol ich mir die notice_id durch die unterabfrage auf die tabelle notice_user
      String query = "DELETE FROM " + TABLE_NOTICE
          + " WHERE id="
          + "(SELECT notice_id from " + TABLE_USER_NOTICE + " un"
          + " WHERE un.user_id=? AND un.notice_id=?)";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, userId);
      preparedStatement.setInt(2, noticeId);

      int rs = preparedStatement.executeUpdate();
      if (rs == 0) {
        error = true;
        errorMsg = "Delete Note failed!";
      }
      preparedStatement.close();
    } catch (SQLException e) {
      error = true;
      e.printStackTrace();
      errorMsg = "sql deleteNote error";
    } finally {
      try {
        mySqlConnector.closeConnection();
      } catch (SQLException e) {
        e.printStackTrace();
        error = true;
        errorMsg = "Sql deleteNote: close connection error";
      }

      if (error) {
        throw new Exception(errorMsg);
      }
    }
  }

  @Override
  public void deleteAllNotes(int userId) throws Exception {
    Connection connection = null;
    boolean error = false;
    String errorMsg = "";

    try {
      connection = mySqlConnector.getConnection();
      // der user darf nur die notices löschen die auch ihm gehören.
      // um dies sicherzustellen hol ich mir die notice_id durch die unterabfrage auf die tabelle notice_user
      String query = "DELETE FROM " + TABLE_NOTICE
          + " WHERE id in "
          + "(SELECT notice_id from " + TABLE_USER_NOTICE + " un"
          + " WHERE un.user_id=?)";

      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, userId);

      int rs = preparedStatement.executeUpdate();
      if (rs == 0) {
        error = true;
        errorMsg = "Delete all Notes failed!";
      }
      preparedStatement.close();
    } catch (SQLException e) {
      error = true;
      e.printStackTrace();
      errorMsg = "sql deleteAllNotes error";
    } finally {
      try {
        mySqlConnector.closeConnection();
      } catch (SQLException e) {
        e.printStackTrace();
        error = true;
        errorMsg = "Sql deleteAllNotes: close connection error";
      }

      if (error) {
        throw new Exception(errorMsg);
      }
    }
  }


}
