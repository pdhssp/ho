/*
 * MSc(Biomedical Informatics) Project
 *
 * Development and Implementation of a Web-based Combined Data Repository of Genealogical, Clinical, Laboratory and Genetic Data
 * and
 * a Set of Related Tools
 */
package gov.sp.health.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.primefaces.component.menuitem.MenuItem;

/**
 *
 * @author Dr. M. H. B. Ariyaratne, MBBS, PGIM Trainee for MSc(Biomedical
 * Informatics)
 */
@ManagedBean
@RequestScoped
public class Menu implements Serializable {

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;
    @ManagedProperty(value = "#{messageProvider}")
    private MessageProvider messageProvider;
    MenuModel model;
    String temIx = "";

    public MessageProvider getMessageProvider() {
        return messageProvider;
    }

    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    public String getTemIx() {
        return temIx;
    }

    public void setTemIx(String temIx) {
        this.temIx = temIx;
    }

    private String getLabel(String key) {
        return getMessageProvider().getValue(key);
    }

    public Menu() {
//        createMenu();
    }

    public Submenu officeSubmenu() {
        Submenu submenu = new Submenu();
        submenu.setLabel(getLabel("office"));

        submenu.getChildren().add(postSubmenu());
        submenu.getChildren().add(recordRoomSubmenu());
        submenu.getChildren().add(citizenCharterSubmenu());
        submenu.getChildren().add(demographySubmenu());
        submenu.getChildren().add(financeSubmenu());
        submenu.getChildren().add(commonSubmenu());

        return submenu;
    }

    public Submenu suppliesSubmenu() {
        Submenu submenu = new Submenu();
        submenu.setLabel(getLabel("Suppliers"));

        submenu.getChildren().add(itemSuppliersSubmenu());
        submenu.getChildren().add(inventorySubmenu());
        submenu.getChildren().add(storesSubmenu());
        submenu.getChildren().add(medicalSubmenu());

        return submenu;
    }

