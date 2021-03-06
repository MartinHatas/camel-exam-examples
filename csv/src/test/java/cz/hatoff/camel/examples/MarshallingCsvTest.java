package cz.hatoff.camel.examples;


import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

public class MarshallingCsvTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {

                CsvDataFormat csvDataFormat = new CsvDataFormat();
                csvDataFormat.setDelimiter(";");
                csvDataFormat.setHeader(asList("Name", "Price", "Date"));


                from("direct:csv.in")
                        .marshal(csvDataFormat)
                        .convertBodyTo(String.class)
                        .to("mock:marshalled");
            }
        };
    }

    @Test
    public void testCsvMarshalling() throws Exception {

        String expectedCsv =
                "Name;Price;Date\r\n" +
                        "Martin;48,6;27/11/1987\r\n" +
                        "Veronika;52,4;27/01/1989\r\n";

        Map<String, Object> martinMap = new HashMap<String, Object>();
        martinMap.put("Name", "Martin");
        martinMap.put("Price", "48,6");
        martinMap.put("Date", "27/11/1987");

        Map<String, Object> veronikaMap = new HashMap<String, Object>();
        veronikaMap.put("Name", "Veronika");
        veronikaMap.put("Price", "52,4");
        veronikaMap.put("Date", "27/01/1989");

        sendBody("direct:csv.in", asList(martinMap, veronikaMap));

        MockEndpoint mock = getMockEndpoint("mock:marshalled");

        mock.setExpectedCount(1);

        mock.message(0).body(String.class).isEqualTo(expectedCsv);

        mock.assertIsSatisfied();

    }
}
