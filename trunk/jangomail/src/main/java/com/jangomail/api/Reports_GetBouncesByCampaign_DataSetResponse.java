/**
 * Reports_GetBouncesByCampaign_DataSetResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetBouncesByCampaign_DataSetResponse  implements java.io.Serializable {
    private com.jangomail.api.Reports_GetBouncesByCampaign_DataSetResponseReports_GetBouncesByCampaign_DataSetResult reports_GetBouncesByCampaign_DataSetResult;

    public Reports_GetBouncesByCampaign_DataSetResponse() {
    }

    public Reports_GetBouncesByCampaign_DataSetResponse(
           com.jangomail.api.Reports_GetBouncesByCampaign_DataSetResponseReports_GetBouncesByCampaign_DataSetResult reports_GetBouncesByCampaign_DataSetResult) {
           this.reports_GetBouncesByCampaign_DataSetResult = reports_GetBouncesByCampaign_DataSetResult;
    }


    /**
     * Gets the reports_GetBouncesByCampaign_DataSetResult value for this Reports_GetBouncesByCampaign_DataSetResponse.
     * 
     * @return reports_GetBouncesByCampaign_DataSetResult
     */
    public com.jangomail.api.Reports_GetBouncesByCampaign_DataSetResponseReports_GetBouncesByCampaign_DataSetResult getReports_GetBouncesByCampaign_DataSetResult() {
        return reports_GetBouncesByCampaign_DataSetResult;
    }


    /**
     * Sets the reports_GetBouncesByCampaign_DataSetResult value for this Reports_GetBouncesByCampaign_DataSetResponse.
     * 
     * @param reports_GetBouncesByCampaign_DataSetResult
     */
    public void setReports_GetBouncesByCampaign_DataSetResult(com.jangomail.api.Reports_GetBouncesByCampaign_DataSetResponseReports_GetBouncesByCampaign_DataSetResult reports_GetBouncesByCampaign_DataSetResult) {
        this.reports_GetBouncesByCampaign_DataSetResult = reports_GetBouncesByCampaign_DataSetResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetBouncesByCampaign_DataSetResponse)) return false;
        Reports_GetBouncesByCampaign_DataSetResponse other = (Reports_GetBouncesByCampaign_DataSetResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetBouncesByCampaign_DataSetResult==null && other.getReports_GetBouncesByCampaign_DataSetResult()==null) || 
             (this.reports_GetBouncesByCampaign_DataSetResult!=null &&
              this.reports_GetBouncesByCampaign_DataSetResult.equals(other.getReports_GetBouncesByCampaign_DataSetResult())));
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
        if (getReports_GetBouncesByCampaign_DataSetResult() != null) {
            _hashCode += getReports_GetBouncesByCampaign_DataSetResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetBouncesByCampaign_DataSetResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetBouncesByCampaign_DataSetResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetBouncesByCampaign_DataSetResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetBouncesByCampaign_DataSetResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Reports_GetBouncesByCampaign_DataSetResponse>Reports_GetBouncesByCampaign_DataSetResult"));
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
