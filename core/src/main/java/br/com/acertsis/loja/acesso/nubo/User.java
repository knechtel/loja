package br.com.acertsis.loja.acesso.nubo;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class User {

    @SerializedName("id")
    private Long id;
    @SerializedName("active")
    private boolean active;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
   // @SerializedName("roles")
    //private Roles roles;
    @SerializedName("utc_last_login")
    private String lasLogin;
    @SerializedName("utc_creation_date")
    private String dtCreate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLasLogin() {
        return lasLogin;
    }

    public void setLasLogin(String lasLogin) {
        this.lasLogin = lasLogin;
    }

    public String getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(String dtCreate) {
        this.dtCreate = dtCreate;
    }
}
