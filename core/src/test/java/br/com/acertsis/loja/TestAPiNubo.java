package br.com.acertsis.loja;

import br.com.acertsis.loja.acesso.ApiNubo;
import br.com.acertsis.loja.acesso.nubo.*;
import br.com.acertsis.loja.acesso.nubo.request.ApiNuboError;
import br.com.acertsis.loja.acesso.nubo.request.RequestException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAPiNubo {

    @Test
    public void testAutenticacao()  {

        ApiNubo api = new ApiNubo();
        try {
            api.authenticate(new Login("api@acertsis.com.br","acertsis123@@",
                                                                             "acertsis"));
            ListResponse list = api.listAccounts();

            LicensePackage licensePackage = new LicensePackage();
            //licensePackage.setId(36L);
            //licensePackage.setLicenseServiceId(5);
            licensePackage.setVisible(true);
            //LicensePackage licensePackage2 = new LicensePackage();
            //licensePackage2.setId(8);
            //licensePackage2.setLicenseServiceId(4);
            //licensePackage2.setVisible(false);
            //licensePackage.setDescription(null);
            //licensePackage.setName("ACNF Prata");
            License license = new License();
            license.setLicensePackage(licensePackage);
            //license.setLicenseServiceId(36);
            //License license2 = new License();
            //license2.setLicensePackage(licensePackage2);
            List<License> licenses = new ArrayList<License>();
            Account account = new Account();
            account.setCompanyName("TESTE API NUBO LOJA ");
            account.setSubdomain("testeapinuboloja");
            account.setUsername("fernando.maciel@FIM.com.br");
            account.setPassword("fernando123@@");
            account.setLicenses(new ArrayList<>());
            account.getLicenses().add(license);
            //account.getLicenses().add(license2);
           // Account a = api.createAccount(account);


            System.out.print("teste");
        } catch (RequestException ex){
            ApiNuboError error = ex.getError();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("teste");
    }
}
