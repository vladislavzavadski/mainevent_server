package by.bsuir.mainevent.domain;

import lombok.Data;

/**
 * Created by ulza1116 on 6/19/2017.
 */
@Data
public class User {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String photoUrl;

}
