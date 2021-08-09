package link.tomasztomczyk.userinfoapi.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="REQUESTS")
@Getter
public class LoginCounter {
    @Id
    @Column(name="LOGIN")
    String login;
    @Column(name="REQUEST_COUNT")
    @Setter
    int count;

    public LoginCounter(){
    }

    public LoginCounter(String login, int count){
        this.login = login;
        this.count = count;
    }
}
