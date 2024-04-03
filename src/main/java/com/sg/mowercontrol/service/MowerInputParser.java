package com.sg.mowercontrol.service;

import com.sg.mowercontrol.exceptions.InvalidInputException;
import com.sg.mowercontrol.model.Mower;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface MowerInputParser {

    List<Mower> parseInputFile(File inputFile) throws InvalidInputException, FileNotFoundException;
}