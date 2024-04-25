package com.telegram.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telegram.dto.AuthUser;

@Service
public class CommsService {
	
	@Autowired
	private BotService bot; // = new BotService();
	
	private final String botToken = System.getenv("bot_token");
	private final String baseChatId = System.getenv("base_chat_id");
	
	
	public String initiateBot() {
		//if(!botRunning()) {
			return bot.sendNewMessage(bot);
		//}
		
	}
	
	private boolean botRunning() {
		boolean result = false;
        try {
            URL url = new URL("https://api.telegram.org/bot" + botToken + "/getMe");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String response = in.readLine();
                System.out.println("Response from Telegram API: " + response);
                // Parse the response JSON to check if the bot is running
                // For example, you can check if the response contains the bot's username
                if (response.contains("AntaresApiBot")) {
                    System.out.println("Bot is running.");
                    result = true;
                } else {
                    System.out.println("Bot is not running.");
                }
                in.close();
            } else {
                System.out.println("Error: Unable to connect to Telegram API. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
	}
	
	public String getChatId(AuthUser user) {
		String result = "false";
        try {
            URL url = new URL("http://127.0.0.1:8082/Auth/telegramChatId");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            String requestBody = "{\"username\":\""+user.getUsername()+"\",\"password\":\""+user.getPassword()+"\"}";
            
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();
            
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.println("Response: " + response.toString());
            connection.disconnect();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "0";
	}
	

}
