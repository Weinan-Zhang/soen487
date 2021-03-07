package com.soen487.rest.project.repository.core.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class CommonUtils {
    public static XMLGregorianCalendar formatXMLGregorianCalendar(String dateTime) throws DatatypeConfigurationException {
        return  DatatypeFactory.newInstance().newXMLGregorianCalendar(dateTime);
    }
}
