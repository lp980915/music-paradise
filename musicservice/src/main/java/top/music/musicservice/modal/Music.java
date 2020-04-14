package top.music.musicservice.modal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)   //链式写法
public class Music {
    private String musicid;
    private String musicname;
    private String singerid;
    private String singername;
    private int hot;
    private String musicimg;
    private String musicfile;
    private String date; //收藏时间
}
