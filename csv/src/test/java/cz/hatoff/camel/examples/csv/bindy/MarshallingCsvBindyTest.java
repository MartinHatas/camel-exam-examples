package cz.hatoff.camel.examples.csv.bindy;

import cz.hatoff.camel.examples.csv.pojo.Person;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static java.util.Arrays.asList;

public class MarshallingCsvBindyTest extends CamelTestSupport {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:csv.in")
                        .marshal().bindy(BindyType.Csv, Person.class)
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

        sendBody("direct:csv.in", asList(
                new Person("Martin", new BigDecimal(48.6), new SimpleDateFormat(DATE_FORMAT).parse("27/11/1987")),
                new Person("Veronika", new BigDecimal(52.4), new SimpleDateFormat(DATE_FORMAT).parse("27/01/1989"))
        ));

        MockEndpoint mock = getMockEndpoint("mock:marshalled");

        mock.setExpectedCount(1);

        mock.message(0).body(String.class).isEqualTo(expectedCsv);

        mock.assertIsSatisfied();

    }
}
