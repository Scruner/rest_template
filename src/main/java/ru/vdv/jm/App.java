package ru.vdv.jm;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.vdv.jm.entity.Users;


public class App {

    private static final String GET_USERS_ENDPOINT_URL = "http://91.241.64.178:7081/api/users";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();

    private static final Users user = new Users("James", "Brown", (byte) 27);

    public static void main(String[] args) {
        App app = new App();
        app.getResult();
    }

    private void getUsers() {
        ResponseEntity<String> response = restTemplate.getForEntity(GET_USERS_ENDPOINT_URL, String.class);
        headers.set("Cookie", response.getHeaders()
                .get("Set-Cookie")
                .get(0));
    }

    private ResponseEntity createUser() {
        HttpEntity<Users> requestBodyPost = new HttpEntity(user, headers);
        return restTemplate.postForEntity(GET_USERS_ENDPOINT_URL, requestBodyPost, Users.class);
    }

    private ResponseEntity<Users> updateUser() {
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<Users> requestBodyPut = new HttpEntity<>(user, headers);
        return restTemplate.exchange(GET_USERS_ENDPOINT_URL, HttpMethod.PUT, requestBodyPut, Users.class);
    }

    private ResponseEntity<Users> deleteUser() {
        HttpEntity<Users> requestBodyDelete = new HttpEntity<>(headers);
        return restTemplate.exchange(GET_USERS_ENDPOINT_URL + "/" + user.getId(), HttpMethod.DELETE, requestBodyDelete, Users.class);
    }

    public void getResult() {
        getUsers();
        System.out.println("RESULT: "
                + createUser().getBody()
                + updateUser().getBody()
                + deleteUser().getBody());
    }
}

