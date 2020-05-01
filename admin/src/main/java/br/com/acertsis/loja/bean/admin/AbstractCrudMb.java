/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acertsis.loja.bean.admin;

import br.com.acertsis.loja.service.CrudService;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import org.omnifaces.util.Faces;

public abstract class AbstractCrudMb<T> implements Serializable {

    public static final int STATE_LIST = 1;
    public static final int STATE_CREATE = 2;
    public static final int STATE_EDIT = 3;
    private String title;
    private int state = 0;
    private T model;

    public void save() {
        try {
            getService().save(model);
            addMensagemSucesso(getSaveSuccessMessage());
            list();
        } catch (Exception ex) {
            Logger.getLogger(AbstractCrudMb.class.getName()).log(Level.SEVERE, null, ex);
            addMensagemErro(getSaveErrorMessage());
        }
    }

    protected String getSaveErrorMessage() {
        return "Erro ao ".concat((state == STATE_CREATE) ? "salvar" : "atualizar").concat(" registro.");
    }

    protected String getSaveSuccessMessage() {
        return (state == STATE_CREATE) ? "Salvo" : "Atualizado".concat(" com sucesso.");
    }

    public void create() {
        clearModel();
        this.title = "Novo";
        this.state = STATE_CREATE;
    }

    public void edit(Long id) {
        clearModel();
        this.title = "Editar";
        this.state = STATE_EDIT;
        model = (T) getService().findById(id).get();
    }

    public void delete(Long id) {
        try {
            getService().deleteById(id);
            addMensagemSucesso("Excluído com sucesso.");
        } catch (Exception ex) {
            Logger.getLogger(AbstractCrudMb.class.getName()).log(Level.SEVERE, null, ex);
            addMensagemErro("Erro ao excluir.");
        }
        list();
    }

    public void list() {
        this.title = "Listar";
        this.state = STATE_LIST;
    }

    protected abstract CrudService getService();

    public void addMensagemSucessoFlash(String message) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.setKeepMessages(true);

        FacesMessage fm = new FacesMessage("", message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public void addMensagemErroFlash(String message) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.setKeepMessages(true);

        FacesMessage fm = new FacesMessage("", message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public void addMensagemSucesso(String message) {
        FacesMessage fm = new FacesMessage("", message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public void addMensagemErro(String message) {
        FacesMessage fm = new FacesMessage("", message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    protected abstract void clearModel();

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static int getSTATE_LIST() {
        return STATE_LIST;
    }

    public static int getSTATE_CREATE() {
        return STATE_CREATE;
    }

    public static int getSTATE_EDIT() {
        return STATE_EDIT;
    }

    /**
     * Funciona apenas com campos que implementam EditableValueHolder.
     *
     * @param id
     */
    public void marcarElementoComoInvalido(String id) {
        UIComponent uIComponent = Faces.getViewRoot().findComponent(id);
        if (uIComponent != null) {
            if (uIComponent instanceof EditableValueHolder) {
                EditableValueHolder i = (EditableValueHolder) uIComponent;
                i.setValid(false);
            }
        }
    }
}
