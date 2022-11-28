package org.example.entities;

import lombok.*;

@ToString
@AllArgsConstructor
public class User {

    @Getter
    private int id;
    @Getter
    @Setter
    private String login;
    private Integer money;
    @Getter
    @Setter
    private String password;
    private String city;
    private String country;
    public User (int id, String login, String password) {
        this.login = login;
        this.password = password;
    }
    public User (int id, String login, String password, Integer money) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.money = money;
    }
    public User (Integer id ,String login, String password, Integer money, String city, String country) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.money = money;
        this.city = city;
        this.country = country;

    }
    public User (String login, String password, Integer money) {
        this.login = login;
        this.password = password;

    }
    @Override
    public String toString() {
        return "\n" + "id=" + id + "\n" + "name=" + login + "\n" +
                "password=" + password + "\n" + "money=" + money + "\n" + "city=" + city + "\n" + "country=" + country;
    }
}
