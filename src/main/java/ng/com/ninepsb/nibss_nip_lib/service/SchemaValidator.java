package ng.com.ninepsb.nibss_nip_lib.service;

import org.xml.sax.SAXException;

import javax.xml.validation.Schema;
import java.io.IOException;

@FunctionalInterface
public interface SchemaValidator {

    /**
     * Loads an XSD schema from classpath
     *
     * @param schemaPath Path to XSD schema file
     * @return Schema object
     * @throws SAXException if schema is invalid
     * @throws IOException if schema file cannot be read
     */
    Schema loadSchema(String schemaPath) throws SAXException, IOException;
}
