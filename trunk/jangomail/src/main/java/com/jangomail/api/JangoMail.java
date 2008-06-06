/**
 * JangoMail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public interface JangoMail extends javax.xml.rpc.Service {

/**
 * The JangoMail Application Programming Interface (API) is a web
 * service that allows you to control your JangoMail account programatically
 * via an HTTP POST, an HTTP GET, or an XML-based SOAP call.  The supported
 * methods are shown below.  For a tutorial on using the API, along with
 * code samples, please go to http://www.jangomail.com/documents/Public/JangoMail_Tutorial_API.pdf.
 * For a Windows CHM Help file documenting all available methods and
 * their signatures, please go to http://www.jangomail.com/documents/Public/JangoMail_Web_Service_Help.chm.
 */
    public java.lang.String getJangoMailSoap12Address();

    public com.jangomail.api.JangoMailSoap getJangoMailSoap12() throws javax.xml.rpc.ServiceException;

    public com.jangomail.api.JangoMailSoap getJangoMailSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getJangoMailSoapAddress();

    public com.jangomail.api.JangoMailSoap getJangoMailSoap() throws javax.xml.rpc.ServiceException;

    public com.jangomail.api.JangoMailSoap getJangoMailSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
