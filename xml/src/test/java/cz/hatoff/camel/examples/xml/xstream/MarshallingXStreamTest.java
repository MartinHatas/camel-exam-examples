package cz.hatoff.camel.examples.xml.xstream;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.BigDecimalConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import cz.hatoff.camel.examples.xml.Person;
import cz.hatoff.camel.examples.xml.PersonList;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import static java.util.Arrays.asList;
import static java.util.TimeZone.getTimeZone;

public class MarshallingXStreamTest extends CamelTestSupport {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {

            public void configure() {

                XStream xStream = new XStream();

                xStream.alias("person", Person.class);
                xStream.alias("persons", PersonList.class);
                xStream.addImplicitCollection(PersonList.class, "persons");

                DateConverter dateConverter = new DateConverter("dd/MM/yyyy", new String[] {}, getTimeZone("UTC"));
                xStream.registerConverter(dateConverter);

                BigDecimalConverter bigDecimalConverter = new BigDecimalConverter() {
                    @Override
                    public String toString(Object obj) {
                        BigDecimal bigDecimal = (BigDecimal) obj;
                        return bigDecimal.setScale(1, RoundingMode.HALF_UP).toString();
                    }
                };

                xStream.registerConverter(bigDecimalConverter);


                from("direct:xml.in")
                    .marshal(new XStreamDataFormat(xStream))
                    .to("mock:marshalled");
            }
        };
    }

    @Test
    public void testXmlXStreamMarshalling() throws Exception {

        String expectedXml =
                    "<?xml version='1.0' encoding='UTF-8'?>" +
                    "<persons>" +
                        "<person>" +
                            "<name>Martin</name>" +
                            "<price>48.6</price>"+
                            "<date>27/11/1987</date>" +
                        "</person>" +
                        "<person>" +
                            "<name>Veronika</name>" +
                            "<price>52.4</price>"+
                            "<date>27/01/1989</date>" +
                        "</person>" +
                    "</persons>";


        PersonList personList = new PersonList();
        personList.getPersons().add(new Person("Martin", new BigDecimal(48.6), new SimpleDateFormat(DATE_FORMAT).parse("27/11/1987")));
        personList.getPersons().add(new Person("Veronika", new BigDecimal(52.4), new SimpleDateFormat(DATE_FORMAT).parse("27/01/1989")));

        sendBody("direct:xml.in", personList);

        MockEndpoint mock = getMockEndpoint("mock:marshalled");

        mock.setExpectedCount(1);

        mock.message(0).body(String.class).isEqualTo(expectedXml);

        mock.assertIsSatisfied();

    }
}
