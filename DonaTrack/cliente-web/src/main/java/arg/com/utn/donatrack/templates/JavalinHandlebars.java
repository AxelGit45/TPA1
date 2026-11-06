package arg.com.utn.donatrack.templates;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.FileRenderer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JavalinHandlebars implements FileRenderer {

  Handlebars handlebars = new Handlebars(new ClassPathTemplateLoader("/templates", ".html.hbs"));

  @NotNull
  @Override
  public String render(@NotNull String path, @NotNull Map<String, ?> model, @NotNull Context context) {
    try {
      String templateName = path.replace(".html.hbs", "").replace(".hbs", "");
      Template template = handlebars.compile(templateName);

      // Copia mutable del modelo (puede venir de Map.of) + datos de sesión para todas las vistas
      Map<String, Object> modeloCompleto = new HashMap<>(model);
      String rolSesion = context.sessionAttribute("rol");
      modeloCompleto.put("logueado", rolSesion != null);
      modeloCompleto.put("rolSesion", rolSesion);

      return template.apply(modeloCompleto);
    } catch (IOException e) {
      e.printStackTrace();
      context.status(HttpStatus.NOT_FOUND);
      return "No se encuentra la página indicada:" + path;
    }
  }
}