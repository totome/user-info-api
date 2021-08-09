package link.tomasztomczyk.userinfoapi.model;

import link.tomasztomczyk.userinfoapi.persistence.LoginRequestsCounter;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserDataFactoryCalculationTest {
    private static final BigDecimal DELTA = new BigDecimal("0.00001");
    @Mock
    private LoginRequestsCounter counter;
    private InputUserData.InputUserDataBuilder inputBuilder;

    private UserDataFactory factoryUnderTest;

    @BeforeEach
    public void setUp(){
        factoryUnderTest = new UserDataFactory(counter);
        inputBuilder = InputUserData.builder()
                .login("dummy")
                .id(12345L);
    }

    /**
     * The method above checks if calculations field in object provided by
     * UserDataFactory provides result of calculations
     * which fulfill the following formula:
     * 6 / followers * (2 + repos)
     */
    @ParameterizedTest
    @MethodSource("provideCalculationsToCheck")
    public void calculationsShouldBeOk(long followers, long repos, BigDecimal expected) throws UserDataFactory.UserDataInvalidException {
        InputUserData input = inputBuilder
                .public_repos(repos)
                .followers(followers)
                .build();
        OutputUserData output = factoryUnderTest.create(input);
        BigDecimal result = new BigDecimal(output.getCalculations());
        assertThat(result).isCloseTo(expected, Offset.offset(DELTA));
    }

    public static Stream<Arguments> provideCalculationsToCheck() {
        return Stream.of(
                Arguments.of(6L,0L, new BigDecimal(2L)),
                Arguments.of(12L, 82, new BigDecimal(42L)),
                Arguments.of(120L, 8, new BigDecimal(0.5)),
                Arguments.of(240L, 8, new BigDecimal(0.25)),
                Arguments.of(240000L, 8, new BigDecimal(0.00025))
        );
    }
}
