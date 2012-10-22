/*
 * MSc(Biomedical Informatics) Project
 * 
 * Development and Implementation of a Web-based Combined Data Repository of 
 Genealogical, Clinical, Laboratory and Genetic Data 
 * and
 * a Set of Related Tools
 */
package gov.sp.health.bean;

import gov.sp.health.entity.*;
import gov.sp.health.facade.BookFacade;
import gov.sp.health.facade.InstitutionFacade;
import gov.sp.health.facade.ItemUnitHistoryFacade;
import gov.sp.health.facade.LocationFacade;
import gov.sp.health.facade.UnitFacade;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.jboss.weld.logging.messages.JsfMessage;

/**
 *
 * @author Dr. M. H. B. Ariyaratne, MBBS, PGIM Trainee for MSc(Biomedical
 * Informatics)
 */
@ManagedBean
@SessionScoped
public final class BookController implements Serializable {

    @EJB
    private BookFacade ejbFacade;
    @EJB
    ItemUnitHistoryFacade ithFacade;
    
    @ManagedProperty(value = "#{sessionController}")
    SessionController sessionController;
    @ManagedProperty(value = "#{imageController}")
    ImageController imageController;
    List<Book> lstItems;
    private Book current;
    private DataModel<Book> items = null;
    private DataModel<Book> itemsIns = null;
    private DataModel<Book> itemsUni = null;
    private DataModel<Book> itemsLoc = null;
    private DataModel<Book> itemsPer = null;
    //
    //
    DataModel<Unit> units;
    DataModel<Institution> institutions;
    DataModel<Location> locations;
    //
    //
    //
    private int selectedItemIndex;
    boolean selectControlDisable = false;
    boolean modifyControlDisable = true;
    String selectText = "";
    String selectName = "";
    Unit unit;
    Location location;
    Person person;
    Institution institution;
//
    //
    @EJB
    UnitFacade unitFacade;
    @EJB
    InstitutionFacade institutionFacade;

    public void issue(){
        if (current==null){
            JsfUtil.addErrorMessage("Please select a book");
            return;
        }
        if (person==null){
            JsfUtil.addErrorMessage("Please select a person");
            return;
        }
        ItemUnitHistory hx = new ItemUnitHistory();
        
        hx.setCreatedAt(Calendar.getInstance().getTime());
        hx.setCreater(sessionController.loggedUser);
        
        
        hx.setItem(null);
        hx.setItemUnit(current);
        
        hx.setBeforeQty(1.0);
        
        
    }

    public ItemUnitHistoryFacade getIthFacade() {
        return ithFacade;
    }

