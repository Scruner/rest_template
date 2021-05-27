package ru.vdv.jm;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.vdv.jm.entity.Users;

public class App {

    private static final String URL = "http://91.241.64.178:7081/api/users";
    private static final String URLDEL = "http://91.241.64.178:7081/api/users/3";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final HttpHeaders HEADERS = new HttpHeaders();
    private static final Users USER = new Users(3L,"James", "Brown", (byte) 27);
    private static final Users USER_UPDATE = new Users(3L, "Thomas", "Shelby", (byte) 23);


    public static void main(String[] args) {
        App app = new App();
        String header = app.getAllUsers();
        HEADERS.add("Cookie", header);
        app.createUser();
        app.updateUser();
        app.deleteUser();
    }

    public String getAllUsers() {
        HttpEntity<String> entity = new HttpEntity<>("Cookie", HEADERS);
        ResponseEntity<String> responseEntity = REST_TEMPLATE.exchange(URL, HttpMethod.GET, entity, String.class);
        return responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    }

    public void createUser() {
        HttpEntity<Users> entity = new HttpEntity<>(USER, HEADERS);
        ResponseEntity<String> responseEntity = REST_TEMPLATE.exchange(URL, HttpMethod.POST, entity, String.class);
        System.out.print(responseEntity.getBody());
    }

    public void updateUser() {
        HttpEntity<Users> entity = new HttpEntity<>(USER_UPDATE, HEADERS);
        ResponseEntity<String> responseEntity = REST_TEMPLATE.exchange(URL, HttpMethod.PUT, entity, String.class);
        System.out.print(responseEntity.getBody());
    }

    public void deleteUser() {
        HttpEntity<Users> entity = new HttpEntity<>(HEADERS);
        ResponseEntity<String> responseEntity = REST_TEMPLATE.exchange(URLDEL, HttpMethod.DELETE, entity, String.class);
        System.out.println(responseEntity.getBody());
    }
}