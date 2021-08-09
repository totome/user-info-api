package link.tomasztomczyk.userinfoapi.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class InputUserData {
    private final Long id;
    private final String login;
    private final String name;
    private final String type;
    private final String avatar_url;
    private final String created_at;
    private final long followers;
    private final long public_repos;
}
