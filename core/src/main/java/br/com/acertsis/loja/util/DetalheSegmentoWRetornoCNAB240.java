package br.com.acertsis.loja.util;

public class DetalheSegmentoWRetornoCNAB240 { 

    Integer codigoErro = 0;

    public DetalheSegmentoWRetornoCNAB240()  {
    }

    public Integer getcodigoErro() {
        return codigoErro;
    }

    public void setcodigoErro(Integer codigoErro) {
        this.codigoErro = codigoErro;
    }

    public void LerDetalheSegmentoWRetornoCNAB240(String registro)
    {
        try
        {
            if (registro.substring(13, 1) != "W")
                throw new Exception("Registro inválido. O detalhe não possuí as características do segmento W.");

            if (!registro.substring(28, 3).trim().equals(""))
                codigoErro = Integer.parseInt(registro.substring(28, 3));

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
