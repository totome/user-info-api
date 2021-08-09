package link.tomasztomczyk.userinfoapi.model;

import link.tomasztomczyk.userinfoapi.persistence.LoginRequestsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;

@Component
public class UserDataFactory {
    private final LoginRequestsCounter counter;

    @Autowired
    public UserDataFactory(LoginRequestsCounter counter) {
        this.counter = counter;
    }

    public OutputUserData create(InputUserData input) throws UserDataInvalidException {
        if (input == null) {
            throw new IllegalArgumentException("Given user data can not be null");
        }

        if (input.getId() == null || input.getLogin() == null || input.getLogin().isBlank()) {
            throw new UserDataInvalidException("Invalid user data");
        }

        counter.inc(input.getLogin());

        return OutputUserData.builder()
                .id(Long.toString(input.getId()))
                .login(input.getLogin())
                .name(input.getName())
                .type(input.getType())
                .avatarUrl(input.getAvatar_url())
                .cratedAt(input.getCreated_at())
                .calculations(getCalculations(input))
                .build();
    }

    private String getCalculations(InputUserData input) {
        final long followers = input.getFollowers();
        final String result;
        if (followers == 0) {
            result = "NaN";
        } else {
            result = new BigDecimal(6L)
                    .multiply(new BigDecimal(2+input.getPublic_repos()))
                    .divide(new BigDecimal(followers), MathContext.DECIMAL128)
                    .toString();
        }
        return result;
    }

    public static class UserDataInvalidException extends Exception {
        public UserDataInvalidException(String msg) {
            super(msg);
        }
    }
}
