/**
 * Reports_GetReplies_XMLResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetReplies_XMLResponse  implements java.io.Serializable {
    private com.jangomail.api.Reports_GetReplies_XMLResponseReports_GetReplies_XMLResult reports_GetReplies_XMLResult;

    public Reports_GetReplies_XMLResponse() {
    }

    public Reports_GetReplies_XMLResponse(
           com.jangomail.api.Reports_GetReplies_XMLResponseReports_GetReplies_XMLResult reports_GetReplies_XMLResult) {
           this.reports_GetReplies_XMLResult = reports_GetReplies_XMLResult;
    }


    /**
     * Gets the reports_GetReplies_XMLResult value for this Reports_GetReplies_XMLResponse.
     * 
     * @return reports_GetReplies_XMLResult
     */
    public com.jangomail.api.Reports_GetReplies_XMLResponseReports_GetReplies_XMLResult getReports_GetReplies_XMLResult() {
        return reports_GetReplies_XMLResult;
    }


    /**
     * Sets the reports_GetReplies_XMLResult value for this Reports_GetReplies_XMLResponse.
     * 
     * @param reports_GetReplies_XMLResult
     */
    public void setReports_GetReplies_XMLResult(com.jangomail.api.Reports_GetReplies_XMLResponseReports_GetReplies_XMLResult reports_GetReplies_XMLResult) {
        this.reports_GetReplies_XMLResult = reports_GetReplies_XMLResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetReplies_XMLResponse)) return false;
        Reports_GetReplies_XMLResponse other = (Reports_GetReplies_XMLResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetReplies_XMLResult==null && other.getReports_GetReplies_XMLResult()==null) || 
             (this.reports_GetReplies_XMLResult!=null &&
              this.reports_GetReplies_XMLResult.equals(other.getReports_GetReplies_XMLResult())));
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
        if (getReports_GetReplies_XMLResult() != null) {
            _hashCode += getReports_GetReplies_XMLResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetReplies_XMLResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetReplies_XMLResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetReplies_XMLResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetReplies_XMLResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Reports_GetReplies_XMLResponse>Reports_GetReplies_XMLResult"));
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
