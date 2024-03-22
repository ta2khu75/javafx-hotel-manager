package controller;

import dao.ServiceDAO;
import dao.TypeOfServiceDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Service;
import model.TypeOfService;
import util.Msg;

public class ServiceController implements Initializable {

    int id = 0;
    @FXML
    private ComboBox<String> cboType;

    @FXML
    private TableColumn<Service, Float> colPrice;

    @FXML
    private TableColumn<Service, Integer> colServiceId;

    @FXML
    private TableColumn<Service, String> colServiceName;

    @FXML
    private TableColumn<Service, String> colType;

    @FXML
    private TableColumn<TypeOfServiceDAO, String> colTypeId;

    @FXML
    private TableColumn<TypeOfService, String> colTypeName;

    @FXML
    private TableView<Service> tblService;

    @FXML
    private TableView<TypeOfService> tblType;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtService;

    @FXML
    private TextField txtType;

    @FXML
    void deleteService(MouseEvent event) {
        Service service = checkService();
        if (service != null && id != 0) {
            service.setId(id);
            serviceDao.delete(service);
            Msg.printInfo("Xóa thành công");
            fillTblService();
        }
    }

    @FXML
    void deleteType(MouseEvent event) {
        TypeOfService service = checkType();
        if (service != null) {
            typeDao.delete(service);
            Msg.printInfo("Xóa thành công");
            fillTblType();
        }
    }

    @FXML
    void insertService(MouseEvent event) {
        Service service = checkService();
        if (service != null) {
            serviceDao.insert(service);
            Msg.printInfo("Thêm thành công");
            fillTblService();
        }
    }

    @FXML
    void insertType(MouseEvent event) {
        TypeOfService service = checkType();
        if (service != null) {
            typeDao.insert(service);
            Msg.printInfo("Thêm thành công");
            fillTblType();
        }
    }

    @FXML
    void newService(MouseEvent event) {
        cleanService();
    }

    @FXML
    void newType(MouseEvent event) {
        cleanType();
    }

    @FXML
    void updateService(MouseEvent event) {
        Service service = checkService();
        if (service != null && id != 0) {
            service.setId(id);
            serviceDao.update(service);
            Msg.printInfo("Cập nhập thành công");
            fillTblService();
        }
    }

    @FXML
    void updateType(MouseEvent event) {
        TypeOfService service = checkType();
        if (service != null) {
            typeDao.update(service);
            Msg.printInfo("Cập nhập thành công");
            fillTblType();
        }
    }

    @FXML
    void fillFormService(MouseEvent event) {
        getService();
    }

    @FXML
    void fillFormType(MouseEvent event) {
        getType();
    }

    private ServiceDAO serviceDao = new ServiceDAO();
    private TypeOfServiceDAO typeDao = new TypeOfServiceDAO();
    private ObservableList<String> nameList = FXCollections.observableArrayList();

    private ObservableList<Service> serviceList = FXCollections.observableArrayList(serviceDao.selectAll());
    private ObservableList<TypeOfService> typeList = FXCollections.observableArrayList(typeDao.selectAll());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colServiceId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colServiceName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory((p) -> {
            String service = p.getValue().getIdTypeOfService();
            for (TypeOfService x : typeList) {
                if (x.getId().equals(service)) {
                    return new SimpleStringProperty(x.getName());
                }
            }
            return null;
        });

        colTypeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTypeName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        tblService.setItems(serviceList);
        tblType.setItems(typeList);
        fillCboType();
    }

    private void fillCboType() {
        cboType.getItems().clear();
        for (TypeOfService x : typeList) {
            nameList.add(x.getName());
        }
        cboType.setItems(nameList);
    }

    private TypeOfService checkType() {
        String Id = txtId.getText();
        String name = txtType.getText();
        if (Id.isEmpty() || name.isEmpty()) {
            Msg.printWarn("Vui lòng nhập đầy đủ thông tin");
            return null;
        }
        return new TypeOfService(Id, name);
    }

    private Service checkService() {
        String name = txtService.getText();
        String price = txtPrice.getText();
        if (name.isEmpty() || price.isEmpty() || cboType.getSelectionModel().getSelectedIndex() < 0) {
            Msg.printWarn("Vui lòng điền đầy đủ thông tin");
            return null;
        }
        float pric = 0;
        try {
            pric = Float.parseFloat(price);
        } catch (Exception e) {
            Msg.printWarn("Đơn giá phải là số ");
            return null;
        }

        return new Service(name, pric, typeList.get(cboType.getSelectionModel().getSelectedIndex()).getId());
    }

    private void cleanService() {
        txtPrice.setText("");
        txtService.setText("");
        cboType.getSelectionModel().select(-1);
        id = 0;
    }

    private void cleanType() {
        txtType.setText("");
        txtId.setText("");
    }

    private void fillTblService() {
        serviceList.clear();
        serviceList.addAll(serviceDao.selectAll());
        cleanService();
    }

    private void fillTblType() {
        typeList.clear();
        typeList.addAll(typeDao.selectAll());
        cleanType();
    }

    private void getService() {
        Service service = tblService.getSelectionModel().getSelectedItem();
        id = service.getId();
        txtPrice.setText(service.getPrice() + "");
        txtService.setText(service.getName());
        cboType.getSelectionModel().select(colType.getCellData(tblService.getSelectionModel().getSelectedIndex()));
    }

    private void getType() {
        TypeOfService type = tblType.getSelectionModel().getSelectedItem();
        txtId.setText(type.getId());
        txtType.setText(type.getName());

    }
}
