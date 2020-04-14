package top.music.musicservice.modal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private String userid;
    private String rowid;
    private String username;
    private String password;
    private String sex;
    private String birth;
}
