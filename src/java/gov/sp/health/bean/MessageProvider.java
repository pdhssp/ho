/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sp.health.bean;

import gov.sp.health.facade.WebUserFacade;
import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Buddhika
 */
@ManagedBean
@SessionScoped
public class MessageProvider implements Serializable {

    public MessageProvider() {
    }
    @ManagedProperty(value = "#{sessionController}")
    SessionController sessionController;
    private ResourceBundle bundle;

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public ResourceBundle resetBundle() {
        Locale myLocale;
        myLocale = new Locale("si", "LK");
        try {
            System.out.println("Traying to get resource bundle in reset bundle");
//            System.out.println("defLocale is " + getSessionController().toString());
//            System.out.println("Session Controller is " + getSessionController().getDefLocale());
//
//            System.out.println("Session Controller is " + getSessionController().getDefLocale());
//            System.out.println("defLocale is " + getSessionController().toString());
            if (getSessionController() == null) {
                System.out.println("Session controller is NULL");
                myLocale = new Locale("en");
            } else if (getSessionController().getDefLocale().equals("si_LK")) {
                myLocale = new Locale("si", "LK");
            } else if (getSessionController().getDefLocale().equals("ta_LK")) {
                myLocale = new Locale("ta", "LK");
            } else {
                myLocale = new Locale("en");
            }
            System.out.println(myLocale.toString());
//            FacesContext.getCurrentInstance().getViewRoot().setLocale(myLocale);
            System.out.println("1");
            FacesContext context = FacesContext.getCurrentInstance();
            System.out.println("2");
            context.getViewRoot().setLocale(myLocale);
            System.out.println("3");
            bundle = context.getApplication().getResourceBundle(context, "labels");
            System.out.println("4");
            return bundle;
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            bundle = context.getApplication().getResourceBundle(context, "labels");
            System.out.println("Error at line 54 is " + e.getMessage());
            return bundle;
        }
    }

    public ResourceBundle getBundle() {
        if (bundle == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            bundle = context.getApplication().getResourceBundle(context, "labels");
        }
        return bundle;
    }

//    public ResourceBundle getBundle() {
//        Locale myLocale;
//        myLocale = new Locale("si", "LK");
//        try {
//            System.out.println("Traying to get resource bundle");
//            System.out.println("defLocale is " + getSessionController().toString());
//            System.out.println("Session Controller is " + getSessionController().getDefLocale());
//
//            if (bundle == null || !bundle.getLocale().equals(new Locale(getSessionController().getDefLocale()))) {
//                System.out.println("Session Controller is " + getSessionController().getDefLocale());
//                System.out.println("defLocale is " + getSessionController().toString());
//                if (getSessionController().getDefLocale().equals("si_LK")) {
//                    myLocale = new Locale("si", "LK");
//                } else if (getSessionController().getDefLocale().equals("ta_LK")) {
//                    myLocale = new Locale("ta", "LK");
//                } else {
//                    myLocale = new Locale("en");;
//                }
//                FacesContext.getCurrentInstance().getViewRoot().setLocale(myLocale);
//                FacesContext context = FacesContext.getCurrentInstance();
//                bundle = context.getApplication().getResourceBundle(context, "labels");
//                return bundle;
//            } else {
//                System.out.println("Bundle is NOT null");
//                return bundle;
//            }
//        } catch (Exception e) {
//            FacesContext context = FacesContext.getCurrentInstance();
//            bundle = context.getApplication().getResourceBundle(context, "labels");
//            System.out.println("Error at line 54 is " + e.getMessage());
//            return bundle;
//        }
//    }
    public String getValue(String key) {

        Locale myLocale;
        myLocale = new Locale("si", "LK");
        if (getSessionController() == null) {
            System.out.println("Session controller is NULL");
            myLocale = new Locale("en");
        } else if (getSessionController().getDefLocale().equals("si_LK")) {
            myLocale = new Locale("si", "LK");
        } else if (getSessionController().getDefLocale().equals("ta_LK")) {
            myLocale = new Locale("ta", "LK");
        } else {
            myLocale = new Locale("en");
        }



        if (!getBundle().getLocale().equals(myLocale)) {
            bundle = null;
        }
        
        String result = null;
        try {
            result = getBundle().getString(key);
        } catch (MissingResourceException e) {
            result = "???" + key + "??? not found";
        }
        return result;
    }
}
