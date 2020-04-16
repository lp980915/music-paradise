package top.music.musicservice.modal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Review {
    private String reviewid;
    private String userid;
    private String username;
    private String reason;
    private String details;
    private String img;
    private String country;
    private String result;
    private String music;
}
