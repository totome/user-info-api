package link.tomasztomczyk.userinfoapi.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class SourceUserData {
    private long id;
    private String login;
    private String name;
    private String type;
    private String avatar_url;
    private String created_at;

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
