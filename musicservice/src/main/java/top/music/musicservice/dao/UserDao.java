package top.music.musicservice.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.music.musicservice.modal.*;

import java.util.List;

@Repository
public interface UserDao extends BaseMapper {
    User getUser(User existUser);

    List<Music> getMusicList();

    User existUser(User user);

    List<Comment> getCommentList(String musicid);

    Music getNextMusic(String musicid);

    Music getFirstMusic();

    Music getPreMusic(String musicid);

    Music getLastMusic();

    int addComment(Comment comment);

    int existCollect(String musicid, String userid);

    int addCollect(String userid, String musicid);

    int removeCollect(String userid, String musicid);

    List<Music> searchMusic(String value);

    List<Singer> searchSinger(String value);

    int addMusicHot(String musicid);

    int removeMusicHot(String musicid);

    List<Singer> getSingerList();

    String getMusicNumBySingerid(String singerid);

    Singer getSingerInfo(String singerid);

    List<Music> getMusicNameListBySingerid(String singerid);

    List<Music> getCollectMusic(String userid);

    List<MusicList> getList();

    List<MusicList> getCollectList(String userid);


    MusicList getListById(String listid);

    List<Music> getMusicsByListId(String listid);

    int existCollectList(String listid, String userid);

    Music getMusicByMusicName(String musicname);

    Singer getSingerBySingerName(String singername);

    int collectList(String listid, String userid);

    int removeCollectList(String listid, String userid);
}
