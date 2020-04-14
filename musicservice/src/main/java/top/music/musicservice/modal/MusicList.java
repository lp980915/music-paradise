package top.music.musicservice.modal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)   //链式写法
public class MusicList {
private String listid;
private String listname;
private String listimg;
private String listintro;
}
