/*
 * MSc(Biomedical Informatics) Project
 * 
 * Development and Implementation of a Web-based Combined Data Repository of 
 Genealogical, Clinical, Laboratory and Genetic Data 
 * and
 * a Set of Related Tools
 */
package gov.sp.health.bean;

import gov.sp.health.facade.AmpFacade;
import gov.sp.health.entity.Amp;
import gov.sp.health.entity.Ampp;
import gov.sp.health.entity.Manufacturer;
import gov.sp.health.entity.StrengthUnit;
import gov.sp.health.entity.Supplier;
import gov.sp.health.entity.VtmInAmp;
import gov.sp.health.facade.MedicineGroupFacade;
import gov.sp.health.facade.VtmFacade;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Dr. M. H. B. Ariyaratne, MBBS, PGIM Trainee for MSc(Biomedical
 * Informatics)
 */
@ManagedBean
@SessionScoped
public final class AmpController  implements Serializable {

    @EJB
    private AmpFacade ejbFacade;
    @EJB
            MedicineGroupFacade medicineGroupFacade;
    
    SessionController sessionController = new SessionController();
    List<Amp> lstItems;
    private Amp current;
    private DataModel<Amp> items = null;
    private int selectedItemIndex;
    boolean selectControlDisable = false;
    boolean modifyControlDisable = true;
    String selectText = "";

    String strVtm;
    String strAtm;
    StrengthUnit strengthUnit;
    Ampp ampp;
    List<Ampp> lstAmpps;
    DataModel<Ampp> ampps;
    VtmInAmp vtmInAmp;
    List<VtmInAmp> lstVtmsInAmp;
    DataModel<VtmInAmp> vtmsInAmp;
    Manufacturer manufacturer;
    Supplier supplier;

    public Ampp getAmpp() {
        return ampp;
    }

    public void setAmpp(Ampp ampp) {
        this.ampp = ampp;
    }

    public DataModel<Ampp> getAmpps() {
        return ampps;
    }

    public void setAmpps(DataModel<Ampp> ampps) {
        this.ampps = ampps;
    }

    public List<Ampp> getLstAmpps() {
        return lstAmpps;
    }

    public void setLstAmpps(List<Ampp> lstAmpps) {
        this.lstAmpps = lstAmpps;
    }

    public List<VtmInAmp> getLstVtmsInAmp() {
        return lstVtmsInAmp;
    }

