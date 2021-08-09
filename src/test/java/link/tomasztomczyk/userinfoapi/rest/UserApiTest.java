package link.tomasztomczyk.userinfoapi.rest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class UserApiTest{
    private GithubUserDTO givenRespBody;
    private static final String GIVEN_USER_NAME = "user12345";
    private static final int id = 0xC00FEE;
    private static final String login = "DUMMY LOGIN";

    @LocalServerPort
    private int port;
    @MockBean
    private WebTarget targetMock;
    @Mock
    private Invocation.Builder invocationMock;
    @Mock
    private Response responseMock;

    private Invocation.Builder testInvocation;

    @BeforeEach
    public void setUp() {
        testInvocation = ClientBuilder.newClient()
                .target("http://localhost:" + port)
                .path("users")
                .path(GIVEN_USER_NAME)
                .request();

        given(targetMock.request(any(String.class))).willReturn(invocationMock);
        given(targetMock.request()).willReturn(invocationMock);
        given(targetMock.path(any(String.class))).willReturn(targetMock);
        given(invocationMock.get()).willReturn(responseMock);
        given(responseMock.getStatus()).willReturn(200);

        givenRespBody = new GithubUserDTO();
        givenRespBody.setId(id);
        givenRespBody.setLogin(login);
    }

    @Test
    void apiUsesGivenUserNameAsAResourcePath() {
        given(responseMock.readEntity(GithubUserDTO.class)).willReturn(givenRespBody);
        testInvocation.get();
        then(targetMock).should().path(GIVEN_USER_NAME);
    }

    @Test
    void apiReturnsResponseFromTheDownstreamService() throws JSONException {
        given(responseMock.readEntity(GithubUserDTO.class)).willReturn(givenRespBody);
        var resp = testInvocation.get();
        var result = resp.readEntity(String.class);

        assertEquals(200, resp.getStatus());

        var expectedResult = new JSONObject();
        expectedResult.put("id", Long.toString(id));
        expectedResult.put("login", login);
        JSONAssert.assertEquals(expectedResult.toString(), result, JSONCompareMode.LENIENT);
    }

    @Test
    void apiReturnsBadGatewayIfTheDownstreamServiceReturnsNonParsableResponse() {
        given(responseMock.getStatus()).willReturn(200);
        given(responseMock.readEntity(GithubUserDTO.class)).willThrow(new ProcessingException(""));

        var resp = testInvocation.get();
        assertEquals(502, resp.getStatus());
    }

    @Test
    void apiReturnsBadGatewayIfTheDownstreamServiceReturnsResponseWithNoLogin() {
        given(responseMock.getStatus()).willReturn(200);
        given(responseMock.readEntity(GithubUserDTO.class)).willReturn(new GithubUserDTO());

        var resp = testInvocation.get();
        assertEquals(502, resp.getStatus());
    }

    @Test
    void apiReturnsBadGatewayIfTheDownstreamServiceReturnsError() {
        given(responseMock.getStatus()).willReturn(500);

        var resp = testInvocation.get();
        assertEquals(502, resp.getStatus());
    }

    @Test
    void apiReturnsUserErrorsToTheApiUser() {
        given(responseMock.getHeaders()).willReturn(new MultivaluedHashMap<>());
        given(responseMock.getStatus()).willReturn(403);
        var resp = testInvocation.get();
        assertEquals(403, resp.getStatus());
    }
}

