/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author ta2khu75
 */
public class Sound {
    private static Media media;
    private static MediaPlayer player;
    private static File file;
    public static void setSound(String url){
        file=new File("sound/"+url);
        media=new Media(file.toURI().toString());
        player=new MediaPlayer(media);
        player.play();
    }
}
