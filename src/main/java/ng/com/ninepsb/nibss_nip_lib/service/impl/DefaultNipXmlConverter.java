package ng.com.ninepsb.nibss_nip_lib.service.impl;


import lombok.extern.slf4j.Slf4j;
import ng.com.ninepsb.nibss_nip_lib.exception.XmlConversionException;
import ng.com.ninepsb.nibss_nip_lib.service.NipXmlConverter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * {@inheritDoc}
 */
@Slf4j
@Component
public class DefaultNipXmlConverter implements NipXmlConverter {
    @Override
    public <T> String toXml(T object) throws XmlConversionException {
        if (object == null) {
            throw new XmlConversionException("Cannot convert null object to XML");
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();

            // Format the XML output
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Add XML declaration
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.FALSE);

            // Convert to string
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);

            return writer.toString();
        } catch (JAXBException e) {
            throw new XmlConversionException("Failed to convert object to XML", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T fromXml(String xml, Class<T> valueType) throws XmlConversionException {
        if (xml == null || xml.isEmpty()) {
            throw new XmlConversionException("Cannot convert empty or null XML string");
        }

        if (valueType == null) {
            throw new XmlConversionException("Target class type cannot be null");
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(valueType);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Parse XML from string
            StringReader reader = new StringReader(xml);
            Object unmarshalledObject = unmarshaller.unmarshal(reader);

            if (!valueType.isInstance(unmarshalledObject)) {
                throw new XmlConversionException(
                        "Unmarshalled object is not of expected type: " + valueType.getName());
            }

            return valueType.cast(unmarshalledObject);
        } catch (JAXBException e) {
            throw new XmlConversionException("Failed to convert XML to object", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> String toXml(T object, Schema schema) throws XmlConversionException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setSchema(schema);

            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new XmlConversionException("Failed to convert object to XML", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T fromXml(String xml, Class<T> clazz, Schema schema) throws XmlConversionException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);

            StringReader reader = new StringReader(xml);
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new XmlConversionException("Failed to convert XML to object", e);
        }
    }

    @Override
    public String getRootElementName(String xmlString) {
        if (xmlString == null || xmlString.trim().isEmpty()) {
            return null;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML string
            Document document = builder.parse(
                    new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8))
            );

            // Get the root element
            Element rootElement = document.getDocumentElement();

            // Return the local name (without namespace prefix)
            return rootElement.getLocalName();

        } catch (Exception e) {
            log.warn(":::Error parsing xml", e);
            return null;
        }
    }
}
