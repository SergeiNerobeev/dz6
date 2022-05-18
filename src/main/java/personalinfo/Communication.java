package personalinfo;

public enum Communication {
  FACEBOOK("Facebook"), VK("VK"), OK("OK"), SKYPE("Skype");

  Communication(String communication) {
    this.communication = communication;
  }

  public String getCommunication() {
    return communication;
  }

  private String communication;
}
