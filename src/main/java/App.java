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

          get("/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("notendangered_animals", NotEndangered.all());
            model.put("endangered_animals", EndangeredAnimal.all());
            model.put("template", "templates/animals.vtl");
            return new ModelAndView(model, layout);
          }, new VelocityTemplateEngine());

          post("/animals/new", (request, response) -> {
            String name = request.queryParams("name");
            String species = request.queryParams("species");
            boolean endangered = request.queryParamsValues("endangered")!=null;
            if(endangered){
              String health = request.queryParams("health");
              String age = request.queryParams("age");
              EndangeredAnimal endangeredAnimal = new EndangeredAnimal(name, age, health);
              endangeredAnimal.save();
            } else{
              NotEndangered notEndangered = new NotEndangered(name, species);
              notEndangered.save();
            }
            response.redirect("/animals");
            return null;
          });

          get("/sightings/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("rangerName", request.session().attribute("rangerName"));
            model.put("template", "templates/sighting-form.vtl");
            return new ModelAndView(model, layout);
          }, new VelocityTemplateEngine());

    }
}