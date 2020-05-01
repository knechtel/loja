package br.com.acertsis.loja.acesso.nubo;

import com.google.gson.annotations.SerializedName;

public class Credentials {

    @SerializedName("account_id")
    private Long accountId;
    @SerializedName("slug_account_type")
    private String slugAccountType;
    @SerializedName("token")
    private String token;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getSlugAccountType() {
        return slugAccountType;
    }

    public void setSlugAccountType(String slugAccountType) {
        this.slugAccountType = slugAccountType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
