package cz.hatoff.camel.examples.csv.bindy;


import cz.hatoff.camel.examples.csv.bindy.pojo.Person;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.test.junit4.CamelTestSupport;

public class MarshallingCsvBindyTest extends CamelTestSupport {

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
}
