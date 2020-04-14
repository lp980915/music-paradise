package top.music.musicservice.modal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Comment {
    private String commentid;
    private String comment;
    private String userid;
    private String username;
    private String musicid;
    private String date;
}
