import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/animals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("HEALTH_HEALTHY", EndangeredAnimal.HEALTH_HEALTHY);
            model.put("HEALTH_OK", EndangeredAnimal.HEALTH_OK);
            model.put("HEALTH_ILL", EndangeredAnimal.HEALTH_ILL);
            model.put("AGE_NEW", EndangeredAnimal.AGE_NEW);
            model.put("AGE_YOUNG", EndangeredAnimal.AGE_YOUNG);
            model.put("AGE_ADULT", EndangeredAnimal.AGE_ADULT);
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("template", "templates/animal-form.vtl");
            return new ModelAndView(model, layout);
          }, new VelocityTemplateEngine());

    }
}