package com.yessumtorah.brandeetsapp;

import com.google.gson.annotations.SerializedName;

public class LoginInstance {

    @SerializedName("email")
    private String email;

    public LoginInstance(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
