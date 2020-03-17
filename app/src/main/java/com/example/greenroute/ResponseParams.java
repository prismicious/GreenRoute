package com.example.greenroute;

public class ResponseParams {
    String first_name;
    String last_name;
    String avatar;

    public ResponseParams(String first_name, String last_name, String avatar, String token) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    String token;

    @Override
    public String toString() {
        return "ResponseParams{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
