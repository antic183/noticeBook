package ch.teko.ntb.dal.interfaces;

import ch.teko.ntb.model.Notice;
import ch.teko.ntb.model.User;

import java.util.List;

/**
 * Created by antic-software-ing on 08.09.2017.
 */
public interface INoticeDal {
  public List<Notice> getNotesByUserId(int userId) throws Exception;

  // return added notice
  public Notice addNote(int userId, Notice notice) throws Exception;

  public void updateNote(int userId, Notice notice) throws Exception;

  public void deleteNote(int userId, int noticeId) throws Exception;
}
