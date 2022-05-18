package personalinfo;

public enum Url {

  URLOTUS("https://otus.ru/");

  private final String urlOtus;

  Url(String urlOtus) {
    this.urlOtus = urlOtus;
  }

  public String getUrlOtus() {
    return urlOtus;
  }
}
