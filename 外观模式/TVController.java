/**
 * Created by Zephyr on 2017/1/4.
 */
public class TVController {
    private PowerSystem powerSystem=new PowerSystem();
    private VoiceSystem voiceSystem=new VoiceSystem();
    private ChannelSystem channelSystem=new ChannelSystem();
    public void powerOn(){
        powerSystem.powerOn();
    }
    public void powerOff(){
        powerSystem.powerOff();
    }
    public void turnUp(){
        voiceSystem.turnUp();
    }
    public void turnDown(){
        voiceSystem.turnDown();
    }
    public void nextChannel(){
        channelSystem.next();
    }
    public void prevChannel(){
        channelSystem.prev();
    }
}
