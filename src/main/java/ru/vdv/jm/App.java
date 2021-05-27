package ru.vdv.jm;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.vdv.jm.entity.Users;

import java.util.Collections;

public class App {

    private static final String URL = "http://91.241.64.178:7081/api/users";
    private static final String URLDEL = "http://91.241.64.178:7081/api/users/3";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();
    private static final Users user = new Users(3L,"James", "Brown", (byte) 27);
    private static final Users userUpdate = new Users(3L, "Thomas", "Shelby", (byte) 23);


    public static void main(String[] args) {
        App app = new App();
        String header = app.getAllUsers();
        app.createUser(header);
        app.updateUser(header);
        app.deleteUser(header);
    }

    public String getAllUsers() {
        HttpEntity<String> entity = new HttpEntity<>("Cookie", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        return responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    }

    public void createUser(String header) {
        headers.add("Cookie", header);
        HttpEntity<Users> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        System.out.print(responseEntity.getBody());
    }

    public void updateUser(String header) {
        headers.add("Cookie", header);
        HttpEntity<Users> entity = new HttpEntity<>(userUpdate, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        System.out.print(responseEntity.getBody());
    }

    public void deleteUser(String header) {
        headers.add("Cookie", header);
        HttpEntity<Users> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URLDEL, HttpMethod.DELETE, entity, String.class);
        System.out.println(responseEntity.getBody());
    }
}