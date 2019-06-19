package socket.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;

import static java.lang.System.exit;

public class SocketClientJava extends Application {
    public static void main(String[] args) {
        System.out.println("client 측 실행");

        Socket socket = null;
        String line;

        try {
            // 테스트용 루프백 : 127.0.0.1
            // 실제 서버 dns : ec2-13-209-69-210.ap-northeast-2.compute.amazonaws.com
            String serverIP = "ec2-13-209-69-210.ap-northeast-2.compute.amazonaws.com";
            String loopback = "127.0.0.1";
            int serverPort = 5000;

            //접속할 서버의 ip를 입력받아 접속 실행
            try { socket = new Socket(serverIP, serverPort); }
            catch (IOException e) { socket = new Socket(loopback, serverPort); }

            InetAddress ia = socket.getInetAddress();
            int _port = socket.getLocalPort();
            String _ip = ia.getHostAddress();

            System.out.print("클라이언트 접속 Port: "+ _port);
            System.out.println(" Server IP: " + _ip);

            ClientHandler.setSetting(socket);
            launch(args);

        } catch(IOException ioe) {
            System.err.println("연결 안됨...");
            System.err.println("서버가 동작중인지 확인하세요...");
            exit(-1818);
        } finally {
            try {
                socket.close();
                System.out.println("\n서버와의 접속을 종료합니다.");
                exit(0);
            } catch(Exception ignored) {}
        }

    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MemoMain.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(GUIController.class.getResource("style.css").toExternalForm());
            primaryStage.setTitle("* 메모장 *");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("JavaFX 실행 오류...");
            e.printStackTrace();
            exit(-2);
        }
    }
}