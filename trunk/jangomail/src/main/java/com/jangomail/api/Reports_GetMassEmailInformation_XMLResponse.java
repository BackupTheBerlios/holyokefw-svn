/**
 * Reports_GetMassEmailInformation_XMLResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetMassEmailInformation_XMLResponse  implements java.io.Serializable {
    private com.jangomail.api.Reports_GetMassEmailInformation_XMLResponseReports_GetMassEmailInformation_XMLResult reports_GetMassEmailInformation_XMLResult;

    public Reports_GetMassEmailInformation_XMLResponse() {
    }

    public Reports_GetMassEmailInformation_XMLResponse(
           com.jangomail.api.Reports_GetMassEmailInformation_XMLResponseReports_GetMassEmailInformation_XMLResult reports_GetMassEmailInformation_XMLResult) {
           this.reports_GetMassEmailInformation_XMLResult = reports_GetMassEmailInformation_XMLResult;
    }


    /**
     * Gets the reports_GetMassEmailInformation_XMLResult value for this Reports_GetMassEmailInformation_XMLResponse.
     * 
     * @return reports_GetMassEmailInformation_XMLResult
     */
    public com.jangomail.api.Reports_GetMassEmailInformation_XMLResponseReports_GetMassEmailInformation_XMLResult getReports_GetMassEmailInformation_XMLResult() {
        return reports_GetMassEmailInformation_XMLResult;
    }


    /**
     * Sets the reports_GetMassEmailInformation_XMLResult value for this Reports_GetMassEmailInformation_XMLResponse.
     * 
     * @param reports_GetMassEmailInformation_XMLResult
     */
    public void setReports_GetMassEmailInformation_XMLResult(com.jangomail.api.Reports_GetMassEmailInformation_XMLResponseReports_GetMassEmailInformation_XMLResult reports_GetMassEmailInformation_XMLResult) {
        this.reports_GetMassEmailInformation_XMLResult = reports_GetMassEmailInformation_XMLResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetMassEmailInformation_XMLResponse)) return false;
        Reports_GetMassEmailInformation_XMLResponse other = (Reports_GetMassEmailInformation_XMLResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetMassEmailInformation_XMLResult==null && other.getReports_GetMassEmailInformation_XMLResult()==null) || 
             (this.reports_GetMassEmailInformation_XMLResult!=null &&
              this.reports_GetMassEmailInformation_XMLResult.equals(other.getReports_GetMassEmailInformation_XMLResult())));
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
        if (getReports_GetMassEmailInformation_XMLResult() != null) {
            _hashCode += getReports_GetMassEmailInformation_XMLResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetMassEmailInformation_XMLResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetMassEmailInformation_XMLResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetMassEmailInformation_XMLResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetMassEmailInformation_XMLResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Reports_GetMassEmailInformation_XMLResponse>Reports_GetMassEmailInformation_XMLResult"));
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
