package personalinfo;

public enum Login {
  LOGIN("temiraj871@procowork.com");


  private String login;

  Login(String login) {
    this.login = login;
  }

  public String getLogin() {
    return login;
  }
}
