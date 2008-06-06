/**
 * Reports_GetAllClicks_DataSetResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetAllClicks_DataSetResponse  implements java.io.Serializable {
    private com.jangomail.api.Reports_GetAllClicks_DataSetResponseReports_GetAllClicks_DataSetResult reports_GetAllClicks_DataSetResult;

    public Reports_GetAllClicks_DataSetResponse() {
    }

    public Reports_GetAllClicks_DataSetResponse(
           com.jangomail.api.Reports_GetAllClicks_DataSetResponseReports_GetAllClicks_DataSetResult reports_GetAllClicks_DataSetResult) {
           this.reports_GetAllClicks_DataSetResult = reports_GetAllClicks_DataSetResult;
    }


    /**
     * Gets the reports_GetAllClicks_DataSetResult value for this Reports_GetAllClicks_DataSetResponse.
     * 
     * @return reports_GetAllClicks_DataSetResult
     */
    public com.jangomail.api.Reports_GetAllClicks_DataSetResponseReports_GetAllClicks_DataSetResult getReports_GetAllClicks_DataSetResult() {
        return reports_GetAllClicks_DataSetResult;
    }


    /**
     * Sets the reports_GetAllClicks_DataSetResult value for this Reports_GetAllClicks_DataSetResponse.
     * 
     * @param reports_GetAllClicks_DataSetResult
     */
    public void setReports_GetAllClicks_DataSetResult(com.jangomail.api.Reports_GetAllClicks_DataSetResponseReports_GetAllClicks_DataSetResult reports_GetAllClicks_DataSetResult) {
        this.reports_GetAllClicks_DataSetResult = reports_GetAllClicks_DataSetResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetAllClicks_DataSetResponse)) return false;
        Reports_GetAllClicks_DataSetResponse other = (Reports_GetAllClicks_DataSetResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetAllClicks_DataSetResult==null && other.getReports_GetAllClicks_DataSetResult()==null) || 
             (this.reports_GetAllClicks_DataSetResult!=null &&
              this.reports_GetAllClicks_DataSetResult.equals(other.getReports_GetAllClicks_DataSetResult())));
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
        if (getReports_GetAllClicks_DataSetResult() != null) {
            _hashCode += getReports_GetAllClicks_DataSetResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetAllClicks_DataSetResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetAllClicks_DataSetResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetAllClicks_DataSetResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetAllClicks_DataSetResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Reports_GetAllClicks_DataSetResponse>Reports_GetAllClicks_DataSetResult"));
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
