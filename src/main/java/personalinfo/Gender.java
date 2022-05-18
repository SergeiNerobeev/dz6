package personalinfo;

public enum Gender {
  MALE("m"), FEMALE("f");

  Gender(String gender) {
    this.gender = gender;
  }

  public String getGender() {
    return gender;
  }

  private String gender;
}
