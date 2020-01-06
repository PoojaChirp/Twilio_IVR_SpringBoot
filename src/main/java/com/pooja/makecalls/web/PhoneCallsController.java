package com.pooja.makecalls.web;


import com.pooja.makecalls.service.PhoneCallsService;
import com.twilio.http.HttpMethod;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Gather;
import com.twilio.twiml.voice.Hangup;
import com.twilio.twiml.voice.Play;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PhoneCallsController {


    @Autowired
    private PhoneCallsService phoneCallsService;

    @RequestMapping(value="/voicecall", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String retrieveBody(@RequestBody String body) throws UnsupportedEncodingException {

        Map<String, String> bodyMap = convertStringToMap(body);
        String number = URLDecoder.decode(bodyMap.get("From"), "UTF-8");
        String mp3file = "https://raw.githubusercontent.com/PoojaChirp/Twilio_IVR_SpringBoot/master/Trailer-PoojaSrinath.mp3";
        VoiceResponse response = new VoiceResponse.Builder()
                .play(new Play.Builder(mp3file)
                        .build())
                .gather(new Gather.Builder()
                        .action("/menu")
                        .method(HttpMethod.GET)
                        .numDigits(1)
                        .inputs(Arrays.asList(Gather.Input.DTMF))
                        .build())
                .build();
        return response.toXml();
    }

    @RequestMapping(value="/menu", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public String processMenu(HttpServletRequest request) {

        Map<String, String[]> map = request.getParameterMap();
        String selectedOption = map.get("Digits")[0];
           VoiceResponse response;
        switch (selectedOption) {
            case "1":
                response = phoneCallsService.getBookOfTheWeek();
                break;
            case "2":
                response = phoneCallsService.getWeather();
                break;

            case "4":
                response =  new VoiceResponse.Builder().hangup(new Hangup.Builder().build()).build();
                break;
            default:
                response = phoneCallsService.getFortuneCookie();
        }

        return response.toXml();


    }


    private Map<String,String> convertStringToMap(String body) throws UnsupportedEncodingException {
        Map<String, String> myMessage = new HashMap<>() ;
        String s = body;
        String[] pairs = s.split("&");
        for (int i=0;i<pairs.length;i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split("=");
            myMessage.put(URLDecoder.decode(keyValue[0], "UTF-8"), keyValue.length==2?keyValue[1]:"");
        }
        return  myMessage;
    }

}
