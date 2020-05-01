package br.com.acertsis.loja.service;
/**
 * Interface para geração de um boleto.
 *
 * @param <R> Retorno do metodo que gera o boleto
 * @param <V> Os dados de entrada utilizado para gerar o boleto.
 */
public interface BoletoService<R, V> {

    /**
     * Metedo responsavel por gerar o boleto.
     *
     * @param dados Dados de entrada de informação.
     * @return Objeto de retorno.
     */
    public R emitirBoleto(V dados);
}
