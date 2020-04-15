package top.music.musicservice.service;

import top.music.musicservice.modal.Comment;
import top.music.musicservice.modal.User;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    User getUser(User existUser);

    Object login(User user);

    Object getMusicList();

    Object getMusicInfo(String musicid,String userid);

    Object getNextMusic(String musicid);

    Object getPreMusic(String musicid);

    Object addComment(Comment comment);

    Object addCollect(String userid, String musicid);

    Object removeCollect(String userid, String musicid);

    Object search(String value);

    Object getSingerList();

    Object getSingerInfo(String singerid);

    Object getCollect(String userid);

    Object getList();

    Object getListInfo(String listid,String userid);

    Object getMusicByMusicName(String musicname);

    Object getSingerBySingerName(String singername);

    Object collectList(String listid, String userid);

    Object removeCollectList(String listid, String userid);

    Object updateUser(User user);

    Object updatePassword(User user);
}
