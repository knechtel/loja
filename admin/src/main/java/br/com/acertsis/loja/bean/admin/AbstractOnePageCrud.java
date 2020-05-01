/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import org.springframework.data.repository.CrudRepository;

/**
 *
 *
 */
public abstract class AbstractOnePageCrud<T> implements Serializable {

    private String title;

    public void save(T entity) {
        try {
            getRepository().save(entity);
            addMensagemSucesso(getSaveSuccessMessage());
        } catch (Exception ex) {
            Logger.getLogger(AbstractCrudMbAntiga.class.getName()).log(Level.SEVERE, null, ex);
            addMensagemErro(getSaveErrorMessage());
        }
    }

    protected String getSaveErrorMessage() {
        return "Erro ao salvar registro.";
    }

    protected String getSaveSuccessMessage() {
        return "Salvo  com sucesso.";
    }

    public void delete(Long id) {
        try {
            getRepository().deleteById(id);
            addMensagemSucesso("Excluído com sucesso.");
        } catch (Exception ex) {
            Logger.getLogger(AbstractCrudMbAntiga.class.getName()).log(Level.SEVERE, null, ex);
            addMensagemErro("Erro ao excluir.");
        }
    }

    protected abstract CrudRepository getRepository();

    public void addMensagemSucessoFlash(String message) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.setKeepMessages(true);

        FacesMessage fm = new FacesMessage("", message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage("", fm);
    }

    public void addMensagemErroFlash(String message) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.setKeepMessages(true);

        FacesMessage fm = new FacesMessage("", message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage("", fm);
    }

    public void addMensagemSucesso(String message) {
        FacesMessage fm = new FacesMessage("", message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage("", fm);
    }

    public void addMensagemErro(String message) {
        FacesMessage fm = new FacesMessage("", message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage("", fm);
    }

    protected abstract void clearModel();

    protected abstract void init();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
