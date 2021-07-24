package network.dto;

import java.io.Serializable;

public class SignUpRequest implements Serializable {
    String clubName;
    String password;

    public SignUpRequest(String clubName, String password) {
        this.clubName = clubName;
        this.password = password;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
