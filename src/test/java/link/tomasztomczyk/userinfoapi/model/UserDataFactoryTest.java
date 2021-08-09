package link.tomasztomczyk.userinfoapi.model;

import link.tomasztomczyk.userinfoapi.persistence.LoginRequestsCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class UserDataFactoryTest {
    private static final Long ID = 0xC0FFEE_C00L;
    private static final String LOGIN = "fdsklhuetwenfiojf9309";
    private static final String NAME = "Marlon";
    private static final String TYPE = "jiof3208wfo32iu90u";
    private static final String AVATAR = "n5435jk43n54nm,n";
    private static final String CREATED = "j32jor2ujfiwsuf9u";
    private InputUserData.InputUserDataBuilder inputBuilder;

    private UserDataFactory factory;

    @Mock
    LoginRequestsCounter counter;

    @BeforeEach
    public void setUp() {
        factory = new UserDataFactory(counter);
        inputBuilder = InputUserData.builder()
                .id(ID)
                .login(LOGIN)
                .name(NAME)
                .type(TYPE)
                .avatar_url(AVATAR)
                .created_at(CREATED);
    }

    @Test
    public void factoryShouldThrowAnExceptionIfGivenDataObjectIsNull() throws UserDataFactory.UserDataInvalidException {
        try {
            factory.create(null);
            fail("Should throw an exception when the argument is null");
        } catch (IllegalArgumentException ex) {}
    }

    @Test
    public void factoryShouldThrowAnExceptionIfGivenDataObjectIsInvalid() throws UserDataFactory.UserDataInvalidException {
        try {
            factory.create(InputUserData.builder().build());
            fail("Should throw an exception when the argument is null");
        } catch (UserDataFactory.UserDataInvalidException ex) {}
    }

    @Test
    public void factoryShouldTranslateRequiredFieldsWithModifiedTypes() throws UserDataFactory.UserDataInvalidException {
        var result = factory.create(inputBuilder.build());
        assertEquals(Long.toString(ID), result.getId());
        assertEquals(LOGIN, result.getLogin());
        assertEquals(NAME, result.getName());
        assertEquals(TYPE, result.getType());
        assertEquals(AVATAR, result.getAvatarUrl());
        assertEquals(CREATED, result.getCratedAt());
    }

    @Test
    public void factoryShouldReturnCalculationsAsNaNWhenNumberOfFollowersIsZero() throws UserDataFactory.UserDataInvalidException {
        var result = factory.create(inputBuilder.build());
        assertEquals("NaN", result.getCalculations());
    }
}
