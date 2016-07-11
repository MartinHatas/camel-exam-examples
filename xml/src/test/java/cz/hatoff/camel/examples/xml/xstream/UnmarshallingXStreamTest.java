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
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

import static java.util.TimeZone.getTimeZone;

public class UnmarshallingXStreamTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

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
                        .unmarshal(new XStreamDataFormat(xStream))
                        .to("mock:unmarshalled");
            }
        };
    }

    @Test
    public void testXmlXStreamUnmarshalling() throws Exception {

        String inputXml =
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

        sendBody("direct:xml.in", inputXml);

        MockEndpoint mock = getMockEndpoint("mock:unmarshalled");

        mock.setExpectedCount(1);

        mock.assertIsSatisfied();

    }
}
