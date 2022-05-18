package personalinfo;

public enum Password {
  PASSWORD("1qaz2wsx");

  private String password;

  Password(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }
}
