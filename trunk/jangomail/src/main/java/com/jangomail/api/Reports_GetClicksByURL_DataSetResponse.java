/**
 * Reports_GetClicksByURL_DataSetResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetClicksByURL_DataSetResponse  implements java.io.Serializable {
    private com.jangomail.api.Reports_GetClicksByURL_DataSetResponseReports_GetClicksByURL_DataSetResult reports_GetClicksByURL_DataSetResult;

    public Reports_GetClicksByURL_DataSetResponse() {
    }

    public Reports_GetClicksByURL_DataSetResponse(
           com.jangomail.api.Reports_GetClicksByURL_DataSetResponseReports_GetClicksByURL_DataSetResult reports_GetClicksByURL_DataSetResult) {
           this.reports_GetClicksByURL_DataSetResult = reports_GetClicksByURL_DataSetResult;
    }


    /**
     * Gets the reports_GetClicksByURL_DataSetResult value for this Reports_GetClicksByURL_DataSetResponse.
     * 
     * @return reports_GetClicksByURL_DataSetResult
     */
    public com.jangomail.api.Reports_GetClicksByURL_DataSetResponseReports_GetClicksByURL_DataSetResult getReports_GetClicksByURL_DataSetResult() {
        return reports_GetClicksByURL_DataSetResult;
    }


    /**
     * Sets the reports_GetClicksByURL_DataSetResult value for this Reports_GetClicksByURL_DataSetResponse.
     * 
     * @param reports_GetClicksByURL_DataSetResult
     */
    public void setReports_GetClicksByURL_DataSetResult(com.jangomail.api.Reports_GetClicksByURL_DataSetResponseReports_GetClicksByURL_DataSetResult reports_GetClicksByURL_DataSetResult) {
        this.reports_GetClicksByURL_DataSetResult = reports_GetClicksByURL_DataSetResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetClicksByURL_DataSetResponse)) return false;
        Reports_GetClicksByURL_DataSetResponse other = (Reports_GetClicksByURL_DataSetResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetClicksByURL_DataSetResult==null && other.getReports_GetClicksByURL_DataSetResult()==null) || 
             (this.reports_GetClicksByURL_DataSetResult!=null &&
              this.reports_GetClicksByURL_DataSetResult.equals(other.getReports_GetClicksByURL_DataSetResult())));
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
        if (getReports_GetClicksByURL_DataSetResult() != null) {
            _hashCode += getReports_GetClicksByURL_DataSetResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetClicksByURL_DataSetResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetClicksByURL_DataSetResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetClicksByURL_DataSetResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetClicksByURL_DataSetResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Reports_GetClicksByURL_DataSetResponse>Reports_GetClicksByURL_DataSetResult"));
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
