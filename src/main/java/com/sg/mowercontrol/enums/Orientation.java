package com.sg.mowercontrol.enums;

public enum Orientation {

    N,
    E,
    S,
    W;

    public Orientation rotateLeft() {
        return switch (this) {
            case N -> W;
            case E -> N;
            case S -> E;
            case W -> S;
        };
    }

    public Orientation rotateRight() {
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
        };
    }
}

