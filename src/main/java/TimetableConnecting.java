import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TimetableConnecting {

    public String getData (String nameTeacher) {
        String query = "https://urfu.ru/api/schedule/teacher/suggest/?query=" + nameTeacher;
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(query).openConnection();

            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);

            connection.connect();

            StringBuilder data = new StringBuilder();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = in.readLine()) != null){
                    data.append(line);
                    data.append("\n");
                }
                String _data = new String();
                _data = data.toString();
                return _data;
            } else {
                throw new RuntimeException();
            }
        } catch (Throwable error){
            error.printStackTrace();
        } finally {
            StringBuilder data = new StringBuilder();
            if (connection != null){
                connection.disconnect();
            }
        }
        return new String();
    }
}