    public void setLstVtmsInAmp(List<VtmInAmp> lstVtmsInAmp) {
        this.lstVtmsInAmp = lstVtmsInAmp;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public MedicineGroupFacade getMedicineGroupFacade() {
        return medicineGroupFacade;
    }

    public void setMedicineGroupFacade(MedicineGroupFacade medicineGroupFacade) {
        this.medicineGroupFacade = medicineGroupFacade;
    }

    public String getStrAtm() {
        return strAtm;
    }

    public void setStrAtm(String strAtm) {
        this.strAtm = strAtm;
    }

    public String getStrVtm() {
        return strVtm;
    }

    public void setStrVtm(String strVtm) {
        this.strVtm = strVtm;
    }

    public StrengthUnit getStrengthUnit() {
        return strengthUnit;
    }

    public void setStrengthUnit(StrengthUnit strengthUnit) {
        this.strengthUnit = strengthUnit;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public VtmInAmp getVtmInAmp() {
        return vtmInAmp;
    }

    public void setVtmInAmp(VtmInAmp vtmInAmp) {
        this.vtmInAmp = vtmInAmp;
    }

    public DataModel<VtmInAmp> getVtmsInAmp() {
        return vtmsInAmp;
    }

    public void setVtmsInAmp(DataModel<VtmInAmp> vtmsInAmp) {
        this.vtmsInAmp = vtmsInAmp;
    }

    

    public AmpFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(AmpFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

   
    public AmpController() {
    }

    public List<Amp> getLstItems() {
        return getFacade().findBySQL("Select d From Amp d");
    }

    public void setLstItems(List<Amp> lstItems) {
        this.lstItems = lstItems;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public Amp getCurrent() {
        if (current == null) {
            current = new Amp();
        }
        return current;
    }

    public void setCurrent(Amp current) {
        this.current = current;
    }

    private AmpFacade getFacade() {
        return ejbFacade;
    }

    public DataModel<Amp> getItems() {
        items = new ListDataModel(getFacade().findAll("name", true));
        return items;
    }

    public static int intValue(long value) {
        int valueInt = (int) value;
        if (valueInt != value) {
            throw new IllegalArgumentException(
                    "The long value " + value + " is not within range of the int type");
        }
        return valueInt;
    }

    public DataModel searchItems() {
        recreateModel();
        if (items == null) {
            if (selectText.equals("")) {
                items = new ListDataModel(getFacade().findAll("name", true));
            } else {
                items = new ListDataModel(getFacade().findAll("name", "%" + selectText + "%",
                        true));
                if (items.getRowCount() > 0) {
                    items.setRowIndex(0);
                    current = (Amp) items.getRowData();
                    Long temLong = current.getId();
                    selectedItemIndex = intValue(temLong);
                } else {
                    current = null;
                    selectedItemIndex = -1;
                }
            }
        }
        return items;

    }

    public Amp searchItem(String itemName, boolean createNewIfNotPresent) {
        Amp searchedItem = null;
        items = new ListDataModel(getFacade().findAll("name", itemName, true));
        if (items.getRowCount() > 0) {
            items.setRowIndex(0);
            searchedItem = (Amp) items.getRowData();
        } else if (createNewIfNotPresent) {
            searchedItem = new Amp();
            searchedItem.setName(itemName);
            searchedItem.setCreatedAt(Calendar.getInstance().getTime());
            searchedItem.setCreater(sessionController.loggedUser);
            getFacade().create(searchedItem);
        }
        return searchedItem;
    }

    private void recreateModel() {
        items = null;
    }

    public void prepareSelect() {
        this.prepareModifyControlDisable();
    }

    public void prepareEdit() {
        if (current != null) {
            selectedItemIndex = intValue(current.getId());
            this.prepareSelectControlDisable();
        } else {
            JsfUtil.addErrorMessage(new MessageProvider().getValue("nothingToEdit"));
        }
    }

    public void prepareAdd() {
        selectedItemIndex = -1;
        current = new Amp();
        this.prepareSelectControlDisable();
    }

    public void saveSelected() {
        if (sessionController.getPrivilege().isMsEdit()==false){
            JsfUtil.addErrorMessage("You are not autherized to make changes to any content");
            return;
        }            
        if (selectedItemIndex > 0) {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(new MessageProvider().getValue("savedOldSuccessfully"));
        } else {
            current.setCreatedAt(Calendar.getInstance().getTime());
            current.setCreater(sessionController.loggedUser);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(new MessageProvider().getValue("savedNewSuccessfully"));
        }
        this.prepareSelect();
        recreateModel();
        getItems();
        selectText = "";
        selectedItemIndex = intValue(current.getId());
    }

    public void addDirectly() {
        JsfUtil.addSuccessMessage("1");
        try {

            current.setCreatedAt(Calendar.getInstance().getTime());
            current.setCreater(sessionController.loggedUser);

            getFacade().create(current);
            JsfUtil.addSuccessMessage(new MessageProvider().getValue("savedNewSuccessfully"));
            current = new Amp();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error");
        }

    }

    public void cancelSelect() {
        this.prepareSelect();
    }

    public void delete() {
        if (current != null) {
            current.setRetired(true);
            current.setRetiredAt(Calendar.getInstance().getTime());
            current.setRetirer(sessionController.loggedUser);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(new MessageProvider().getValue("deleteSuccessful"));
        } else {
            JsfUtil.addErrorMessage(new MessageProvider().getValue("nothingToDelete"));
        }
        recreateModel();
        getItems();
        selectText = "";
        selectedItemIndex = -1;
        current = null;
        this.prepareSelect();
    }

    public boolean isModifyControlDisable() {
        return modifyControlDisable;
    }

    public void setModifyControlDisable(boolean modifyControlDisable) {
        this.modifyControlDisable = modifyControlDisable;
    }

    public boolean isSelectControlDisable() {
        return selectControlDisable;
    }

    public void setSelectControlDisable(boolean selectControlDisable) {
        this.selectControlDisable = selectControlDisable;
    }

    public String getSelectText() {
        return selectText;
    }

    public void setSelectText(String selectText) {
        this.selectText = selectText;
        searchItems();
    }

    public void prepareSelectControlDisable() {
        selectControlDisable = true;
        modifyControlDisable = false;
    }

    public void prepareModifyControlDisable() {
        selectControlDisable = false;
        modifyControlDisable = true;
    }

    @FacesConverter(forClass = Amp.class)
    public static class AmpControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AmpController controller = (AmpController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "ampController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Amp) {
                Amp o = (Amp) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type "
                        + object.getClass().getName() + "; expected type: " + AmpController.class.getName());
            }
        }
    }
}
