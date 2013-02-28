/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.frc.team1450;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class VisionSystem {

    public static void main(String[] args) throws ParseException {

        // create Options object
        Options options = new Options();

        // add t option
        options.addOption("debug", false, "debug - ie. parse images from file, not camera");

        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(options, args);

        VisionSystem vision = new VisionSystem();

        if (cmd.hasOption("debug")) {
            vision.runTestImages();
        } else {
            vision.startTargeting();
        }
    }

    private void startTargeting() {
        System.out.println("Target Go!");
        // 0-default camera, 1 - next...so on
        //final OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        //grabber.setFormat("jpg");
        //  grabber.start();
        //IplImage img = grabber.grab();
        //  cvSaveImage("images/image16.jpg", img);
    }

    private void runTestImages() {
        try {
            FindTarget findTarget = new FindTarget();
            String files[] = {"images/image16.jpg",
                "images/image18.jpg",
                "images/image19.jpg",
                "images/image20.jpg",
                "images/image21.jpg",
                "images/image22.jpg",
                "images/image23.jpg",
                "images/image24.jpg",
                "images/image25.jpg",
                "images/image26.jpg",
                "images/image27.jpg",
                "images/image28.jpg"};
            
            
            for (String file: files) {
                findTarget.run(file);
            }
            
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
