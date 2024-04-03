package com.sg.mowercontrol.model;

import com.sg.mowercontrol.coordinates.Coordinates;
import com.sg.mowercontrol.enums.Instruction;
import com.sg.mowercontrol.enums.Orientation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Mower {

    private Coordinates coordinates;
    private Orientation orientation;
    private final int maxX;
    private final int maxY;
    private final Instruction[] instructions;


    public Mower(int x, int y, Orientation orientation, int maxX, int maxY, Instruction[] instructions) {
        this.coordinates = new Coordinates(x, y);
        this.orientation = orientation;
        this.maxX = maxX;
        this.maxY = maxY;
        this.instructions = instructions;
    }

    public void processInstructions() {
        for (Instruction instruction : instructions) {
            switch (instruction) {
                case G -> rotateLeft();
                case D -> rotateRight();
                case A -> moveForward();
            }
        }
    }

    private void rotateLeft() {
        orientation = orientation.rotateLeft();
    }

    private void rotateRight() {
        orientation = orientation.rotateRight();
    }

    private void moveForward() {
        switch (orientation) {
            case N -> coordinates = new Coordinates(coordinates.x(), Math.min(coordinates.y() + 1, maxY));
            case E -> coordinates = new Coordinates(Math.min(coordinates.x() + 1, maxX), coordinates.y());
            case S -> coordinates = new Coordinates(coordinates.x(), Math.max(coordinates.y() - 1, 0));
            case W -> coordinates = new Coordinates(Math.max(coordinates.x() - 1, 0), coordinates.y());
        }
    }
}



