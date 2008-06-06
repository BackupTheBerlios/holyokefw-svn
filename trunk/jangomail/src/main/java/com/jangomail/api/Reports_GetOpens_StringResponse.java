/**
 * Reports_GetOpens_StringResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetOpens_StringResponse  implements java.io.Serializable {
    private java.lang.String reports_GetOpens_StringResult;

    public Reports_GetOpens_StringResponse() {
    }

    public Reports_GetOpens_StringResponse(
           java.lang.String reports_GetOpens_StringResult) {
           this.reports_GetOpens_StringResult = reports_GetOpens_StringResult;
    }


    /**
     * Gets the reports_GetOpens_StringResult value for this Reports_GetOpens_StringResponse.
     * 
     * @return reports_GetOpens_StringResult
     */
    public java.lang.String getReports_GetOpens_StringResult() {
        return reports_GetOpens_StringResult;
    }


    /**
     * Sets the reports_GetOpens_StringResult value for this Reports_GetOpens_StringResponse.
     * 
     * @param reports_GetOpens_StringResult
     */
    public void setReports_GetOpens_StringResult(java.lang.String reports_GetOpens_StringResult) {
        this.reports_GetOpens_StringResult = reports_GetOpens_StringResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetOpens_StringResponse)) return false;
        Reports_GetOpens_StringResponse other = (Reports_GetOpens_StringResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetOpens_StringResult==null && other.getReports_GetOpens_StringResult()==null) || 
             (this.reports_GetOpens_StringResult!=null &&
              this.reports_GetOpens_StringResult.equals(other.getReports_GetOpens_StringResult())));
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
        if (getReports_GetOpens_StringResult() != null) {
            _hashCode += getReports_GetOpens_StringResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetOpens_StringResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetOpens_StringResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetOpens_StringResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetOpens_StringResult"));
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
