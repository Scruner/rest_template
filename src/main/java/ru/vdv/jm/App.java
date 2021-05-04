package ru.vdv.jm;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.vdv.jm.entity.Users;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class App {

    private static final String GET_USERS_ENDPOINT_URL = "http://91.241.64.178:7081/api/users";
    private static final String CREATE_USER_ENDPOINT_URL = "http://91.241.64.178:7081/api/users";
    private static final String UPDATE_USER_ENDPOINT_URL = "http://91.241.64.178:7081/api/users/{id}";
    private static final String DELETE_USER_ENDPOINT_URL = "http://91.241.64.178:7081/api/users/{id}";
    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        App restClient = new App();

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
}

