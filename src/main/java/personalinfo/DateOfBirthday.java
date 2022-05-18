package personalinfo;

public enum DateOfBirthday {
  DATEONE("01.01.2001"), DATESECOND("30.02.2018");
  private String dateOfBirthday;

  public String getDateOfBirthday() {
    return dateOfBirthday;
  }

  DateOfBirthday(String dateOfBirthday) {
    this.dateOfBirthday = dateOfBirthday;
  }
}
