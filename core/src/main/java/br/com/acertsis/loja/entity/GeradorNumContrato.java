package br.com.acertsis.loja.entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GeradorNumContrato implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefixo = Integer.toString(LocalDate.now().getYear());

        Connection connection = session.connection();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select max(id_contrato) as id from acnf_loja.contrato\n"
                    + "WHERE id_contrato like '" + prefixo + "%'");
            if (rs.next()) {
                String id = rs.getString(1);
                String generatedId = null;
                if (id == null) {
                    generatedId = prefixo + String.format("%07d", Long.parseLong("1"));
                } else {
                    generatedId = String.format("%07d", Long.parseLong(id) + 1);
                }
                return Long.parseLong(generatedId);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
