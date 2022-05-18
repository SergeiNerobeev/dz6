package personalinfo;

public enum Englishlevel {
  NOTINDICATED("Не указано"), BEGINNER("Начальный уровень (Beginner)"), ELEMENTARY("Элементарный уровень (Elementary)"), INTERMEDIATE("Средний (Intermediate)");

  Englishlevel(String englishLevel) {
    this.englishLevel = englishLevel;
  }

  public String getEnglishLevel() {
    return englishLevel;
  }

  private String englishLevel;
}
