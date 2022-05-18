package personalinfo;

public enum Position {
  POSITIONONE("FOH"), POSITIONSECOND("Hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhooooooo");
  private String position;

  public String getPosition() {
    return position;
  }

  Position(String position) {
    this.position = position;
  }
}
