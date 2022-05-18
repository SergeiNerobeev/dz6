package personalinfo;

public enum LastName {
  SMITH("Smith"), COOCK("Coock"), BRENSON("Brenson");

  LastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  private String lastName;
}
