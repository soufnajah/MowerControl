package com.sg.mowercontrol;

import com.sg.mowercontrol.exceptions.InvalidInputException;
import com.sg.mowercontrol.model.Mower;
import com.sg.mowercontrol.service.MowerInputParser;
import com.sg.mowercontrol.service.MowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@SpringBootApplication
public class MowerControlApplication implements CommandLineRunner {

    private final MowerInputParser mowerInputParser;
    private final MowerService mowerService;

    @Autowired
    public MowerControlApplication(MowerInputParser mowerInputParser, MowerService mowerService) {
        this.mowerInputParser = mowerInputParser;
        this.mowerService = mowerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MowerControlApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar <jar-file> <input-file>");
            return;
        }
        try {
            List<Mower> mowers = mowerInputParser.parseInputFile(new File(args[0]));
            mowerService
                    .processInstructions(mowers)
                    .forEach(mower -> System.out.println(mower.getCoordinates().x() + " " + mower.getCoordinates().y() + " " + mower.getOrientation()));
        } catch (FileNotFoundException e) {
            System.err.println("Input File Not Found" + e.getMessage());
        } catch (InvalidInputException e) {
            System.err.println("Exception occurred during workflow processing: " + e.getMessage());
        }
    }
}


