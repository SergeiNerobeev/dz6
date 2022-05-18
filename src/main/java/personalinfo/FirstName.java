package personalinfo;

public enum FirstName {
  JOHN("John"), YANA("Яна"), LUKAS("Lukaswertugifodsdplasnzbxcfghjklmnbvqazxbcndhfeuroqaqzxsnwedcrfvtgbnhyujmujmkiolujmkliopmgjfdh");

  FirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFirstName() {
    return firstName;
  }

  private String firstName;
}
