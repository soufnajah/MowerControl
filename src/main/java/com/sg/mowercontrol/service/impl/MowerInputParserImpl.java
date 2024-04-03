package com.sg.mowercontrol.service.impl;

import com.sg.mowercontrol.enums.Instruction;
import com.sg.mowercontrol.enums.Orientation;
import com.sg.mowercontrol.exceptions.InvalidInputException;
import com.sg.mowercontrol.model.Mower;
import com.sg.mowercontrol.service.MowerInputParser;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class MowerInputParserImpl implements MowerInputParser {

    @Override
    public List<Mower> parseInputFile(File inputFile) throws InvalidInputException, FileNotFoundException {

        List<Mower> mowers = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputFile)) {

            if (!scanner.hasNextLine()) {
                throw new InvalidInputException("Empty input file");
            }

            String[] dimensions = scanner.nextLine().split(" ");
            if (dimensions.length != 2) {
                throw new InvalidInputException("Invalid dimensions format: " + Arrays.toString(dimensions));
            }

            int maxX = Integer.parseInt(dimensions[0]);
            int maxY = Integer.parseInt(dimensions[1]);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length != 3) {
                    throw new InvalidInputException("Invalid input format: " + line);
                }

                try {
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    char orientation = parts[2].charAt(0);

                    if (orientation != 'N'
                            && orientation != 'E'
                            && orientation != 'S'
                            && orientation != 'W') {
                        throw new InvalidInputException("Invalid orientation: " + orientation);
                    }

                    if (!scanner.hasNextLine()) {
                        throw new InvalidInputException("Unexpected end of file");
                    }

                    String instructionsFeed = scanner.nextLine();
                    if (!instructionsFeed.matches("[GAD]+")) {
                        throw new InvalidInputException("Invalid instruction format: " + instructionsFeed);
                    }
                    Instruction[] instructions = Arrays.stream(instructionsFeed.split(""))
                            .map(Instruction::valueOf)
                            .toArray(Instruction[]::new);
                    mowers.add(new Mower(x, y, Orientation.valueOf(String.valueOf(orientation)), maxX, maxY, instructions));

                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    throw new InvalidInputException("Invalid numeric values or orientation: " + line);
                }
            }
        }
        return mowers;
    }
}




