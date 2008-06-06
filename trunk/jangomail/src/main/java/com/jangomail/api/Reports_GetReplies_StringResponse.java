/**
 * Reports_GetReplies_StringResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetReplies_StringResponse  implements java.io.Serializable {
    private java.lang.String reports_GetReplies_StringResult;

    public Reports_GetReplies_StringResponse() {
    }

    public Reports_GetReplies_StringResponse(
           java.lang.String reports_GetReplies_StringResult) {
           this.reports_GetReplies_StringResult = reports_GetReplies_StringResult;
    }


    /**
     * Gets the reports_GetReplies_StringResult value for this Reports_GetReplies_StringResponse.
     * 
     * @return reports_GetReplies_StringResult
     */
    public java.lang.String getReports_GetReplies_StringResult() {
        return reports_GetReplies_StringResult;
    }


    /**
     * Sets the reports_GetReplies_StringResult value for this Reports_GetReplies_StringResponse.
     * 
     * @param reports_GetReplies_StringResult
     */
    public void setReports_GetReplies_StringResult(java.lang.String reports_GetReplies_StringResult) {
        this.reports_GetReplies_StringResult = reports_GetReplies_StringResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetReplies_StringResponse)) return false;
        Reports_GetReplies_StringResponse other = (Reports_GetReplies_StringResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetReplies_StringResult==null && other.getReports_GetReplies_StringResult()==null) || 
             (this.reports_GetReplies_StringResult!=null &&
              this.reports_GetReplies_StringResult.equals(other.getReports_GetReplies_StringResult())));
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
        if (getReports_GetReplies_StringResult() != null) {
            _hashCode += getReports_GetReplies_StringResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetReplies_StringResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetReplies_StringResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetReplies_StringResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetReplies_StringResult"));
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
