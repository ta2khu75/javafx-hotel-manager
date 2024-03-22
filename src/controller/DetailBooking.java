package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.BookingDAO;
import dao.ServiceDAO;
import dao.ServiceRoomDAO;
import dao.TypeOfServiceDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Booking;
import model.Room;
import model.Service;
import model.ServiceRoom;
import model.TypeOfRoom;
import model.TypeOfService;
import util.Fx;
import util.Msg;

public class DetailBooking implements Initializable {

    @FXML
    private ComboBox<String> cboType;

    @FXML
    private DatePicker chsIn;

    @FXML
    private DatePicker chsOut;

    @FXML
    private Label lblCustomer;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblHour;

    @FXML
    private Label lblPriceRoom;

    @FXML
    private Label lblRoom;

    @FXML
    private Label lblTimeIn;

    @FXML
    private Label lblTimeOut;

    @FXML
    private RadioButton rdoDate;

    @FXML
    private ToggleGroup type;

    @FXML
    private RadioButton rdoHour;

    @FXML
    private GridPane menuService;

    @FXML
    private TableColumn<ServiceRoom, String> colName;

    @FXML
    private TableColumn<ServiceRoom, Float> colPrice;

    @FXML
    private TableColumn<ServiceRoom, Integer> colQuantity;

    @FXML
    private TableColumn<ServiceRoom, Integer> colStt;

    @FXML
    private TableColumn<ServiceRoom, Float> colTotalAmount;

    @FXML
    private TableView<ServiceRoom> tblService;

    @FXML
    private Label lblPriceService;

    @FXML
    private TextField txtGiven;

    @FXML
    private TextField txtRefunds;

    @FXML
    private Label lblTotal;

    @FXML
    void caculatorDate(MouseEvent event) {
        caculator();
    }

    @FXML
    void caculatorHour(MouseEvent event) {
        caculator();
    }

    @FXML
    void editTable(MouseEvent event) {
        editServiceRoom();
    }

    @FXML
    void changeRoom(MouseEvent event) throws IOException {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/view/changeRoom.fxml"));
        Parent parent = load.load();
        ChangeRoom changeRoom = load.getController();
        Stage stage = Fx.getStage(cboType);
        Fx.setRoot(stage, parent);
        changeRoom.setDate(this);
    }

    @FXML
    void pay(MouseEvent event) {
        booking.setStatus(true);
        booking.setEndDate(today);
        booking.setTotalMoney(Float.parseFloat(lblTotal.getText()));
        bookDao.update(booking);
        Msg.printPay("Thanh toán thành công");
        this.itemRoom.homePage.fillMenuRoom();
        createPdf();
    }

    private LocalDateTime today;
    private LocalDateTime in;
    private LocalDateTime out;
    private Booking booking;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    private boolean add0 = false;
    private static Stage stage = new Stage();
    private ItemRoom itemRoom;
    private long day;
    private long hour;
    private BookingDAO bookDao = new BookingDAO();
    private Room room;
    private TypeOfRoom typeRoom;
    private TypeOfServiceDAO typeServiceDAO = new TypeOfServiceDAO();
    private ServiceDAO serviceDao = new ServiceDAO();
    private ObservableList<TypeOfService> listTypeService;
    private ServiceRoomDAO serviceRoomDAO = new ServiceRoomDAO();
    private ObservableList<ServiceRoom> listServiceRoom = FXCollections.observableArrayList();
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ItemRoom getItemRoom() {
        return itemRoom;
    }

