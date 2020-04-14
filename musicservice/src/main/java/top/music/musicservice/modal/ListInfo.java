package top.music.musicservice.modal;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListInfo {
    private List<Music> musics;
    private MusicList List;
    private Boolean existCollectList;
}
