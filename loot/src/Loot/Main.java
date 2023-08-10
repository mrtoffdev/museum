package Loot;

import Loot.res.draw.LoginFrame;
import Loot.util.controller.EventList;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    // GLOBALS
    public static final int WIN_WIDTH  = 1280;
    public static final int WIN_HEIGHT = 750;
    
    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException {
        EventList EventList = new EventList();
        EventList.Init();
    }
}
