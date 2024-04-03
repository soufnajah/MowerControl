package com.sg.mowercontrol.service;

import com.sg.mowercontrol.exceptions.InvalidInputException;
import com.sg.mowercontrol.model.Mower;

import java.util.List;

public interface MowerService {

    List<Mower> processInstructions(List<Mower> mowers) throws InvalidInputException;
}