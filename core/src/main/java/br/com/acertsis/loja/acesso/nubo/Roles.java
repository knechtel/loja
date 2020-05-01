package br.com.acertsis.loja.acesso.nubo;

import com.google.gson.annotations.SerializedName;

public class Roles {
    @SerializedName("id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
