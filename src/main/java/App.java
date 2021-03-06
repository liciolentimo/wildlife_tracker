import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {

    ProcessBuilder process = new ProcessBuilder();
    Integer port;
    if (process.environment().get("PORT") != null) {
      port = Integer.parseInt(process.environment().get("PORT"));
    } else {
      port = 4567;
    }

    setPort(port);

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
      boolean endangered = request.queryParamsValues("endangered") != null;
      if (endangered) {
        String health = request.queryParams("health");
        String age = request.queryParams("age");
        EndangeredAnimal endangeredAnimal = new EndangeredAnimal(name, age, health, species);
        endangeredAnimal.save();
      } else {
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

    post("/sightings/new", (request, response) -> {
      String rangerName = request.queryParams("rangerName");
      String location = request.queryParams("location");
      Sighting sighting = new Sighting(location, rangerName);
      sighting.save();
      String[] animalArray = request.queryParams("animalname").split(",");
      Animal animal;
      for (String animalName : animalArray) {
        animal = NotEndangered.findAnimalByName(animalName);
        if (animal == null) {
          animal = EndangeredAnimal.findAnimalByName(animalName);
        }
        if (animal != null) {
          sighting.addAnimal(animal);
        } else {
          throw new NullPointerException("Sorry, Animal not found. Please add the animal to the database first.");
        }
      }
      response.redirect("/sightings");
      return null;
    });

    get("/sightings", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("sightings", Sighting.listByDate());
      model.put("template", "templates/sightings.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sightings/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Sighting sighting = Sighting.find(Integer.parseInt(request.params("id")));
      model.put("sighting", sighting);
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("template", "templates/sighting.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sightings/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Sighting sighting = Sighting.find(Integer.parseInt(request.params("id")));
      sighting.delete();
      response.redirect("/sightings");
      return null;
    });

    get("/animals/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      NotEndangered animal = NotEndangered.find(Integer.parseInt(request.params("id")));
      model.put("animal", animal);
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("template", "templates/animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      NotEndangered animal = NotEndangered.find(Integer.parseInt(request.params("id")));
      animal.delete();
      response.redirect("/animals");
      return null;
    });

    get("/animals/endangered/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      EndangeredAnimal animal = EndangeredAnimal.find(Integer.parseInt(request.params("id")));
      model.put("animal", animal);
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("template", "templates/animal.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/endangered/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      EndangeredAnimal animal = EndangeredAnimal.find(Integer.parseInt(request.params("id")));
      animal.delete();
      response.redirect("/animals");
      return null;
    });

    exception(NullPointerException.class, (exc, request, response) -> {
      response.status(500);
      VelocityTemplateEngine engine = new VelocityTemplateEngine();
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("message", exc.getMessage());
      model.put("template", "templates/notfound.vtl");
      String html = engine.render(new ModelAndView(model, layout));
      response.body(html);
    });

    exception(IndexOutOfBoundsException.class, (exc, request, response) -> {
      response.status(404);
      VelocityTemplateEngine engine = new VelocityTemplateEngine();
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("rangerName", request.session().attribute("rangerName"));
      model.put("message", exc.getMessage());
      model.put("template", "templates/notfound.vtl");
      String html = engine.render(new ModelAndView(model, layout));
      response.body(html);
    });

    exception(NumberFormatException.class, (exc, req, res) -> {
      res.status(404);
      VelocityTemplateEngine engine = new VelocityTemplateEngine();
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", "It looks like that page doesn't exist!");
      model.put("template", "templates/notfound.vtl");
      String html = engine.render(new ModelAndView(model, layout));
      res.body(html);
    });


  }
}