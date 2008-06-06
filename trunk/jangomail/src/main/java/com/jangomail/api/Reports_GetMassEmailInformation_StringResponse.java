/**
 * Reports_GetMassEmailInformation_StringResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetMassEmailInformation_StringResponse  implements java.io.Serializable {
    private java.lang.String reports_GetMassEmailInformation_StringResult;

    public Reports_GetMassEmailInformation_StringResponse() {
    }

    public Reports_GetMassEmailInformation_StringResponse(
           java.lang.String reports_GetMassEmailInformation_StringResult) {
           this.reports_GetMassEmailInformation_StringResult = reports_GetMassEmailInformation_StringResult;
    }


    /**
     * Gets the reports_GetMassEmailInformation_StringResult value for this Reports_GetMassEmailInformation_StringResponse.
     * 
     * @return reports_GetMassEmailInformation_StringResult
     */
    public java.lang.String getReports_GetMassEmailInformation_StringResult() {
        return reports_GetMassEmailInformation_StringResult;
    }


    /**
     * Sets the reports_GetMassEmailInformation_StringResult value for this Reports_GetMassEmailInformation_StringResponse.
     * 
     * @param reports_GetMassEmailInformation_StringResult
     */
    public void setReports_GetMassEmailInformation_StringResult(java.lang.String reports_GetMassEmailInformation_StringResult) {
        this.reports_GetMassEmailInformation_StringResult = reports_GetMassEmailInformation_StringResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetMassEmailInformation_StringResponse)) return false;
        Reports_GetMassEmailInformation_StringResponse other = (Reports_GetMassEmailInformation_StringResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetMassEmailInformation_StringResult==null && other.getReports_GetMassEmailInformation_StringResult()==null) || 
             (this.reports_GetMassEmailInformation_StringResult!=null &&
              this.reports_GetMassEmailInformation_StringResult.equals(other.getReports_GetMassEmailInformation_StringResult())));
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
        if (getReports_GetMassEmailInformation_StringResult() != null) {
            _hashCode += getReports_GetMassEmailInformation_StringResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetMassEmailInformation_StringResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetMassEmailInformation_StringResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetMassEmailInformation_StringResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetMassEmailInformation_StringResult"));
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
