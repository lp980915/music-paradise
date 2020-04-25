package top.music.musicservice.service.impl;

import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.music.musicservice.dao.UserDao;
import top.music.musicservice.modal.*;
import top.music.musicservice.service.UserService;
import top.music.musicservice.token.TokenService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public Object login(User user){
        User loginUser=userDao.existUser(user);
        /*Thread.sleep(2000);*/
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

    @Override
    public Object collectList(String listid, String userid) {
        int result=userDao.collectList(listid,userid);
        return result>0;
    }

    @Override
    public Object removeCollectList(String listid, String userid) {
        int result=userDao.removeCollectList(listid,userid);
        return result>0;
    }

    @Override
    public Object updateUser(User user) {
        System.out.println(user.toString());
      if(user.getRowid().equals("2")){
          int result1=userDao.updateSingerName(user);
      }
        int result=userDao.updateUser(user);
        return result>0;
    }

    @Override
    public Object updatePassword(User user) {
        int result=userDao.updatePassword(user);
        return result>0;
    }

    @Override
    public Object uploadSingerReq(MultipartFile file, HttpServletRequest req) {
         int result=0;
         Map<String,Object> map=new HashMap<>();
        //首先判断是不是空的文件
        if (!file.isEmpty()) {
            //对文文件的全名进行截取然后在后缀名进行删选。
            int begin = file.getOriginalFilename().indexOf(".");
            int last = file.getOriginalFilename().length();
            //获得文件后缀名
            String a = file.getOriginalFilename().substring(begin, last);
             //判断文件类型
            if (a.endsWith(".jpg")||a.endsWith(".png")||a.endsWith(".jfif")) {
                String realpath=req.getServletContext().getRealPath("/reqimg/");
                File folder=new File(realpath);
                File copyFile=new File("musicservice/src/main/resources/static/reqimg/");
                if(!folder.exists()){
                    folder.mkdirs();
                }
                //获取文件全名
                String oldName=file.getOriginalFilename();
                String newName= UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."));
                File imgTomcatPath=new File(folder,newName);
                File localPathFile=new File(copyFile.getAbsolutePath()+File.separator+ newName);
                System.out.println("真实路径"+localPathFile.toPath());
                try {
                    file.transferTo(imgTomcatPath);
                    Files.copy(imgTomcatPath.toPath(), localPathFile.toPath());
                }catch (IOException e1) {
                    e1.printStackTrace();
                }
                String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/reqimg/"+ newName;
                map.put("imgURL",url);
            } else {
                String realpath=req.getServletContext().getRealPath("/reqmusic/");
                File folder=new File(realpath);
                File copyFile=new File("musicservice/src/main/resources/static/reqmusic/");
                if(!folder.exists()){
                    folder.mkdirs();
                }
                //获取文件全名
                String oldName=file.getOriginalFilename();
                String newName= UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."));
                File imgTomcatPath=new File(folder,newName);
                File localPathFile=new File(copyFile.getAbsolutePath()+File.separator+ newName);
                System.out.println("真实路径"+localPathFile.toPath());
                try {
                    file.transferTo(imgTomcatPath);
                    Files.copy(imgTomcatPath.toPath(), localPathFile.toPath());
                }catch (IOException e1) {
                    e1.printStackTrace();
                }
                String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/reqmusic/"+ newName;
                map.put("musicURL",url);
            }
        }
        return map;
    }

    @Override
    public Object submitSingerReq(Review review) {
        int result=userDao.submitSingerReq(review);
        return result>0;
    }

    @Override
    public Object existSingerReq(String userid) {
        if(userDao.existSingerReqOrNull(userid)>0){
            return userDao.existSingerReq(userid);
        }else {
            return "无";
        }
    }

    @Override
    public Object becomeSinger(String userid) {
        int result=userDao.becomeSinger(userid);
        Review review=userDao.getReviewByUserId(userid);
        int result1=userDao.addSinger(review);
        return result>0&&result1>0;
    }

    @Override
    public Object getReviewList() {
        return userDao.getReviewList();
    }

    @Override
    public Object reviewSuccess(String userid) {
        int result=userDao.reviewSuccess(userid);
        return result>0;
    }

    @Override
    public Object reviewFail(String userid) {
        int result=userDao.reviewFail(userid);
        return result>0;
    }

    @Override
    public Object clearFailSubmit(String userid) {
        int result=userDao.clearFailSubmit(userid);
        return result>0;
    }

    @Override
    public Object getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public Object addUser(User user) {
        int result=userDao.addUser(user);
        return result>0;
    }

    @Override
    public Object deleteUser(String userid) {
        int result=userDao.deleteUser(userid);
        return result>0;
    }
}
