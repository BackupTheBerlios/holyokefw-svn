/**
 * JangoMailLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class JangoMailLocator extends org.apache.axis.client.Service implements com.jangomail.api.JangoMail {

/**
 * The JangoMail Application Programming Interface (API) is a web
 * service that allows you to control your JangoMail account programatically
 * via an HTTP POST, an HTTP GET, or an XML-based SOAP call.  The supported
 * methods are shown below.  For a tutorial on using the API, along with
 * code samples, please go to http://www.jangomail.com/documents/Public/JangoMail_Tutorial_API.pdf.
 * For a Windows CHM Help file documenting all available methods and
 * their signatures, please go to http://www.jangomail.com/documents/Public/JangoMail_Web_Service_Help.chm.
 */

    public JangoMailLocator() {
    }


    public JangoMailLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public JangoMailLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for JangoMailSoap12
    private java.lang.String JangoMailSoap12_address = "https://api.jangomail.com/api.asmx";

    public java.lang.String getJangoMailSoap12Address() {
        return JangoMailSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String JangoMailSoap12WSDDServiceName = "JangoMailSoap12";

    public java.lang.String getJangoMailSoap12WSDDServiceName() {
        return JangoMailSoap12WSDDServiceName;
    }

    public void setJangoMailSoap12WSDDServiceName(java.lang.String name) {
        JangoMailSoap12WSDDServiceName = name;
    }

    public com.jangomail.api.JangoMailSoap getJangoMailSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(JangoMailSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getJangoMailSoap12(endpoint);
    }

    public com.jangomail.api.JangoMailSoap getJangoMailSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.jangomail.api.JangoMailSoap12Stub _stub = new com.jangomail.api.JangoMailSoap12Stub(portAddress, this);
            _stub.setPortName(getJangoMailSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setJangoMailSoap12EndpointAddress(java.lang.String address) {
        JangoMailSoap12_address = address;
    }


    // Use to get a proxy class for JangoMailSoap
    private java.lang.String JangoMailSoap_address = "https://api.jangomail.com/api.asmx";

    public java.lang.String getJangoMailSoapAddress() {
        return JangoMailSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String JangoMailSoapWSDDServiceName = "JangoMailSoap";

    public java.lang.String getJangoMailSoapWSDDServiceName() {
        return JangoMailSoapWSDDServiceName;
    }

    public void setJangoMailSoapWSDDServiceName(java.lang.String name) {
        JangoMailSoapWSDDServiceName = name;
    }

    public com.jangomail.api.JangoMailSoap getJangoMailSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(JangoMailSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getJangoMailSoap(endpoint);
    }

    public com.jangomail.api.JangoMailSoap getJangoMailSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.jangomail.api.JangoMailSoapStub _stub = new com.jangomail.api.JangoMailSoapStub(portAddress, this);
            _stub.setPortName(getJangoMailSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setJangoMailSoapEndpointAddress(java.lang.String address) {
        JangoMailSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.jangomail.api.JangoMailSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.jangomail.api.JangoMailSoap12Stub _stub = new com.jangomail.api.JangoMailSoap12Stub(new java.net.URL(JangoMailSoap12_address), this);
                _stub.setPortName(getJangoMailSoap12WSDDServiceName());
                return _stub;
            }
            if (com.jangomail.api.JangoMailSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.jangomail.api.JangoMailSoapStub _stub = new com.jangomail.api.JangoMailSoapStub(new java.net.URL(JangoMailSoap_address), this);
                _stub.setPortName(getJangoMailSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("JangoMailSoap12".equals(inputPortName)) {
            return getJangoMailSoap12();
        }
        else if ("JangoMailSoap".equals(inputPortName)) {
            return getJangoMailSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://api.jangomail.com/", "JangoMail");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://api.jangomail.com/", "JangoMailSoap12"));
            ports.add(new javax.xml.namespace.QName("http://api.jangomail.com/", "JangoMailSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("JangoMailSoap12".equals(portName)) {
            setJangoMailSoap12EndpointAddress(address);
        }
        else 
if ("JangoMailSoap".equals(portName)) {
            setJangoMailSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
