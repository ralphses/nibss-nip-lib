package ng.com.ninepsb.nibss_nip_lib.service.impl;

import ng.com.ninepsb.nibss_nip_lib.service.SchemaValidator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;

@Component
public class DefaultSchemaValidator implements SchemaValidator {

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema loadSchema(String schemaPath) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        InputStream schemaStream = new ClassPathResource(schemaPath).getInputStream();
        return factory.newSchema(new StreamSource(schemaStream));
    }
}
