package cz.hatoff.camel.examples.csv.bindy;



import cz.hatoff.camel.examples.csv.bindy.pojo.Person;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class CsvBindyTest extends CamelTestSupport {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {

                from("direct:csv.in")
                    .to("mock:unmarshalled");

            }
        };
    }

    @Test
    public void testParse() throws Exception {

        String csv =
                "Name,Amount,Date\n" +
                "Martin,48.6,27/11/1987" +
                "Veronika,52.4,27/01/1989";

        sendBody("direct:csv.in", csv.getBytes());

        MockEndpoint mock = getMockEndpoint("mock:unmarshalled");

        mock.setExpectedCount(2);

        mock.expectedBodiesReceivedInAnyOrder(
                new Person("Martin", 48.6, new SimpleDateFormat(DATE_FORMAT).parse("27/11/1987")),
                new Person("Veronika", 52.4, new SimpleDateFormat(DATE_FORMAT).parse("27/01/1989"))
        );

        mock.assertIsSatisfied();
        
    }
}
