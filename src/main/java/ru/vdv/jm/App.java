package ru.vdv.jm;

import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import ru.vdv.jm.entity.Users;

import java.io.IOException;
import java.util.*;

public class App implements ClientHttpRequestInterceptor {

    private final String headerName;
    private final String headerValue;

    private static final String GET_USERS_ENDPOINT_URL = "http://91.241.64.178:7081/api/users";
    private static final String CREATE_USER_ENDPOINT_URL = "http://91.241.64.178:7081/api/users";
    private static final String UPDATE_USER_ENDPOINT_URL = "http://91.241.64.178:7081/api/users/{id}";
    private static final String DELETE_USER_ENDPOINT_URL = "http://91.241.64.178:7081/api/users/{id}";
    private static RestTemplate restTemplate = new RestTemplate();
    //RestTemplate restTemplate = new RestTemplate();

    public App(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    public static void main(String[] args) {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new App("JSESSIONID=2158003970B72F2BCC0F1FFD97F817DF; Path=/; HttpOnly", "Cookie"));

        restTemplate.setInterceptors(interceptors);
       // App restClient = new App("JSESSIONID=2158003970B72F2BCC0F1FFD97F817DF; Path=/; HttpOnly", "Cookie");

        restClient.getUsers();
        restClient.createUser();
        restClient.updateUser();
        restClient.deleteUser();
    }

    private void getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> rsl = restTemplate.exchange(GET_USERS_ENDPOINT_URL, HttpMethod.GET, entity,
                String.class);
        System.out.println(rsl);
    }

    private void createUser() {
        Users newUser = new Users("James", "Brown", (byte) 27);
        RestTemplate restTemplate = new RestTemplate();
        Users rsl = restTemplate.postForObject(CREATE_USER_ENDPOINT_URL, newUser, Users.class);
        System.out.println(rsl);
    }

    private void updateUser() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "3");
        Users updateUser = new Users("Thomas", "Shelby", (byte) 27);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(UPDATE_USER_ENDPOINT_URL, updateUser, params);
    }

    private void deleteUser() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "1");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(DELETE_USER_ENDPOINT_URL, params);
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        request.getHeaders()
                .set(headerName, headerValue);
        return execution.execute(request, body);
    }
}

