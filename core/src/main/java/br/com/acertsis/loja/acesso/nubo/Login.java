package br.com.acertsis.loja.acesso.nubo;

public class Login {

    private final String user;
    private final String password;
    private final String subdominio;

    public Login(String user, String password, String subdominio) {
        this.user = user;
        this.password = password;
        this.subdominio = subdominio;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getSubdominio() {
        return subdominio;
    }
}
