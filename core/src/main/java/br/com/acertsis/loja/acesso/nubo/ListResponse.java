package br.com.acertsis.loja.acesso.nubo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListResponse {
    @SerializedName("data")
    private List<Account> data;

}
