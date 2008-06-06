/**
 * Reports_GetUnsubscribesAll_StringResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetUnsubscribesAll_StringResponse  implements java.io.Serializable {
    private java.lang.String reports_GetUnsubscribesAll_StringResult;

    public Reports_GetUnsubscribesAll_StringResponse() {
    }

    public Reports_GetUnsubscribesAll_StringResponse(
           java.lang.String reports_GetUnsubscribesAll_StringResult) {
           this.reports_GetUnsubscribesAll_StringResult = reports_GetUnsubscribesAll_StringResult;
    }


    /**
     * Gets the reports_GetUnsubscribesAll_StringResult value for this Reports_GetUnsubscribesAll_StringResponse.
     * 
     * @return reports_GetUnsubscribesAll_StringResult
     */
    public java.lang.String getReports_GetUnsubscribesAll_StringResult() {
        return reports_GetUnsubscribesAll_StringResult;
    }


    /**
     * Sets the reports_GetUnsubscribesAll_StringResult value for this Reports_GetUnsubscribesAll_StringResponse.
     * 
     * @param reports_GetUnsubscribesAll_StringResult
     */
    public void setReports_GetUnsubscribesAll_StringResult(java.lang.String reports_GetUnsubscribesAll_StringResult) {
        this.reports_GetUnsubscribesAll_StringResult = reports_GetUnsubscribesAll_StringResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetUnsubscribesAll_StringResponse)) return false;
        Reports_GetUnsubscribesAll_StringResponse other = (Reports_GetUnsubscribesAll_StringResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetUnsubscribesAll_StringResult==null && other.getReports_GetUnsubscribesAll_StringResult()==null) || 
             (this.reports_GetUnsubscribesAll_StringResult!=null &&
              this.reports_GetUnsubscribesAll_StringResult.equals(other.getReports_GetUnsubscribesAll_StringResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getReports_GetUnsubscribesAll_StringResult() != null) {
            _hashCode += getReports_GetUnsubscribesAll_StringResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetUnsubscribesAll_StringResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetUnsubscribesAll_StringResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetUnsubscribesAll_StringResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetUnsubscribesAll_StringResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
