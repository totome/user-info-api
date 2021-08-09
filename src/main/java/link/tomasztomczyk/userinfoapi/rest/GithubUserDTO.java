package link.tomasztomczyk.userinfoapi.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import link.tomasztomczyk.userinfoapi.model.InputUserData;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class GithubUserDTO {
    private final InputUserData.InputUserDataBuilder builder;

    public GithubUserDTO() {
        builder = InputUserData.builder();
    }

    public void setId(long id) {
        builder.id(id);
    }

    public void setLogin(String login) {
        builder.login(login);
    }

    public void setName(String name) {
        builder.name(name);
    }

    public void setType(String type) {
        builder.type(type);
    }

    public void setAvatar_url(String avatar_url) {
        builder.avatar_url(avatar_url);
    }

    public void setCreated_at(String created_at) {
        builder.created_at(created_at);
    }

    public void setFollowers(long followers) {
        builder.followers(followers);
    }

    public void setPublic_repos(long repos) {
        builder.public_repos(repos);
    }

    public InputUserData getData() {
        return builder.build();
    }
}
