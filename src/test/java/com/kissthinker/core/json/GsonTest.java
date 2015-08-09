package com.kissthinker.core.json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import static com.kissthinker.object.ClassUtil.path;

/**
 * @author David Ainslie
 *
 */
public class GsonTest
{
    /** */
    private final String exampleJSONLocation = path("src/test/resources/", getClass(), "example.json");

    /**
     * @throws IOException
     *
     */
    @Test
    public void test() throws IOException
    {
        Bean bean = new Bean(null);

        Gson gson = new Gson();
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

        // Convert java object to JSON format, and returned as JSON formatted string
        String json = gson.toJson(bean);
        System.out.printf("JSON from bean: %s%n", json);
        System.out.printf("Pretty JSON from bean:%n%s%n", prettyGson.toJson(bean));

        // Write converted JSON data to a file named "example.json"
        FileWriter fileWriter = new FileWriter(exampleJSONLocation);
        fileWriter.write(json);
        fileWriter.close();

        // Going to read back in.
        BufferedReader bufferedReader = new BufferedReader(new FileReader(exampleJSONLocation));

        // Convert the json string back to object
        Bean beanFromJSON = gson.fromJson(bufferedReader, Bean.class);
        System.out.printf("Bean from JSON: %s%n", beanFromJSON);
    }
}