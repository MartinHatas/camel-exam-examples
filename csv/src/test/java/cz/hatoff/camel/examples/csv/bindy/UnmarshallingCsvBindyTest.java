package cz.hatoff.camel.examples.csv.bindy;



import cz.hatoff.camel.examples.csv.bindy.pojo.Person;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class UnmarshallingCsvBindyTest extends CamelTestSupport {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:csv.in")
                    .unmarshal().bindy(BindyType.Csv, Person.class)
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

        mock.message(0).body(Person.class).isEqualTo(new Person("Martin", new BigDecimal(48.6), new SimpleDateFormat(DATE_FORMAT).parse("27/11/1987")));
        mock.message(1).body(Person.class).isEqualTo(new Person("Veronika", new BigDecimal(52.4), new SimpleDateFormat(DATE_FORMAT).parse("27/01/1989")));

        mock.assertIsSatisfied();

    }
}
