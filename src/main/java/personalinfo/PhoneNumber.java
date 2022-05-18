package personalinfo;

public enum PhoneNumber {
  FIRSTNUMBER("+7 916 345 68 98"), SECONDNUMBER("+33 334 654 32 10");

  PhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  private String phoneNumber;
}
