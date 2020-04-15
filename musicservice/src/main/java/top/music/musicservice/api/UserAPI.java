package top.music.musicservice.api;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.music.musicservice.modal.Comment;
import top.music.musicservice.modal.User;
import top.music.musicservice.service.UserService;
import top.music.musicservice.token.annotation.UserLoginToken;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserAPI extends ApiController {
    @Resource
    UserService userService;

    @PostMapping("/login")
    public R<Object> login(User user){return success(userService.login(user));}

    @UserLoginToken
    @GetMapping("/getMusicList")
    public R<Object> getMusicList(){return success(userService.getMusicList());}

    @UserLoginToken
    @GetMapping("/getMusicInfo")
    public R<Object> getMusicInfo(String musicid,String userid){return success(userService.getMusicInfo(musicid,userid));}

    @UserLoginToken
    @GetMapping("/getNextMusic")
    public R<Object> getNextMusic(String musicid){return success(userService.getNextMusic(musicid));}

    @UserLoginToken
    @GetMapping("/getPreMusic")
    public R<Object> getPreMusic(String musicid){return success(userService.getPreMusic(musicid));}

    @UserLoginToken
    @GetMapping("/addComment")
    public R<Object> addComment(Comment comment){return success(userService.addComment(comment));}

    @UserLoginToken
    @GetMapping("/addCollect")
    public R<Object> addCollect(String userid,String musicid){return success(userService.addCollect(userid,musicid));}

    @UserLoginToken
    @GetMapping("/removeCollect")
    public R<Object> removeCollect(String userid,String musicid){return success(userService.removeCollect(userid,musicid));}

    @UserLoginToken
    @GetMapping("/search")
    public R<Object> search(String value){return success(userService.search(value));}

    @UserLoginToken
    @GetMapping("/getSingerList")
    public R<Object> getSingerList(){return success(userService.getSingerList());}

    @UserLoginToken
    @GetMapping("/getSingerInfo")
    public R<Object> getSingerInfo(String singerid){return success(userService.getSingerInfo(singerid));}

    @UserLoginToken
    @GetMapping("/getCollect")
    public R<Object> getCollect(String userid){return success(userService.getCollect(userid));}

    @UserLoginToken
    @GetMapping("/getList")
    public R<Object> getList(){return success(userService.getList());}

    @UserLoginToken
    @GetMapping("/getListInfo")
    public R<Object> getListInfo(String listid,String userid){return success(userService.getListInfo(listid,userid));}

    @UserLoginToken
    @GetMapping("/getMusicByMusicName")
    public R<Object> getMusicByMusicName(String musicname){return success(userService.getMusicByMusicName(musicname));}

    @UserLoginToken
    @GetMapping("/getSingerBySingerName")
    public R<Object> getSingerBySingerName(String singername){return success(userService.getSingerBySingerName(singername));}

    @UserLoginToken
    @GetMapping("/collectList")
    public R<Object> collectList(String listid,String userid){return success(userService.collectList(listid,userid));}

    @UserLoginToken
    @GetMapping("/removeCollectList")
    public R<Object> removeCollectList(String listid,String userid){return success(userService.removeCollectList(listid,userid));}

    @UserLoginToken
    @PostMapping("/updateUser")
    public R<Object> updateUser(@RequestBody User user){return success(userService.updateUser(user));}

    @UserLoginToken
    @PostMapping("/updatePassword")
    public R<Object> updatePassword(User user){return success(userService.updatePassword(user));}


    @CrossOrigin("http://localhost:8080")
    @PostMapping("/uploadSingerReq")
    public R<Object> uploadSingerReq(MultipartFile file, HttpServletRequest req){return success(userService.uploadSingerReq(file,req));}
}
