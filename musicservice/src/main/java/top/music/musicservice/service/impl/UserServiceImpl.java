package top.music.musicservice.service.impl;

import org.springframework.stereotype.Service;
import top.music.musicservice.dao.UserDao;
import top.music.musicservice.modal.*;
import top.music.musicservice.service.UserService;
import top.music.musicservice.token.TokenService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;
    @Resource
    TokenService tokenService;

    @Override
    public User getUser(User existUser) {
        return userDao.getUser(existUser);
    }

    @Override
    public Object login(User user) {
        User loginUser=userDao.existUser(user);
        if(loginUser==null){
            return "用户名或密码有误!";
        }
        else {
            Map<String,Object> map = new HashMap<String, Object>();
            String token=tokenService.getToken(loginUser);
            map.put("token", token);
            map.put("user",loginUser);
            if("1".equals(loginUser.getRowid())){
                map.put("admin",true);
            }else {
                map.put("admin",false);
            }
            return map;
        }
    }

    @Override
    public Object getMusicList() {
        return userDao.getMusicList();

    }

    @Override
    public Object getMusicInfo(String musicid,String userid) {
        Map<String,Object> map=new HashMap<>();
        List<Comment> commentList=userDao.getCommentList(musicid);
        int existCollect=userDao.existCollect(musicid,userid);
        map.put("commentList",commentList);
        map.put("existCollect",existCollect>0);
        return map;
    }

    @Override
    public Object getNextMusic(String musicid) {
       Music music=userDao.getNextMusic(musicid);
       if(music==null){
           music=userDao.getFirstMusic();
       }
       return music;
    }

    @Override
    public Object getPreMusic(String musicid) {
        Music music=userDao.getPreMusic(musicid);
        if(music==null){
            music=userDao.getLastMusic();
        }
        return music;
    }

    @Override
    public Object addComment(Comment comment) {
        int result=userDao.addComment(comment);
        return result>0;
    }

    @Override
    public Object addCollect(String userid, String musicid) {
        int result=userDao.addCollect(userid,musicid);
        int result1=userDao.addMusicHot(musicid);
        return result>0&&result1>0;
    }

    @Override
    public Object removeCollect(String userid, String musicid) {
        int result=userDao.removeCollect(userid,musicid);
        int result1=userDao.removeMusicHot(musicid);
        return result>0&&result1>0;
    }

    @Override
    public Object search(String value) {
        Map<String,Object> map=new HashMap<>();
        List<Music> musicList=userDao.searchMusic(value);
        List<Singer> singerList=userDao.searchSinger(value);
        map.put("musicList",musicList);
        map.put("singerList",singerList);
        return map;
    }

    @Override
    public Object getSingerList() {
         List<Singer> singerList=userDao.getSingerList();
         for(Singer singer:singerList){
             singer.setMusicnum(userDao.getMusicNumBySingerid(singer.getSingerid()));
         }
        return singerList;
    }

    @Override
    public Object getSingerInfo(String singerid) {
        Singer singer=userDao.getSingerInfo(singerid);
        singer.setMusicnum(userDao.getMusicNumBySingerid(singerid));
        singer.setMusic(userDao.getMusicNameListBySingerid(singerid));
        return singer;
    }

    @Override
    public Object getCollect(String userid) {
       HashMap<String,Object> map=new HashMap<>();
       List<Music> collectMusic=userDao.getCollectMusic(userid);
       List<MusicList> collectList=userDao.getCollectList(userid);
       map.put("collectMusic",collectMusic);
       map.put("collectList",collectList);
        return map;
    }

    @Override
    public Object getList() {
        return userDao.getList();
    }

    @Override
    public Object getListInfo(String listid,String userid) {
        ListInfo listInfo=new ListInfo();
        listInfo.setList(userDao.getListById(listid))
                .setMusics(userDao.getMusicsByListId(listid))
                .setExistCollectList(userDao.existCollectList(listid,userid)>0);
        return listInfo;
    }

    @Override
    public Object getMusicByMusicName(String musicname) {
        return userDao.getMusicByMusicName(musicname);
    }

    @Override
    public Object getSingerBySingerName(String singername) {
        return userDao.getSingerBySingerName(singername);
    }
}
