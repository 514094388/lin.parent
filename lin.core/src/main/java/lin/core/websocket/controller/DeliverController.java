package lin.core.websocket.controller;

 

import java.util.HashMap;

import java.util.Map;

 

import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.stereotype.Controller;

 

@Controller

public class DeliverController {

 

    @MessageMapping("/deliver")

    @SendTo("/topic/deliver")

    public Map<String, String> greeting(String countryID) throws Exception {

        Map<String, String> resultMap = new HashMap<String, String>();

        if(null != countryID){

                 resultMap.put("status", "success");

            resultMap.put("countryID", countryID);

        }else{

                 resultMap.put("status", "false");

        }

        return resultMap;

    }

}
