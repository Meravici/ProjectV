package org.steps.mediator;

import com.google.gson.Gson;
import org.steps.app.objects.Group;
import org.steps.app.objects.User;
import org.steps.utils.ServerErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by Xato on 12/20/2014.
 */
public class Mediator implements MediatorAPI {
    private static final String SERVER = "http://192.168.84.157/:8888";

    private Gson gson;

    public Mediator() {
        this.gson = new Gson();
    }

    @Override
    public void login(String googleID, String phoneNumber) throws ServerErrorException {
        User user = new User(googleID, phoneNumber);
        String userData = gson.toJson(user);
        sendData(userData);

    }

    @Override
    public void createNewGroup(String name, int imageID) throws ServerErrorException {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        Group group = new Group(0, name, currentTimestamp, imageID);
        String groupData = gson.toJson(group);
        String response = sendData(groupData);
        group.setId(Integer.parseInt(response));


    }


    private String sendData(String jsonString) throws ServerErrorException {
        try {
            HttpURLConnection cn = (HttpURLConnection) new URL(SERVER + jsonString).openConnection();
            cn.setRequestMethod("GET");
            int responseCode = cn.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            InputStream response = cn.getInputStream();

            if (responseCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(response));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                return total.toString();
            }
            // TODO test 404
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

