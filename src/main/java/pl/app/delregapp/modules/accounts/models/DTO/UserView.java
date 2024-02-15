package pl.app.delregapp.modules.accounts.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserView {
    private String userName;
    private String avatar;

    public UserView setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserView setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
}