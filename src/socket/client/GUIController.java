package socket.client;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable{

    @FXML Button btnAdd;
    @FXML Button btnDelete;
    @FXML PasswordField password;
    @FXML Button btnGo;
    @FXML TextArea memoArea;
    @FXML TextField memoNameForSave;
    @FXML Label memoName;
    @FXML Button btnSave;

    @FXML TableView<TableViewModel> memoTable;
    @FXML TableColumn<TableViewModel, String> memo;

    private ObservableList<TableViewModel> memoList;

    private void tableSet() {
        memoList = ClientHandler.getList();
        memo.setCellValueFactory(cellData -> cellData.getValue().memoProperty());
        memoTable.setItems(memoList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableSet();
    }

    @FXML public void handleMemoAdd() {
        String getMemoName = memoNameForSave.getText();
        if (getMemoName.contains(",")) {
            int p = getMemoName.indexOf(',');
            if (p < getMemoName.length() - 1) {
                ClientHandler.newMemo(getMemoName);
                tableSet();
            }
        }
    }

    @FXML public void handleMemoDelete() {
        String passwd = password.getText();
        try {
            String memoSelected = memoTable.getSelectionModel().getSelectedItem().getName();
            if (ClientHandler.checkPassword(memoSelected, passwd)) {
                ClientHandler.delMemo(memoSelected, passwd);
                tableSet();
            }
        } catch (Exception e) {
            System.err.println("조작 에러...");
        }
    }

    @FXML public void handleMemoGo() {
        String passwd = password.getText();
        try {
            String memoSelected = memoTable.getSelectionModel().getSelectedItem().getName();
            if (ClientHandler.checkPassword(memoSelected, passwd)) {
                memoName.setText(memoSelected);
                String data = ClientHandler.updateMemo(memoSelected);
                memoArea.setText(data);
            }
        } catch (Exception e) {
            System.err.println("조작 에러...");
        }
    }

    @FXML public void handleMemoSave() {
        String now = memoName.getText();
        String data = memoArea.getText();
        ClientHandler.saveMemo(now, data);
    }
}