    public void setIthFacade(ItemUnitHistoryFacade ithFacade) {
        this.ithFacade = ithFacade;
    }

    
    
    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectText = "";
        this.selectName = selectName;
    }

    public InstitutionFacade getInstitutionFacade() {
        return institutionFacade;
    }

    public void setInstitutionFacade(InstitutionFacade institutionFacade) {
        this.institutionFacade = institutionFacade;
    }

    public UnitFacade getUnitFacade() {
        return unitFacade;
    }

    public void setUnitFacade(UnitFacade unitFacade) {
        this.unitFacade = unitFacade;
    }

    public DataModel<Location> getLocations() {
        if (getUnit() != null) {
            locations = new ListDataModel(getFacade().findBySQL("select l from Location l where l.retired=false and l.unit.id = " + getUnit().getId()));
            return locations;
        } else {
            return null;
        }
    }

    public void setLocations(DataModel<Location> locations) {
        this.locations = locations;
    }

    public DataModel<Institution> getInstitutions() {
        String temSQL;
        if (sessionController.getPrivilege().getRestrictedInstitution() != null) {
            temSQL = "SELECT i FROM Institution i WHERE i.retired=false AND i.id = " + sessionController.getPrivilege().getRestrictedInstitution().getId();
        } else {
            temSQL = "SELECT i FROM Institution i WHERE i.retired=false ORDER BY i.name";
        }
        return new ListDataModel<Institution>(getInstitutionFacade().findBySQL(temSQL));
    }

    public void setInstitutions(DataModel<Institution> institutions) {
        this.institutions = institutions;
    }

    public DataModel<Unit> getUnits() {
        String temSql;
        if (sessionController.getPrivilege().getRestrictedUnit() != null) {
            temSql = "SELECT u from Unit u where u.id = " + sessionController.getPrivilege().getRestrictedUnit().getId();
        } else if (getInstitution() != null) {
            temSql = "select u from Unit u where u.retired=false and u.institution.id = " + getInstitution().getId() + " order by u.name";
        } else {
            return null;
        }
        return new ListDataModel<Unit>(getUnitFacade().findBySQL(temSql));
    }

    public void setUnits(DataModel<Unit> units) {
        this.units = units;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public BookController() {
    }

    public BookFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(BookFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public List<Book> getLstItems() {
        return getFacade().findBySQL("Select d From Book d WHERE d.retired=false ORDERBY i.name");
    }

    public void setLstItems(List<Book> lstItems) {
        this.lstItems = lstItems;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public Book getCurrent() {
        if (current == null) {
            current = new Book();
        }
        return current;
    }

    public void setCurrent(Book current) {
        this.current = current;
    }

    private BookFacade getFacade() {
        return ejbFacade;
    }

    public DataModel<Book> getItems() {
        String temSql;
        if (unit == null) {
            return null;
        }
        if (!selectText.equals("")) {
            temSql = "select f from Book f where f.retired=false and lower(f.name) like '%" + selectText.toLowerCase() + "%' and f.unit.id = " + unit.getId() + " order by f.name";
        } else if (!selectName.equals("")) {
            temSql = "select f from Book f where f.retired=false  and lower(f.description) like '%" + selectName.toLowerCase() + "%' and f.unit.id = " + unit.getId() + " order by f.name";
        } else {
            temSql = "select f from Book f where f.retired=false and f.unit.id = " + unit.getId() + " order by f.name";
        }
        items = new ListDataModel(getFacade().findBySQL(temSql));
        return items;
    }

    public DataModel<Book> getLocItems() {
        String temSql;
        if (location == null) {
            return null;
        }
        temSql = "select f from Book f where f.retired=false and f.location.id = " + location.getId() + " order by f.name";
        items = new ListDataModel(getFacade().findBySQL(temSql));
        return items;
    }

    public DataModel<Book> getItemsIns() {
        if (institution != null) {
            return new ListDataModel<Book>(getFacade().findBySQL("SELECT i From Book i WHERE i.retired=false AND i.institution.id = " + institution.getId()));
        } else {
            return null;
        }
    }

    public void setItemsIns(DataModel<Book> itemsIns) {
        this.itemsIns = itemsIns;
    }

    public DataModel<Book> getItemsLoc() {
        if (location != null) {
            return new ListDataModel<Book>(getFacade().findBySQL("SELECT i From Book i WHERE i.retired=false AND i.location.id = " + location.getId()));
        } else {
            return null;
        }
    }

    public void setItemsLoc(DataModel<Book> itemsLoc) {
        this.itemsLoc = itemsLoc;
    }

    public DataModel<Book> getItemsPer() {
        if (person != null) {
            return new ListDataModel<Book>(getFacade().findBySQL("SELECT i From Book i WHERE i.retired=false AND i.person.id = " + person.getId()));
        } else {
            return null;
        }
    }

    public void setItemsPer(DataModel<Book> itemsPer) {
        this.itemsPer = itemsPer;
    }

    public DataModel<Book> getItemsUni() {
        if (unit != null) {
            return new ListDataModel<Book>(getFacade().findBySQL("SELECT i From Book i WHERE i.retired=false AND i.unit.id = " + unit.getId()));
        } else {
            return null;
        }
    }

    public void setItemsUni(DataModel<Book> itemsUni) {
        this.itemsUni = itemsUni;
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
                    current = (Book) items.getRowData();
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

    public Book searchItem(String itemName, boolean createNewIfNotPresent) {
        Book searchedItem = null;
        items = new ListDataModel(getFacade().findAll("name", itemName, true));
        if (items.getRowCount() > 0) {
            items.setRowIndex(0);
            searchedItem = (Book) items.getRowData();
        } else if (createNewIfNotPresent) {
            searchedItem = new Book();
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
        current = new Book();
        this.prepareSelectControlDisable();
    }

    public void saveSelected() {
        if (sessionController.getPrivilege().isInventoryEdit()==false){
            JsfUtil.addErrorMessage("You are not autherized to make changes to any content");
            return;
        }            
        current.setItem(null);
        current.setUnit(unit);
        current.setInstitution(institution);

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
            current = new Book();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error");
        }

    }

    public void cancelSelect() {
        this.prepareSelect();
    }

    public void delete() {
        if (sessionController.getPrivilege().isInventoryDelete()==false){
            JsfUtil.addErrorMessage("You are not autherized to delete any content");
            return;
        }
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

    public String viewBoxImages() {
        if (current != null && current.getLocation() != null) {
            imageController.setLocation(current.getLocation());
            return "item_unit_image";
        } else {
            JsfUtil.addErrorMessage("Please select a book");
            return "";
        }

    }

    public ImageController getImageController() {
        return imageController;
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
    }

    public String viewBookImages() {
        if (current == null) {
            JsfUtil.addErrorMessage("Please select a book");
            return "";
        }
        imageController.setItemUnit(current);
        return "location_image";
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
        this.selectName = "";
    }

    public void prepareSelectControlDisable() {
        selectControlDisable = true;
        modifyControlDisable = false;
    }

    public void prepareModifyControlDisable() {
        selectControlDisable = false;
        modifyControlDisable = true;
    }

    @FacesConverter(forClass = Book.class)
    public static class BookControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BookController controller = (BookController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bookController");
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
            if (object instanceof Book) {
                Book o = (Book) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type "
                        + object.getClass().getName() + "; expected type: " + BookController.class.getName());
            }
        }
    }
}
