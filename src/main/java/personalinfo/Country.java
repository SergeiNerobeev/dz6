package personalinfo;

public enum Country {
  RUSSIA("Россия"), BELORUS("Республика Белорусь"), KAZAHSTAN("Казахстан"), UKRAIN("Украина"), USA("США");

  Country(String country) {
    this.country = country;
  }

  public String getCountry() {
    return country;
  }

  private String country;



}
