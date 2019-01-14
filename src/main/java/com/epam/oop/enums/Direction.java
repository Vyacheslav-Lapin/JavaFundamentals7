package com.epam.oop.enums;

public enum Direction {

  FORWARD(1.0) {
    public Direction opposite() {
      return BACKWARD;
    }
  },
  BACKWARD(2.0) {
    public Direction opposite() {
      return FORWARD;
    }
  };

  private final double ratio;

  Direction(double ratio) {
    this.ratio = ratio;
  }

  public static Direction byRatio(double ratio) {
    for (Direction direction : values())
      if (direction.ratio == ratio)
        return direction;
    throw new IllegalArgumentException();
  }

  public double getRatio() {
    return ratio;
  }
}
