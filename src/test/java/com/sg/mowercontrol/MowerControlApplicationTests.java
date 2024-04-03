package com.sg.mowercontrol;

import com.sg.mowercontrol.enums.Instruction;
import com.sg.mowercontrol.enums.Orientation;
import com.sg.mowercontrol.exceptions.InvalidInputException;
import com.sg.mowercontrol.model.Mower;
import com.sg.mowercontrol.service.MowerInputParser;
import com.sg.mowercontrol.service.MowerService;
import com.sg.mowercontrol.service.impl.MowerInputParserImpl;
import com.sg.mowercontrol.service.impl.MowerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MowerControlApplicationTests {

    private MowerInputParser mowerInputParser;
    private MowerService mowerService;

    @BeforeEach
    public void setUp() {
        mowerInputParser = new MowerInputParserImpl();
        mowerService = new MowerServiceImpl();
    }

    @Test
    public void should_return_expected_output_when_file_is_found_and_data_format_is_valid()
            throws InvalidInputException, FileNotFoundException {

        // Given
        File inputFile = new File("src/test/resources/data.txt");
        List<Mower> mowers = mowerInputParser.parseInputFile(inputFile);

        // When
        List<Mower> result = mowerService.processInstructions(mowers);

        // Then
        assertEquals(2, result.size());
        Mower finalMowerPosition = result.get(0);

        assertEquals(1, finalMowerPosition.getCoordinates().x());
        assertEquals(3, finalMowerPosition.getCoordinates().y());

        assertEquals(Orientation.N, finalMowerPosition.getOrientation());
    }

    @Test
    public void should_throw_exception_when_file_not_found() {

        // Given
        File inputFile = new File("src/test/resources/nonexistent-file.txt");

        // When & Then
        assertThrows(FileNotFoundException.class, () -> mowerInputParser.parseInputFile(inputFile));
        assertThrows(InvalidInputException.class, () -> mowerService.processInstructions(new ArrayList<>()));
    }

    @Test
    public void should_throw_exception_when_file_found_but_data_format_invalid() {

        // Given
        File inputFile = new File("src/test/resources/invalid-data.txt");

        // When & Then
        assertThrows(InvalidInputException.class, () -> mowerInputParser.parseInputFile(inputFile));
        assertThrows(InvalidInputException.class, () -> mowerService.processInstructions(new ArrayList<>()).size());
    }

    @Test
    public void should_processing_instructions_result_in_parsing_exception() {
        // Given
        File inputFile = new File("src/test/resources/invalid-format-data2.txt");

        // When & Then
        assertThrows(InvalidInputException.class, () -> mowerInputParser.parseInputFile(inputFile));
    }

    @Test
    public void should_processing_instruction_with_rogue_file_result_in_exception() {
        // Given
        File inputFile = new File("src/test/resources/unexpected-end-of-file-data.txt");

        // When & Then
        assertThrows(InvalidInputException.class, () -> mowerInputParser.parseInputFile(inputFile));
    }

    @Test
    public void should_parse_input_file_correctly_with_valid_input() throws IOException, InvalidInputException {
        // Given
        String validInput = "5 5\n1 2 N\nAADAADADDA";
        File inputFile = createTempFile(validInput);

        // When
        List<Mower> mowers = mowerInputParser.parseInputFile(inputFile);

        // Then
        assertEquals(1, mowers.size());
        Mower mower = mowers.get(0);
        assertEquals(1, mower.getCoordinates().x());
        assertEquals(2, mower.getCoordinates().y());
        assertEquals(Orientation.N, mower.getOrientation());
        assertArrayEquals("AADAADADDA".chars().mapToObj(c -> Instruction.valueOf((char) c + "")).toArray(), mower.getInstructions());
    }

    @Test
    public void should_throw_exception_when_data_not_matchs_the_regex() throws IOException {
        // Given
        String invalidInput = "5 5\n1 2 N\nLM1LMLMLMM";
        File inputFile = createTempFile(invalidInput);
        MowerInputParser mowerInputParser = new MowerInputParserImpl();

        // When & Then
        assertThrows(InvalidInputException.class, () -> mowerInputParser.parseInputFile(inputFile));
    }


    private File createTempFile(String content) throws IOException {
        File tempFile = Files.createTempFile("test", ".txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(content);
        }
        return tempFile;
    }
}
