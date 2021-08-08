package link.tomasztomczyk.userinfoapi.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class UserDataProxyApplicationTests {
    private static final String MESSAGE = "Hello, world!";
    private static final String GIVEN_USER_NAME = "user12345";

    @LocalServerPort
    private int port;

    @MockBean
    private WebTarget targetMock;
    @Mock
    private Invocation.Builder invocationMock;
    private Invocation.Builder serviceUnderTestRequest;

    @BeforeEach
    public void setUp(){
        serviceUnderTestRequest = ClientBuilder.newClient()
                .target("http://localhost:" + port)
                .path("users")
                .path(GIVEN_USER_NAME)
                .request();
        given(targetMock.path(any(String.class))).willReturn(targetMock);
        given(targetMock.request()).willReturn(invocationMock);
        given(invocationMock.get()).willReturn(Response.ok(MESSAGE).build());
    }

    @Test
    void apiUsesGivenUserNameAsAResourcePath() {
        serviceUnderTestRequest.get();
        then(targetMock).should().path(GIVEN_USER_NAME);
    }

    @Test
    void apiReturnsResponseFromTheDownstreamService() {
        var result = serviceUnderTestRequest.get();
        assertEquals(MESSAGE, result.readEntity(String.class));
    }
}

