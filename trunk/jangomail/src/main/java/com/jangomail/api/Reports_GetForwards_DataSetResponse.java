/**
 * Reports_GetForwards_DataSetResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetForwards_DataSetResponse  implements java.io.Serializable {
    private com.jangomail.api.Reports_GetForwards_DataSetResponseReports_GetForwards_DataSetResult reports_GetForwards_DataSetResult;

    public Reports_GetForwards_DataSetResponse() {
    }

    public Reports_GetForwards_DataSetResponse(
           com.jangomail.api.Reports_GetForwards_DataSetResponseReports_GetForwards_DataSetResult reports_GetForwards_DataSetResult) {
           this.reports_GetForwards_DataSetResult = reports_GetForwards_DataSetResult;
    }


    /**
     * Gets the reports_GetForwards_DataSetResult value for this Reports_GetForwards_DataSetResponse.
     * 
     * @return reports_GetForwards_DataSetResult
     */
    public com.jangomail.api.Reports_GetForwards_DataSetResponseReports_GetForwards_DataSetResult getReports_GetForwards_DataSetResult() {
        return reports_GetForwards_DataSetResult;
    }


    /**
     * Sets the reports_GetForwards_DataSetResult value for this Reports_GetForwards_DataSetResponse.
     * 
     * @param reports_GetForwards_DataSetResult
     */
    public void setReports_GetForwards_DataSetResult(com.jangomail.api.Reports_GetForwards_DataSetResponseReports_GetForwards_DataSetResult reports_GetForwards_DataSetResult) {
        this.reports_GetForwards_DataSetResult = reports_GetForwards_DataSetResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetForwards_DataSetResponse)) return false;
        Reports_GetForwards_DataSetResponse other = (Reports_GetForwards_DataSetResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetForwards_DataSetResult==null && other.getReports_GetForwards_DataSetResult()==null) || 
             (this.reports_GetForwards_DataSetResult!=null &&
              this.reports_GetForwards_DataSetResult.equals(other.getReports_GetForwards_DataSetResult())));
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
        if (getReports_GetForwards_DataSetResult() != null) {
            _hashCode += getReports_GetForwards_DataSetResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetForwards_DataSetResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetForwards_DataSetResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetForwards_DataSetResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetForwards_DataSetResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Reports_GetForwards_DataSetResponse>Reports_GetForwards_DataSetResult"));
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
