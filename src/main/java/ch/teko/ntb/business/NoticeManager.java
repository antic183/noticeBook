package ch.teko.ntb.business;

import ch.teko.ntb.business.util.TokenHandler;
import ch.teko.ntb.dal.interfaces.INoticeDal;
import ch.teko.ntb.model.Notice;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created by antic-software-ing on 08.09.2017.
 */
public class NoticeManager {

  @Inject
  private INoticeDal dbNotice;

  public Notice addNote(String jwtToken, Notice notice) throws Exception {
    String cleanToken = jwtToken.replace("Bearer ", "");
    int userId = TokenHandler.getUserIdByToken(cleanToken);
    Notice addedNotice = dbNotice.addNote(userId, notice);

    return addedNotice;
  }

  public void deleteNote(String jwtToken, int noticeId) throws Exception {
    String cleanToken = jwtToken.replace("Bearer ", "");
    int userId = TokenHandler.getUserIdByToken(cleanToken);
    dbNotice.deleteNote(userId, noticeId);
  }

  public void updateNote(String jwtToken, Notice notice) throws Exception {
    String cleanToken = jwtToken.replace("Bearer ", "");
    int userId = TokenHandler.getUserIdByToken(cleanToken);
    dbNotice.updateNote(userId, notice);
  }

    public List<Notice> getNotes(String jwtToken) throws Exception {
      String cleanToken = jwtToken.replace("Bearer ", "");
      int userId = TokenHandler.getUserIdByToken(cleanToken);
      List<Notice> noticeList = dbNotice.getNotesByUserId(userId);
      return noticeList;
    }
}
