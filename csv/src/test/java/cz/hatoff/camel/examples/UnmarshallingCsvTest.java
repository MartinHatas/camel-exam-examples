package cz.hatoff.camel.examples;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class UnmarshallingCsvTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {

                CsvDataFormat csvDataFormat = new CsvDataFormat();
                csvDataFormat.setDelimiter(";");
                csvDataFormat.setSkipHeaderRecord(true);

                from("direct:csv.in")
                        .unmarshal(csvDataFormat)
                        .split(body())
                        .to("mock:unmarshalled");
            }
        };
    }

    @Test
    public void testCsvUnmarshalling() throws Exception {

        String csv =
                "Name;Price;Date\n" +
                        "Martin;48.6;27/11/1987\n" +
                        "Veronika;52.4;27/01/1989";

        sendBody("direct:csv.in", csv.getBytes());

        MockEndpoint mock = getMockEndpoint("mock:unmarshalled");

        mock.setExpectedCount(2);

        mock.message(0).body(List.class).isEqualTo(Arrays.asList("Martin", "48.6", "27/11/1987"));
        mock.message(1).body(List.class).isEqualTo(Arrays.asList("Veronika", "52.4", "27/01/1989"));

        mock.assertIsSatisfied();

    }

}