    public Submenu itemSuppliersSubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("itemSuppliers"));

        item = new MenuItem();
        item.setValue(getLabel("addSuppliers"));
        item.setUrl("supplier_add.xhtml");
        submenu.getChildren().add(item);



        item = new MenuItem();
        item.setValue(getLabel("addSupplierContactDetails"));
        item.setUrl("supplier_contact.xhtml");
        submenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("addItemSuppliers"));
        item.setUrl("item_supplier.xhtml");
        submenu.getChildren().add(item);



        return submenu;
    }

    private Submenu citizenCharterSubmenu() {
        Submenu submenu = new Submenu();
        submenu.setLabel(getLabel("citizenCharter"));

        MenuItem item;



        item = new MenuItem();
        item.setValue(getLabel("addCompletedTask"));
        item.setUrl("citizen_charter_add.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("reports"));
        item.setUrl("citizen_charter_reports.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("edit"));
        item.setUrl("citizen_charter_edit.xhtml");
        submenu.getChildren().add(item);

        return submenu;

    }

    private Submenu financeSubmenu() {
        Submenu submenu = new Submenu();
        submenu.setLabel(getLabel("finance"));

        MenuItem item;

        item = new MenuItem();
        item.setValue(getLabel("addExpenses"));
        item.setUrl("finance_add_expense.xhtml");
        submenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("reports"));
        item.setUrl("finance_reports.xhtml");
        submenu.getChildren().add(item);



        item = new MenuItem();
        item.setValue(getLabel("edit"));
        item.setUrl("finance_edit.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public void createMenu() {
        model = new DefaultMenuModel();
//        model.addSubmenu(cadreSubmenu());


        MenuItem item;



        item = new MenuItem();
        item.setValue(getLabel("home"));
        item.setUrl("index.xhtml");
        model.addMenuItem(item);

        if (sessionController.privilege.isDemographyView()) {
            model.addSubmenu(officeSubmenu());
        }

        if (sessionController.privilege.isInventoryView()) {
            model.addSubmenu(suppliesSubmenu());
        }

        if (sessionController.privilege.isLibraryView()) {
            model.addSubmenu(librarySubmenu());
        }

        model.addSubmenu(transferSubmenu());

        if (sessionController.privilege.isBmeView()) {
            model.addSubmenu(biomedSubmenu());
        }
//        if (sessionController.privilege.isVehicleView()) model.addSubmenu(transportSubmenu());
//
//        if (sessionController.privilege.isDemographyView()) {
//            model.addSubmenu(demographySubmenu());
//        }

//        if (sessionController.privilege.isCaderView()) {
//            model.addSubmenu(humanSubmenu());
//        }

//        if (sessionController.privilege.isMsView()) {
//            model.addSubmenu(medicalSubmenu());
//        }
        if (sessionController.privilege.isManageAccounts()) {
            model.addSubmenu(adminSubmenu());
        }

        model.addSubmenu(userSubmenu());
    }

    private Submenu commonSubmenu() {
        Submenu submenu = new Submenu();
        submenu.setLabel(getLabel("common"));

        MenuItem item;

        item = new MenuItem();
        item.setValue(getLabel("InstitutionTypes"));
        item.setUrl("institution_type.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("institutions"));
        item.setUrl("institutions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Units"));
        item.setUrl("inventory_unit.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("unitsImages"));
        item.setUrl("unit_add_image.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Locations"));
        item.setUrl("inventory_location.xhtml");
        submenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("locationImages"));
        item.setUrl("location_add_image.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Persons"));
        item.setUrl("person.xhtml");
        submenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("Suppliers"));
        item.setUrl("inventory_supplier.xhtml");
        submenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("Manufacturers"));
        item.setUrl("inventory_manufacturer.xhtml");
        submenu.getChildren().add(item);



        item = new MenuItem();
        item.setValue(getLabel("Countries"));
        item.setUrl("country.xhtml");
        submenu.getChildren().add(item);

        return submenu;

    }

    private Submenu transferSubmenu() {
        Submenu submenu = new Submenu();
        submenu.setLabel(getLabel("transfer"));

        MenuItem item;

        item = new MenuItem();
        item.setValue(getLabel("newTransfer"));
        item.setUrl("transfer_new.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("awaitingTransfer"));
        item.setUrl("transfer_awaiting.xhtml");
        submenu.getChildren().add(item);

        return submenu;

    }

    private Submenu recordRoomSubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("recordRoom"));



        item = new MenuItem();
        item.setValue(getLabel("boxes"));
        item.setUrl("record_room_boxes.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("files"));
        item.setUrl("record_room_files.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("boxFiles"));
        item.setUrl("record_room_files_in_boxes.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    private Submenu userSubmenu() {
        Submenu submenu = new Submenu();
        submenu.setLabel(getLabel("user"));

        MenuItem item;

        item = new MenuItem();
        item.setValue(getLabel("changePassword"));
        item.setUrl("change_password.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("preferences"));
        item.setUrl("under_construction.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    private Submenu postSubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("post"));
        
        item = new MenuItem();
        item.setValue(getLabel("postIndex"));
        item.setUrl("post_index.xhtml");
        submenu.getChildren().add(item);        
        
        item = new MenuItem();
        item.setValue(getLabel("newLetter"));
        item.setUrl("post_new_letter.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("lettersToInstitution"));
        item.setUrl("post_institution_received.xhtml");
        submenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("lettersToSubjects"));
        item.setUrl("post_institution_received_subject.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("myLetters"));
        item.setUrl("post_my_letters.xhtml");
        submenu.getChildren().add(item);


        return submenu;
    }

    private Submenu cadreSubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("hr"));



        item = new MenuItem();
        item.setValue(getLabel("InstitutionTypes"));
        item.setUrl("institution_type.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Institutions"));
        item.setUrl("institutions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("designationsCategory"));
        item.setUrl("designation_category.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Designations"));
        item.setUrl("designation_level.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Designations"));
        item.setUrl("designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("institutionDesignations"));
        item.setUrl("institution_designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("cadrePositions"));
        item.setUrl("cadre_positions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("reports"));
        item.setUrl("reports.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    private Submenu biomedSubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("biomedical"));

        item = new MenuItem();
        item.setValue(getLabel("InstitutionTypes"));
        item.setUrl("institution_type.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Institutions"));
        item.setUrl("institutions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("designationsCategory"));
        item.setUrl("designation_category.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Designations"));
        item.setUrl("designation_level.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Designations"));
        item.setUrl("designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("institutionDesignations"));
        item.setUrl("institution_designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("cadrePositions"));
        item.setUrl("cadre_positions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("reports"));
        item.setUrl("reports.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    private Submenu transportSubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("transport"));

        item = new MenuItem();
        item.setValue(getLabel("InstitutionTypes"));
        item.setUrl("institution_type.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("InstitutionTypes"));
        item.setUrl("institutions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("designationsCategory"));
        item.setUrl("designation_category.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Designations"));
        item.setUrl("designation_level.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Designations"));
        item.setUrl("designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("institutionDesignations"));
        item.setUrl("institution_designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("cadrePositions"));
        item.setUrl("cadre_positions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("reports"));
        item.setUrl("reports.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    private Submenu demographySubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("demography"));


        item = new MenuItem();
        item.setValue(getLabel("edit"));
        item.setUrl("demography_edit.xhtml");
        submenu.getChildren().add(item);



        item = new MenuItem();
        item.setValue(getLabel("import"));
        item.setUrl("demography_import_excel.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("display"));
        item.setUrl("demography_display_areas.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Designations"));
        item.setUrl("designation_level.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Designations"));
        item.setUrl("designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("institutionDesignations"));
        item.setUrl("institution_designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("cadrePositions"));
        item.setUrl("cadre_positions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("reports"));
        item.setUrl("reports.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    private Submenu humanSubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("hr"));

        item = new MenuItem();
        item.setValue(getLabel("InstitutionTypes"));
        item.setUrl("institution_type.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Institutions"));
        item.setUrl("institutions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("designationsCategory"));
        item.setUrl("designation_category.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Designations"));
        item.setUrl("designation_level.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Designations"));
        item.setUrl("designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("institutionDesignations"));
        item.setUrl("institution_designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("cadrePositions"));
        item.setUrl("cadre_positions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("reports"));
        item.setUrl("reports.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    private Submenu medicalSubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("msd"));

        Submenu editSubmenu = new Submenu();
        editSubmenu.setLabel(getLabel("edit"));

        Submenu medicinesSubmenu = new Submenu();
        medicinesSubmenu.setLabel(getLabel("medicines"));

        item = new MenuItem();
        item.setValue(getLabel("medicineGroups"));
        item.setUrl("ms_medicine_group.xhtml");
        medicinesSubmenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("ItemCategories"));
        item.setUrl("ms_item_category.xhtml");
        medicinesSubmenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("genericNames"));
        item.setUrl("ms_vtm.xhtml");
        medicinesSubmenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("tradeNames"));
        item.setUrl("ms_atm.xhtml");
        medicinesSubmenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("itemMaster"));
        item.setUrl("ms_amp.xhtml");
        medicinesSubmenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("importFromExcel"));
        item.setUrl("ms_import_items.xhtml");
        medicinesSubmenu.getChildren().add(item);

        editSubmenu.getChildren().add(medicinesSubmenu);


        Submenu insSubmenu = new Submenu();
        insSubmenu.setLabel(getLabel("institutions"));

        item = new MenuItem();
        item.setValue(getLabel("Units"));
        item.setUrl("inventory_unit.xhtml");
        insSubmenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Suppliers"));
        item.setUrl("inventory_supplier.xhtml");
        insSubmenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Manufacturers"));
        item.setUrl("inventory_manufacturer.xhtml");
        insSubmenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Countries"));
        item.setUrl("country.xhtml");
        insSubmenu.getChildren().add(item);

        editSubmenu.getChildren().add(insSubmenu);


        submenu.getChildren().add(editSubmenu);


        item = new MenuItem();
        item.setValue(getLabel("msPurchase"));
        item.setUrl("ms_purchase.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("msReceive"));
        item.setUrl("institutions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("msIssue"));
        item.setUrl("designation_category.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("msRequests"));
        item.setUrl("designation_level.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("msIssueRequests"));
        item.setUrl("designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("msReceiveRequests"));
        item.setUrl("institution_designation.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("msReceiveRequests"));
        item.setUrl("cadre_positions.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("msPrepareEstimates"));
        item.setUrl("reports.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    private Submenu inventorySubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("inventory"));



        Submenu editMenu = new Submenu();
        editMenu.setLabel(getLabel("edit"));



        item = new MenuItem();
        item.setValue(getLabel("ItemCategories"));
        item.setUrl("inventory_item_category.xhtml");
        editMenu.getChildren().add(item);



        item = new MenuItem();
        item.setValue(getLabel("make"));
        item.setUrl("inventory_make.xhtml");
        editMenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("model"));
        item.setUrl("inventory_modal.xhtml");
        editMenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Items"));
        item.setUrl("inventory_item.xhtml");
        editMenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("ItemsImportFromExcel"));
        item.setUrl("inventory_import_items.xhtml");
        editMenu.getChildren().add(item);

        submenu.getChildren().add(editMenu);

        item = new MenuItem();
        item.setValue(getLabel("purchase"));
        item.setUrl("inventory_purchase.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("issue"));
        item.setUrl("inventory_issue.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("receiveInventoryItems"));
        item.setUrl("inventory_institution_received_bills.xhtml");
        submenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("requests"));
        item.setUrl("designation_category.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("estimates"));
        item.setUrl("designation_level.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("adjust"));
        item.setUrl("inventory_adjust.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("reports"));
        item.setUrl("inventory_reports.xhtml");
        submenu.getChildren().add(item);


        return submenu;
    }

    private Submenu storesSubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("stores"));



        Submenu editMenu = new Submenu();
        editMenu.setLabel(getLabel("edit"));



        item = new MenuItem();
        item.setValue(getLabel("ItemCategories"));
        item.setUrl("inventory_item_category.xhtml");
        editMenu.getChildren().add(item);



        item = new MenuItem();
        item.setValue(getLabel("make"));
        item.setUrl("inventory_make.xhtml");
        editMenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("model"));
        item.setUrl("inventory_modal.xhtml");
        editMenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("Items"));
        item.setUrl("inventory_item.xhtml");
        editMenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("ItemsImportFromExcel"));
        item.setUrl("inventory_import_items.xhtml");
        editMenu.getChildren().add(item);

        submenu.getChildren().add(editMenu);

        item = new MenuItem();
        item.setValue(getLabel("purchase"));
        item.setUrl("stores_purchase.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("issue"));
        item.setUrl("inventory_issue.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("receiveInventoryItems"));
        item.setUrl("inventory_institution_received_bills.xhtml");
        submenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("requests"));
        item.setUrl("designation_category.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("estimates"));
        item.setUrl("designation_level.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("adjust"));
        item.setUrl("inventory_adjust.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("reports"));
        item.setUrl("stores_reports.xhtml");
        submenu.getChildren().add(item);


        return submenu;
    }

    private Submenu adminSubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("admin"));

        item = new MenuItem();
        item.setValue(getLabel("activateAccounts"));
        item.setUrl("admin_activate_users.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("addPersonsAsUsers"));
        item.setUrl("admin_person_to_user.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("manageAccounts"));
        item.setUrl("admin_user_previlages.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("removeUserAccounts"));
        item.setUrl("admin_remove_users.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    private Submenu librarySubmenu() {
        Submenu submenu;

        MenuItem item;

        submenu = new Submenu();
        submenu.setLabel(getLabel("library"));


        item = new MenuItem();
        item.setValue(getLabel("publishers"));
        item.setUrl("library_publisher.xhtml");
        submenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("books"));
        item.setUrl("library_books.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("issueBooks"));
        item.setUrl("library_issue_books.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("returnBooks"));
        item.setUrl("library_return_books.xhtml");
        submenu.getChildren().add(item);


        item = new MenuItem();
        item.setValue(getLabel("booksInTheLibrary"));
        item.setUrl("library_current_books.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("issuedBooks"));
        item.setUrl("library_issued_books.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("delayedReturns"));
        item.setUrl("library_delayed_books.xhtml");
        submenu.getChildren().add(item);

        item = new MenuItem();
        item.setValue(getLabel("bookHistory"));
        item.setUrl("library_books_history.xhtml");
        submenu.getChildren().add(item);

        return submenu;
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    @PostConstruct
    public void init() {
        try {
            createMenu();
        } catch (Exception e) {
            System.out.println("Error in init method. It is " + e.getMessage());
        }
    }
}
