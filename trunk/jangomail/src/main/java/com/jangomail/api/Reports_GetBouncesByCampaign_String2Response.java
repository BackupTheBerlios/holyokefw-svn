/**
 * Reports_GetBouncesByCampaign_String2Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetBouncesByCampaign_String2Response  implements java.io.Serializable {
    private java.lang.String reports_GetBouncesByCampaign_String2Result;

    public Reports_GetBouncesByCampaign_String2Response() {
    }

    public Reports_GetBouncesByCampaign_String2Response(
           java.lang.String reports_GetBouncesByCampaign_String2Result) {
           this.reports_GetBouncesByCampaign_String2Result = reports_GetBouncesByCampaign_String2Result;
    }


    /**
     * Gets the reports_GetBouncesByCampaign_String2Result value for this Reports_GetBouncesByCampaign_String2Response.
     * 
     * @return reports_GetBouncesByCampaign_String2Result
     */
    public java.lang.String getReports_GetBouncesByCampaign_String2Result() {
        return reports_GetBouncesByCampaign_String2Result;
    }


    /**
     * Sets the reports_GetBouncesByCampaign_String2Result value for this Reports_GetBouncesByCampaign_String2Response.
     * 
     * @param reports_GetBouncesByCampaign_String2Result
     */
    public void setReports_GetBouncesByCampaign_String2Result(java.lang.String reports_GetBouncesByCampaign_String2Result) {
        this.reports_GetBouncesByCampaign_String2Result = reports_GetBouncesByCampaign_String2Result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetBouncesByCampaign_String2Response)) return false;
        Reports_GetBouncesByCampaign_String2Response other = (Reports_GetBouncesByCampaign_String2Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetBouncesByCampaign_String2Result==null && other.getReports_GetBouncesByCampaign_String2Result()==null) || 
             (this.reports_GetBouncesByCampaign_String2Result!=null &&
              this.reports_GetBouncesByCampaign_String2Result.equals(other.getReports_GetBouncesByCampaign_String2Result())));
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
        if (getReports_GetBouncesByCampaign_String2Result() != null) {
            _hashCode += getReports_GetBouncesByCampaign_String2Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetBouncesByCampaign_String2Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetBouncesByCampaign_String2Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetBouncesByCampaign_String2Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetBouncesByCampaign_String2Result"));
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
