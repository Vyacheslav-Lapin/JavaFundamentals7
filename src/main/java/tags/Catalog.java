package tags;

import lombok.Setter;
import lombok.SneakyThrows;
import model.Gun;

import javax.servlet.jsp.tagext.TagSupport;
import java.util.Collection;

public class Catalog extends TagSupport {

  @Setter
  private Collection<Gun> guns;

  public static String getGunList(Collection<Gun> guns) {
    StringBuilder out = new StringBuilder();
    for (Gun gun : guns)
      out.append("<tr><td><a href=\"/buy/?id=")
        .append(gun.getId())
        .append("\">")
        .append(gun.getName())
        .append("</a></td><td>")
        .append(gun.getCaliber())
        .append("</td></tr>");

    return out.toString();
  }

  @Override
  @SneakyThrows
  public int doStartTag() {
    pageContext.getOut().print(getGunList(guns));

    return SKIP_BODY;
  }
}
