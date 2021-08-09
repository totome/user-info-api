package link.tomasztomczyk.userinfoapi.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OutputUserData {
    private final String id;
    private final String login;
    private final String name;
    private final String type;
    private final String avatarUrl;
    private final String cratedAt;
    private final String calculations;
}
