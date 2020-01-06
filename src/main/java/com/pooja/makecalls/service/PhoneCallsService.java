package com.pooja.makecalls.service;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Hangup;
import com.twilio.twiml.voice.Say;
import org.springframework.stereotype.Component;

@Component
public class PhoneCallsService {


    public VoiceResponse getBookOfTheWeek() {

        VoiceResponse response = new VoiceResponse.Builder()
                .say(new Say.Builder(
                        "The book of the week for this turn is Harry Potter and the Goblet of fire."+"We hope you enjoy this recommendation")
                        .voice(Say.Voice.ALICE)
                        .language(Say.Language.EN_GB)
                        .build())
                .hangup(new Hangup.Builder().build())
                .build();

        return response;
    }

    public VoiceResponse getWeather() {

        VoiceResponse response = new VoiceResponse.Builder()
                .say(new Say.Builder(
                        "It is bright and sunny outside just like it is inside all of us"+"Stay warm for this winter."+"Winter is coming")
                        .voice(Say.Voice.ALICE)
                        .language(Say.Language.EN_GB)
                        .build())
                .hangup(new Hangup.Builder().build())
                .build();

        return response;
    }


    public VoiceResponse getFortuneCookie() {

        VoiceResponse response = new VoiceResponse.Builder()
                .say(new Say.Builder(
                        "Here is your fortune cookie"+"No one can make you feel inferior without your consent"+"We hope you enjoyed our Fortune cookie of the day"
                                +"Good Bye")
                        .voice(Say.Voice.ALICE)
                        .language(Say.Language.EN_GB)
                        .build())
                .hangup(new Hangup.Builder().build())
                .build();

        return response;
    }
}
