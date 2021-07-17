package network.dto;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    String clubName;
    String password;

    public LoginRequest(String clubName, String password){
        this.clubName = clubName;
        this.password = password;
    }

    public String getClubName() {
        return clubName;
    }

    public String getPassword() {
        return password;
    }
}
