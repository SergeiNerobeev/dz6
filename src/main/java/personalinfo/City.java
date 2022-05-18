package personalinfo;

public enum City {
  MOSCOW("Москва"), GOMEL("Гомель"), ABAY("Aбай"), KIEV("Киев"), LA("Лос-Анджелес");

  City(String city) {
    this.city = city;
  }

  public String getCity() {
    return city;
  }

  private String city;
}
