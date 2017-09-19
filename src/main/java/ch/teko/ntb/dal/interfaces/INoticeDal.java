package ch.teko.ntb.dal.interfaces;

import ch.teko.ntb.model.Note;

import java.util.List;

/**
 * Created by antic-software-ing on 08.09.2017.
 */
public interface INoticeDal {
  public List<Note> getNotes(int userId) throws Exception;

  public Note getNote(int noteId, int userId) throws Exception;

  // return added notice
  public Note addNote(int userId, Note notice) throws Exception;

  public void updateNote(int userId, Note notice) throws Exception;

  public void deleteNote(int userId, int noticeId) throws Exception;

}
