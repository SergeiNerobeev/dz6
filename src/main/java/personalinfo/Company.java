package personalinfo;

public enum Company {
  EMI("EMI Records"), SONY("Sony Records"), SHELL("Shell");

  public String getCompany() {
    return company;
  }

  Company(String company) {
    this.company = company;
  }

  private String company;
}
