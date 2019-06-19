package socket.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    // 전달받은 socket 과 io stream 저장

    private static Socket connectedServerSocket;

    private static BufferedReader reader;
    private static BufferedWriter writer;

    public static void setSetting(Socket socket) {
        connectedServerSocket = socket;
        try {
            // buffer io
            reader = new BufferedReader(
                    new InputStreamReader(connectedServerSocket.getInputStream()));
            writer = new BufferedWriter(
                    new OutputStreamWriter(connectedServerSocket.getOutputStream()));

        } catch (IOException ioe) {
            System.err.println("입출력 에러...");
        }

        // 연결 확인
        try {
            System.out.println(reader.readLine());
        } catch (IOException ioe) {
            System.err.println("입출력 에러...");
        }
    }

    public static String updateMemo(String memoNameForLoad) {
        try {
            writer.write("1");
            writer.newLine();
            writer.write(memoNameForLoad);
            writer.newLine();
            writer.flush();

            String str;
            StringBuilder result = new StringBuilder();
            while (!"=*= FinFile =*=".equals(str = reader.readLine())) {
                result.append(str);
                result.append("\n");
            }
            return result.toString();

        } catch (IOException ioe) {
            System.err.println("입출력 에러... 1");
            return "Error";
        }
    }

    public static void newMemo(String memoNameForSave) {
        try {
            writer.write("2");
            writer.newLine();
            writer.write(memoNameForSave);
            writer.newLine();
            writer.flush();

        } catch (IOException ioe) {
            System.err.println("입출력 에러... 2");
        }
    }

    public static void delMemo(String fname, String pwd) {
        try {
            writer.write("3");
            writer.newLine();
            writer.write(fname);
            writer.newLine();
            writer.write(pwd);
            writer.newLine();
            writer.flush();

        } catch (IOException ioe) {
            System.err.println("입출력 에러... 3");
        }
    }

    public static ObservableList<TableViewModel> getList() {
        ObservableList<TableViewModel> memo = FXCollections.observableArrayList();

        try {
            String str;
            while ((str = reader.readLine()) == null);
            int cnt = Integer.parseInt(str);

            String line;
            for (int i = 0; i < cnt; i++) {
                line = reader.readLine();
                memo.add(new TableViewModel(line));
            }

        } catch (IOException ioe) {
            System.err.println("입출력 에러...");
        }
        return memo;
    }

    public static boolean checkPassword(String memoName, String passwd) {
        try {
            writer.write("9");
            writer.newLine();
            writer.write(memoName);
            writer.newLine();
            writer.write(passwd);
            writer.newLine();
            writer.flush();

            String str;
            while ((str = reader.readLine()) == null);
            if ("OK".equals(str)) {
                return true;
            } else if ("NO".equals(str)) {
                return false;
            }

            System.out.println("정상적인 수신이 아님...");

        } catch (IOException ioe) {
            System.err.println("패스워드 전송 중 오류...");
            return false;
        }
        return false;
    }

    public static void saveMemo(String fname, String data) {
        try {
            writer.write("4");
            writer.newLine();
            writer.write(fname);
            writer.newLine();
            writer.write(data);
            writer.newLine();
            writer.write("=*= FinFile =*=");
            writer.newLine();
            writer.flush();
        } catch (IOException ioe) {
            System.err.println("전송 오류...");
        }
    }
}
