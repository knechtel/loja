package br.com.acertsis.loja.acesso.nubo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Account {
    @SerializedName("id")
    private Long id;
    @SerializedName("company_name")
    private String companyName;
    @SerializedName("subdomain")
    private String subdomain;
    @SerializedName("status")
    private Integer status;
    @SerializedName("active")
    private Boolean active;
    @SerializedName("confirmed")
    private Boolean confirmed;
    @SerializedName("users")
    private List<User> users;
    @SerializedName("licenses")
    private List<License> licenses;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User>users) {
        this.users = users;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", subdomain='" + subdomain + '\'' +
                ", status=" + status +
                ", active=" + active +
                ", confirmed=" + confirmed +
                ", users=" + users +
                ", licenses=" + licenses +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
