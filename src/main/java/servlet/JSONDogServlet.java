package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Dog;
import persistence.orm.Lworm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
and yes... it's a bit scrappy. I need to do some major LWORM refactory that I only now see from having this somewhat broken implementation.
Namely that it's inconsistent how I'm writing the actual queries
*/
/**
 * @author Evan Ritchey
 * @since 11|23|2021
 * showing off the CRUD functionality of our LWORM by overriding each of the corresponding HTTP verbs
 * examples utilize our Dog classes using Jackson JSON
 */
@WebServlet(value = "/jsonDogServlet")
public class JSONDogServlet extends HttpServlet {

    private Lworm lworm;
    private final ObjectMapper mapper;
    public JSONDogServlet(){
        this.lworm = new Lworm();
        this.mapper = new ObjectMapper();
    }

    /**
     * create
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dog dog = mapper.readValue(req.getReader().lines().collect(Collectors.joining()),Dog.class);
//        System.out.println(dog);
        lworm.create(dog);

        resp.setHeader("Content-Type","application/json");
        resp.setStatus(201);
    }

    /**
     * read
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //retrieve the request contents
        List<Lworm.FieldValuePair> fieldsValues = new ArrayList<>();

        String[] nameField = req.getParameterValues("name");
        if(nameField != null) {
            for (String s : nameField) {
                fieldsValues.add(new Lworm.FieldValuePair("name", s));
            }
        }
        String[] breedField = req.getParameterValues("breed");
        if(breedField != null) {
            for (String s : breedField) {
                fieldsValues.add(new Lworm.FieldValuePair("breed", s));
            }
        }
        String[] coinField = req.getParameterValues("coin");
        if(coinField != null) {
            for (String s : coinField) {
                fieldsValues.add(new Lworm.FieldValuePair("coin", Long.parseLong(s)));
            }
        }
        String[] ageField = req.getParameterValues("age");
        if(ageField != null) {
            for (String s : ageField) {
                fieldsValues.add(new Lworm.FieldValuePair("age", Integer.parseInt(s)));
            }
        }


        if(fieldsValues.isEmpty()){
            resp.setStatus(400);//sent an empty query some how
            return;
        }

        //make the DB query
//        Dog[] dogs = (Dog[]) lworm.get(new Dog(), (Lworm.FieldValuePair[]) fieldsValues.toArray());
        //I don't know why you have to do it this way as opposed to the above, but this way works.
        Lworm.FieldValuePair[] fvp = new Lworm.FieldValuePair[fieldsValues.size()];
        for(int i = 0; i < fvp.length; i++)
            fvp[i] = fieldsValues.get(i);

        Object[] dogs = lworm.get(new Dog(),fvp); //The actual query

        if(dogs == null){//no query results
            resp.setStatus(404);
            return;
        }

        //display
        for(Object dog : dogs){
            resp.getWriter().println(mapper.writeValueAsString(dog));
        }

        resp.setHeader("Content-Type", "application/json");
        resp.setStatus(200);
    }


    /**
     * update (replace)
     * Current setup: values are in the HTTP body req, constraints are in the HTTP parameters.
     * The body needs to be a fully filled in Dog
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //retrieve the request contents
        List<Lworm.FieldValuePair> fieldsValues = new ArrayList<>();

        String[] nameField = req.getParameterValues("name");
        if(nameField != null) {
            for (String s : nameField) {
                fieldsValues.add(new Lworm.FieldValuePair("name", s));
            }
        }
        String[] breedField = req.getParameterValues("breed");
        if(breedField != null) {
            for (String s : breedField) {
                fieldsValues.add(new Lworm.FieldValuePair("breed", s));
            }
        }
        String[] coinField = req.getParameterValues("coin");
        if(coinField != null) {
            for (String s : coinField) {
                fieldsValues.add(new Lworm.FieldValuePair("coin", Long.parseLong(s)));
            }
        }
        String[] ageField = req.getParameterValues("age");
        if(ageField != null) {
            for (String s : ageField) {
                fieldsValues.add(new Lworm.FieldValuePair("age", Integer.parseInt(s)));
            }
        }


        if(fieldsValues.isEmpty()){
            resp.setStatus(400);//sent an empty query some how
            return;
        }

        //make the DB query
//        Dog[] dogs = (Dog[]) lworm.get(new Dog(), (Lworm.FieldValuePair[]) fieldsValues.toArray());
        //I don't know why you have to do it this way as opposed to the above, but this way works.
        Lworm.FieldValuePair[] fvp = new Lworm.FieldValuePair[fieldsValues.size()];
        for(int i = 0; i < fvp.length; i++)
            fvp[i] = fieldsValues.get(i);

        Dog dog = mapper.readValue(req.getReader().lines().collect(Collectors.joining()),Dog.class);
        lworm.update(dog,
                new Lworm.FieldValuePair[]{
                        new Lworm.FieldValuePair("name",dog.getName()),
                        new Lworm.FieldValuePair("breed",dog.getBreed()),
                        new Lworm.FieldValuePair("age",dog.getAge()),
                        new Lworm.FieldValuePair("coin",dog.getCoin())
                },
                fvp
        ); //The actual query

        resp.setHeader("Content-Type", "application/json");
        resp.setStatus(200);
    }

    /**
     * delete ¯\_(ツ)_/¯
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //retrieve the request contents
        List<Lworm.FieldValuePair> fieldsValues = new ArrayList<>();

        String[] nameField = req.getParameterValues("name");
        if(nameField != null) {
            for (String s : nameField) {
                fieldsValues.add(new Lworm.FieldValuePair("name", s));
            }
        }
        String[] breedField = req.getParameterValues("breed");
        if(breedField != null) {
            for (String s : breedField) {
                fieldsValues.add(new Lworm.FieldValuePair("breed", s));
            }
        }
        String[] coinField = req.getParameterValues("coin");
        if(coinField != null) {
            for (String s : coinField) {
                fieldsValues.add(new Lworm.FieldValuePair("coin", Long.parseLong(s)));
            }
        }
        String[] ageField = req.getParameterValues("age");
        if(ageField != null) {
            for (String s : ageField) {
                fieldsValues.add(new Lworm.FieldValuePair("age", Integer.parseInt(s)));
            }
        }


        if(fieldsValues.isEmpty()){
            resp.setStatus(400);//sent an empty query some how
            return;
        }

        //make the DB query
//        Dog[] dogs = (Dog[]) lworm.get(new Dog(), (Lworm.FieldValuePair[]) fieldsValues.toArray());
        //I don't know why you have to do it this way as opposed to the above, but this way works.
        Lworm.FieldValuePair[] fvp = new Lworm.FieldValuePair[fieldsValues.size()];
        for(int i = 0; i < fvp.length; i++)
            fvp[i] = fieldsValues.get(i);

        boolean success = lworm.delete(new Dog(),fvp); //The actual query
        if(!success){
            resp.setStatus(500);
            return;
        }

        resp.setHeader("Content-Type", "application/json");
        resp.setStatus(200);
    }
}
