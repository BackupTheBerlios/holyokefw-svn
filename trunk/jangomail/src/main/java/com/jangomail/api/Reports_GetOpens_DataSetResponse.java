/**
 * Reports_GetOpens_DataSetResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetOpens_DataSetResponse  implements java.io.Serializable {
    private com.jangomail.api.Reports_GetOpens_DataSetResponseReports_GetOpens_DataSetResult reports_GetOpens_DataSetResult;

    public Reports_GetOpens_DataSetResponse() {
    }

    public Reports_GetOpens_DataSetResponse(
           com.jangomail.api.Reports_GetOpens_DataSetResponseReports_GetOpens_DataSetResult reports_GetOpens_DataSetResult) {
           this.reports_GetOpens_DataSetResult = reports_GetOpens_DataSetResult;
    }


    /**
     * Gets the reports_GetOpens_DataSetResult value for this Reports_GetOpens_DataSetResponse.
     * 
     * @return reports_GetOpens_DataSetResult
     */
    public com.jangomail.api.Reports_GetOpens_DataSetResponseReports_GetOpens_DataSetResult getReports_GetOpens_DataSetResult() {
        return reports_GetOpens_DataSetResult;
    }


    /**
     * Sets the reports_GetOpens_DataSetResult value for this Reports_GetOpens_DataSetResponse.
     * 
     * @param reports_GetOpens_DataSetResult
     */
    public void setReports_GetOpens_DataSetResult(com.jangomail.api.Reports_GetOpens_DataSetResponseReports_GetOpens_DataSetResult reports_GetOpens_DataSetResult) {
        this.reports_GetOpens_DataSetResult = reports_GetOpens_DataSetResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetOpens_DataSetResponse)) return false;
        Reports_GetOpens_DataSetResponse other = (Reports_GetOpens_DataSetResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetOpens_DataSetResult==null && other.getReports_GetOpens_DataSetResult()==null) || 
             (this.reports_GetOpens_DataSetResult!=null &&
              this.reports_GetOpens_DataSetResult.equals(other.getReports_GetOpens_DataSetResult())));
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
        if (getReports_GetOpens_DataSetResult() != null) {
            _hashCode += getReports_GetOpens_DataSetResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetOpens_DataSetResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetOpens_DataSetResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetOpens_DataSetResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetOpens_DataSetResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Reports_GetOpens_DataSetResponse>Reports_GetOpens_DataSetResult"));
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
