package br.com.acertsis.loja.entity;

import java.util.Arrays;
import java.util.List;

public enum BrandCardEnum {
    Visa, Master, Elo, Diners, Aura, JCB, Amex, Discover, Hipercard, Hiper;

    public static List<BrandCardEnum> toList() {
        return Arrays.asList(BrandCardEnum.values());
    }
}
