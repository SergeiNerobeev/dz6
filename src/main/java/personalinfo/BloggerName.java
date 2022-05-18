package personalinfo;

public enum BloggerName {
  AMERICANO("Americano"), ITALIANO("Italiano"), NAPOLITANO("Napolitano");

  BloggerName(String bloggerName) {
    this.bloggerName = bloggerName;
  }

  public String getBloggerName() {
    return bloggerName;
  }

  private String bloggerName;
}
