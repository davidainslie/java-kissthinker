package com.kissthinker.core.xml;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import com.kissthinker.core.text.StringUtil;
import static com.kissthinker.core.object.ClassUtil.path;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test {@link XML}
 * @author David Ainslie
 *
 */
public class XMLTest
{
    /** */
    @Test
    public void create()
    {
        XML xml = createXML();
        System.out.println("\n--- Start ---");
        System.out.println(xml);
        assertTrue(xml.toString().contains("id=\"scooby\""));
    }

    /** */
    @Test
    public void toMap()
    {
        XML xml = createXML();
        Map<String, String> map = xml.toMap();

        System.out.println("\n--- Start ---");

        for (String key : map.keySet())
        {
            System.out.printf("Key->%s  Value->%s%n", key, map.get(key));
        }
    }

    /** */
    @Test
    public void toMapFromInputStream()
    {
        XML xml = createXML();
        Map<String, String> map = XML.toMap(StringUtil.toInputStream(xml.toString()));

        System.out.println("\n--- Start ---");

        for (String key : map.keySet())
        {
            System.out.printf("Key->%s  Value->%s%n", key, map.get(key));
        }
    }

    /** */
    @Test
    public void toMapFromURI()
    {
        File file = new File(path("src/test/resources/", getClass(), "/example.xml"));
        Map<String, String> map = XML.toMap(file.toURI());

        System.out.println("\n--- Start ---");

        for (String key : map.keySet())
        {
            System.out.printf("Key->%s  Value->%s%n", key, map.get(key));
        }
    }

    /** */
    @Test
    public void parse()
    {
        long start = System.currentTimeMillis();

        XML xml = XML.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream(path(getClass(), "/order.xml")));

        assertEquals("AUD", xml.get("//buy/@ccy|CCY"));
        assertEquals("CNY", xml.get("//sell/@ccy|CCY"));
        assertEquals("20120101", xml.get("//fixing/@fixingDate|date"));
        assertEquals("SAEC", xml.get("//fixing/@reference"));

        long stop = System.currentTimeMillis();
        System.out.printf("%nParsing and xpath look up in %s milliseconds%n", stop - start);
    }

    /** */
    @Test
    public void parseList()
    {
        long start = System.currentTimeMillis();

        XML xml = XML.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream(path(getClass(), "/order.xml")));

        xml.get("//sources/source/@", attributeNameValuePairs -> {
            for (Entry<String, String> attributeNameValuePair : attributeNameValuePairs.entrySet())
            {
                System.out.printf("%nAttribute name and value: %s, %s", attributeNameValuePair.getKey(), attributeNameValuePair.getValue());
            }
        });

        long stop = System.currentTimeMillis();
        System.out.printf("%nList parsing and xpath look up in %s milliseconds%n", stop - start);
    }

    /**
     *
     * @return XML
     */
    private XML createXML()
    {
        return XML.create("demo").text("Root blah")
                        .node("demo1").text("Blah 1").attribute("id", "scooby")
                            .node("address").text("Address 1").nodeEnd()
                            .node("country").text("UK").nodeEnd()
                            .nodeEnd()
                        .node("demo2").text("Blah 2")
                            .node("address").text("Address 2").nodeEnd()
                            .node("country").text("USA")
                            .xmlEnd();
    }
}