    public void setItemRoom(ItemRoom itemRoom) {
        this.itemRoom = itemRoom;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCbo();
        fillMenuService(serviceDao.selectAll());
        colName.setCellValueFactory((p) -> {
            String name = serviceDao.selectById(p.getValue().getIdService()).getName();
            return new SimpleStringProperty(name);
        });
        colPrice.setCellValueFactory((p) -> {
            float price = serviceDao.selectById(p.getValue().getIdService()).getPrice();
            return new SimpleObjectProperty<>(price);
        });
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotalAmount.setCellValueFactory((p) -> {
            ServiceRoom serviceRoom = p.getValue();
            float totalAmount = serviceDao.selectById(serviceRoom.getIdService()).getPrice() * serviceRoom.getQuantity();
            return new SimpleObjectProperty<>(totalAmount);
        });
        colStt.setCellValueFactory((p) -> {
            int index = tblService.getItems().indexOf(p.getValue()) + 1;
            return new SimpleObjectProperty<>(index);
        });
        tblService.setItems(listServiceRoom);
        txtGiven.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if (t1.isEmpty()) {
                    return;
                }
                try {
                    Long.parseLong(t1);
                } catch (Exception e) {
                    Msg.printErr("Vui lòng nhập 1 số");
                    return;
                }
                if (!add0 && t1.contains("0")) {
                    add0 = true;
                    txtGiven.setText(t1 + "00");
                }
                long given = given = Long.parseLong(t1);
                long pay = Long.parseLong(lblTotal.getText());
                long result = given - pay;
                txtRefunds.setText(result + "đ");
            }
        });
    }

    private void fillCbo() {
        listTypeService = FXCollections.observableArrayList(typeServiceDAO.selectAll());
        cboType.getItems().add("All");
        for (TypeOfService x : listTypeService) {
            cboType.getItems().add(x.getName());
        }
        cboType.getSelectionModel().select(0);
        cboType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                if (t1.intValue() == 0) {
                    fillMenuService(serviceDao.selectAll());
                } else {
                    TypeOfService type = listTypeService.get(t1.intValue() - 1);
                    fillMenuService(serviceDao.selectServiceByIdTypeOfService(type));
                }
            }

        });
    }

    void setData(ItemRoom ir) {
        this.itemRoom = ir;
        this.room = itemRoom.room;
        booking = bookDao.selectByRoom(room.getId());
        typeRoom = this.itemRoom.typeOfRoom;
        lblRoom.setText(booking.getIdRoom());
        lblCustomer.setText("Customer: " + this.itemRoom.guest.getFullName());
        lblDate.setText("Price date: " + typeRoom.getPricePerDay());
        lblHour.setText("Hour price: " + typeRoom.getHourlyPrice());
        today = LocalDateTime.now();
        in = booking.getStartDate();
        out = booking.getEndDate();
        lblTimeIn.setText("Time in: " + in.format(dateTimeFormatter));
        lblTimeOut.setText("Time out: " + today.format(dateTimeFormatter));
        chsIn.setValue(in.toLocalDate());
        chsOut.setValue(out.toLocalDate());
        if (booking.isType()) {
            rdoDate.setSelected(true);
        } else {
            rdoHour.setSelected(true);
        }
        caculator();
    }

    void fillMenuService(List<Service> list) {
        menuService.getChildren().clear();
        menuService.getColumnConstraints().clear();
        menuService.getRowConstraints().clear();
        int col = 0;
        int row = 0;
        for (Service x : list) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/itemService.fxml"));
            VBox pane = null;
            try {
                pane = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(DetailBooking.class.getName()).log(Level.SEVERE, null, ex);
            }
            ItemService itemService = loader.getController();
            itemService.setData(x, this);
            if (col == 7) {
                col = 0;
                row++;
            }
            menuService.add(pane, col++, row);
            GridPane.setMargin(pane, new Insets(5));
        }
    }

    void caculator() {
        long result = 0;
        if (rdoDate.isSelected()) {
            long hoursUsed = in.until(LocalDateTime.now(), ChronoUnit.HOURS);
            long dayUsed = ChronoUnit.DAYS.between(in, LocalDateTime.now());
            double day = (double) hoursUsed / 24;
            double dayPhay = day % 1;
            if (day > 1 && dayPhay > 0.5) {
                dayUsed++;
                result = (long) (dayUsed * typeRoom.getPricePerDay());
            } else if (day > 1 && dayPhay <= 0.5) {
                double priceHour = dayPhay * 24;
                double priceDay = dayUsed * typeRoom.getPricePerDay();
                result = (long) (priceDay + priceHour);
            } else {
                dayUsed++;
                result = (long) (dayUsed * typeRoom.getPricePerDay());
            }
            this.day = dayUsed;
        } else {
            long hoursUsed = in.until(LocalDateTime.now(), ChronoUnit.HOURS);
            long minuteUsed = in.until(LocalDateTime.now(), ChronoUnit.MINUTES);
            double hour = (double) minuteUsed / 60;
            double hourPhay = hour % 1;
            if (hour > 1 && hourPhay > 0.5) {
                hoursUsed++;
                result = (long) (hoursUsed * typeRoom.getHourlyPrice());
            } else if (hour < 1) {
                hoursUsed++;
                result = (long) (hoursUsed * typeRoom.getHourlyPrice());
            } else {
                result = (long) (hoursUsed * typeRoom.getHourlyPrice());
            }
            this.hour = hoursUsed;
        }
        lblPriceRoom.setText(result + "");
        totalService();
    }

    void totalService() {
        List<ServiceRoom> list = serviceRoomDAO.selectByBooking(booking.getId());
        listServiceRoom.clear();
        if (list != null) {
            listServiceRoom.addAll(list);

            int result = 0;
            for (int i = 0; i < listServiceRoom.size(); i++) {
                result += colTotalAmount.getCellData(i);
            }
            lblPriceService.setText(result + "");
            int priceRoom = Integer.parseInt(lblPriceRoom.getText());
            result += priceRoom;
            lblTotal.setText(result + "");
        } else {
            lblPriceService.setText("0đ");
            lblTotal.setText(lblPriceRoom.getText());
        }
    }

    void editServiceRoom() {
        if (tblService.getItems().isEmpty()) {
            return;
        }
        ServiceRoom serviceRoom = tblService.getSelectionModel().getSelectedItem();
        String nameString = colName.getCellData(tblService.getSelectionModel().getSelectedIndex());
        FXMLLoader load = new FXMLLoader(getClass().getResource("/view/quantity.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(load.load());
        } catch (IOException ex) {
            Logger.getLogger(DetailBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
        stage.setScene(scene);
        Quantity controller = load.getController();
        controller.setData(serviceRoom, nameString, this);
        stage.show();
    }

    private void createPdf() {
        String typeString = rdoDate.isSelected() ? "Ngày" : "Giờ";
        long number = rdoDate.isSelected() ? day : hour;
        float price = rdoDate.isSelected() ? typeRoom.getPricePerDay() : typeRoom.getHourlyPrice();
        double result = number * price;
        String directoryPath = String.format("pdf/%d-%s-%d", today.getYear(), today.getMonth(), today.getDayOfMonth());
        String nameFile = String.format("/%d:%d:%d_%d_%s.pdf", today.getHour(), today.getMinute(), today.getSecond(), booking.getId(), this.itemRoom.guest.getFullName());
        String priceRoomString = String.format("%d %s X %.0f = %.0fđ", number, typeString, price, result);
        File directory = new File(directoryPath);
        StringBuilder fileBuilder = new StringBuilder(directoryPath);
        fileBuilder.append(nameFile);
        if (!directory.exists()) {
            directory.mkdir();
        }
        Document document = new Document(PageSize.A4);
        try {
            BaseFont baseFont = BaseFont.createFont("font/Arial-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont baseFontBold = BaseFont.createFont("font/Arial-Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 12);
            Font font15 = new Font(baseFont, 15);
            Font fontBold12 = new Font(baseFontBold, 12);
            Font fontBold = new Font(baseFontBold, 15);
            PdfWriter.getInstance(document, new FileOutputStream(fileBuilder.toString()));
            document.open();
            //Tiêu đề
            Paragraph bill = new Paragraph("HÓA ĐƠN", fontBold);
            bill.setAlignment(Element.ALIGN_CENTER);

            PdfPTable tableBooking = new PdfPTable(2);
            tableBooking.addCell(createCellNoBorder("Mã hóa đơn: " + booking.getId(), Element.ALIGN_LEFT, font));
            tableBooking.addCell(createCellNoBorder("Thời gian: " + today.format(dateTimeFormatter), Element.ALIGN_RIGHT, font));
            tableBooking.addCell(createCellNoBorder("\n", Element.ALIGN_LEFT, font));
            tableBooking.addCell(createCellNoBorder("\n", Element.ALIGN_RIGHT, font));
            tableBooking.addCell(createCellNoBorder("Họ tên: " + itemRoom.guest.getFullName(), Element.ALIGN_LEFT, font));
            tableBooking.addCell(createCellNoBorder("Mã phòng: " + booking.getIdRoom(), Element.ALIGN_RIGHT, font));
            tableBooking.addCell(createCellNoBorder("\n", Element.ALIGN_LEFT, font));
            tableBooking.addCell(createCellNoBorder("\n", Element.ALIGN_RIGHT, font));
            tableBooking.addCell(createCellNoBorder("Ngày vào: " + in.format(dateTimeFormatter), Element.ALIGN_LEFT, font));
            tableBooking.addCell(createCellNoBorder("Ngày ra: " + out.format(dateTimeFormatter), Element.ALIGN_RIGHT, font));
            tableBooking.addCell(createCellNoBorder("\n", Element.ALIGN_LEFT, font));
            tableBooking.addCell(createCellNoBorder("\n", Element.ALIGN_RIGHT, font));
            tableBooking.addCell(createCellNoBorder("Tiền phòng: ", Element.ALIGN_LEFT, fontBold12));
            tableBooking.addCell(createCellNoBorder(priceRoomString, Element.ALIGN_RIGHT, fontBold12));
            tableBooking.addCell(createCellNoBorder("\n", Element.ALIGN_LEFT, font));
            tableBooking.addCell(createCellNoBorder("\n", Element.ALIGN_RIGHT, font));

            PdfPTable tableService = new PdfPTable(4);
            tableService.addCell(new PdfPCell(new Paragraph("Tên dịch vụ", fontBold12)));
            tableService.addCell(new PdfPCell(new Paragraph("Giá", fontBold12)));
            tableService.addCell(new PdfPCell(new Paragraph("Số lượng", fontBold12)));
            tableService.addCell(new PdfPCell(new Paragraph("Tổng tiền", fontBold12)));
            for (int i = 0; i < tblService.getItems().size(); i++) {
                tableService.addCell(new PdfPCell(new Paragraph(colName.getCellData(i), font)));
                tableService.addCell(new PdfPCell(new Paragraph(colPrice.getCellData(i) + "đ", font)));
                tableService.addCell(new PdfPCell(new Paragraph(colQuantity.getCellData(i) + "", font)));
                tableService.addCell(new PdfPCell(new Paragraph(colTotalAmount.getCellData(i) + "đ", font)));
            }
            document.add(bill);
            document.add(new Phrase("\n"));
            document.add(tableBooking);
            document.add(tableService);
            PdfPTable tableTotal = new PdfPTable(2);
            tableTotal.addCell(createCellNoBorder("\n", Element.ALIGN_LEFT, font));
            tableTotal.addCell(createCellNoBorder("\n", Element.ALIGN_RIGHT, font));
            tableTotal.addCell(createCellNoBorder("Tiền dịch vụ:", Element.ALIGN_LEFT, fontBold12));
            tableTotal.addCell(createCellNoBorder(lblPriceService.getText() + "đ", Element.ALIGN_RIGHT, fontBold12));
            tableTotal.addCell(createCellNoBorder("\n", Element.ALIGN_LEFT, font));
            tableTotal.addCell(createCellNoBorder("\n", Element.ALIGN_RIGHT, font));
            tableTotal.addCell(createCellNoBorder("Tổng tiền:", Element.ALIGN_LEFT, fontBold));
            tableTotal.addCell(createCellNoBorder(lblTotal.getText() + "đ", Element.ALIGN_RIGHT, fontBold));
            document.add(tableTotal);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DetailBooking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(DetailBooking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DetailBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static PdfPCell createCell(String content, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    private static PdfPCell createCellNoBorder(String content, int alignment, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
