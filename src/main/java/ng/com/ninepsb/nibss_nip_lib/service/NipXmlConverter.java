package ng.com.ninepsb.nibss_nip_lib.service;


import ng.com.ninepsb.nibss_nip_lib.exception.XmlConversionException;

import javax.xml.validation.Schema;

public interface NipXmlConverter {
    <T> String toXml(T object) throws XmlConversionException;
    <T> T fromXml(String xml, Class<T> valueType) throws XmlConversionException;
    <T> String toXml(T object, Schema schema) throws XmlConversionException;
    <T> T fromXml(String xml, Class<T> clazz, Schema schema) throws XmlConversionException;
}
