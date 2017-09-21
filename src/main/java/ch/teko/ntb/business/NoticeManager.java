package ch.teko.ntb.business;

import ch.teko.ntb.business.util.TokenHandler;
import ch.teko.ntb.dal.interfaces.INoticeDal;
import ch.teko.ntb.model.Note;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created by antic-software-ing on 08.09.2017.
 */
public class NoticeManager {

  @Inject
  private INoticeDal dbNotice;

  public Note addNote(String jwtToken, Note notice) throws Exception {
    int userId = getUserIdByToken(jwtToken);
    Note addedNotice = dbNotice.addNote(userId, notice);

    return addedNotice;
  }

  public void deleteNote(String jwtToken, int noticeId) throws Exception {
    int userId = getUserIdByToken(jwtToken);
    dbNotice.deleteNote(userId, noticeId);
  }

  public void updateNote(String jwtToken, Note notice) throws Exception {
    int userId = getUserIdByToken(jwtToken);
    dbNotice.updateNote(userId, notice);
  }

  public List<Note> getNotes(String jwtToken) throws Exception {
    int userId = getUserIdByToken(jwtToken);
    List<Note> noticeList = dbNotice.getNotes(userId);
    return noticeList;
  }

  public Note getNote(int noticeId, String jwtToken) throws Exception {
    int userId = getUserIdByToken(jwtToken);
    Note note = dbNotice.getNote(userId, noticeId);

    return note;
  }

  public void deleteAllNotes(String jwtToken) throws Exception {
    int userId = getUserIdByToken(jwtToken);
    dbNotice.deleteAllNotes(userId);
  }

  private int getUserIdByToken(String token) throws Exception {
    String cleanToken = token.replace("Bearer ", "");
    return TokenHandler.getUserIdByToken(cleanToken);
  }

}
