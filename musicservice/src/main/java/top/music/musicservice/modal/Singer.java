package top.music.musicservice.modal;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Singer {
    private String singerid;
    private String singername;
    private String singerimg;
    private String singercountry;
    private String singerdetails;
    private String musicnum;
    private List<Music> music;
}
