package com.sg.mowercontrol.service.impl;

import com.sg.mowercontrol.exceptions.InvalidInputException;
import com.sg.mowercontrol.model.Mower;
import com.sg.mowercontrol.service.MowerService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MowerServiceImpl implements MowerService {

    @Override
    public List<Mower> processInstructions(List<Mower> mowers) throws InvalidInputException {
        if (mowers == null || mowers.isEmpty()) {
            throw new InvalidInputException("Invalid input exception, no data is available for processing");
        }
        mowers.forEach(Mower::processInstructions);
        return mowers;
    }
}

