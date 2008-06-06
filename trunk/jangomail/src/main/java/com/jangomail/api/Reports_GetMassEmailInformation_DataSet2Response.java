/**
 * Reports_GetMassEmailInformation_DataSet2Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetMassEmailInformation_DataSet2Response  implements java.io.Serializable {
    private com.jangomail.api.Reports_GetMassEmailInformation_DataSet2ResponseReports_GetMassEmailInformation_DataSet2Result reports_GetMassEmailInformation_DataSet2Result;

    public Reports_GetMassEmailInformation_DataSet2Response() {
    }

    public Reports_GetMassEmailInformation_DataSet2Response(
           com.jangomail.api.Reports_GetMassEmailInformation_DataSet2ResponseReports_GetMassEmailInformation_DataSet2Result reports_GetMassEmailInformation_DataSet2Result) {
           this.reports_GetMassEmailInformation_DataSet2Result = reports_GetMassEmailInformation_DataSet2Result;
    }


    /**
     * Gets the reports_GetMassEmailInformation_DataSet2Result value for this Reports_GetMassEmailInformation_DataSet2Response.
     * 
     * @return reports_GetMassEmailInformation_DataSet2Result
     */
    public com.jangomail.api.Reports_GetMassEmailInformation_DataSet2ResponseReports_GetMassEmailInformation_DataSet2Result getReports_GetMassEmailInformation_DataSet2Result() {
        return reports_GetMassEmailInformation_DataSet2Result;
    }


    /**
     * Sets the reports_GetMassEmailInformation_DataSet2Result value for this Reports_GetMassEmailInformation_DataSet2Response.
     * 
     * @param reports_GetMassEmailInformation_DataSet2Result
     */
    public void setReports_GetMassEmailInformation_DataSet2Result(com.jangomail.api.Reports_GetMassEmailInformation_DataSet2ResponseReports_GetMassEmailInformation_DataSet2Result reports_GetMassEmailInformation_DataSet2Result) {
        this.reports_GetMassEmailInformation_DataSet2Result = reports_GetMassEmailInformation_DataSet2Result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetMassEmailInformation_DataSet2Response)) return false;
        Reports_GetMassEmailInformation_DataSet2Response other = (Reports_GetMassEmailInformation_DataSet2Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.reports_GetMassEmailInformation_DataSet2Result==null && other.getReports_GetMassEmailInformation_DataSet2Result()==null) || 
             (this.reports_GetMassEmailInformation_DataSet2Result!=null &&
              this.reports_GetMassEmailInformation_DataSet2Result.equals(other.getReports_GetMassEmailInformation_DataSet2Result())));
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
        if (getReports_GetMassEmailInformation_DataSet2Result() != null) {
            _hashCode += getReports_GetMassEmailInformation_DataSet2Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetMassEmailInformation_DataSet2Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetMassEmailInformation_DataSet2Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reports_GetMassEmailInformation_DataSet2Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Reports_GetMassEmailInformation_DataSet2Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Reports_GetMassEmailInformation_DataSet2Response>Reports_GetMassEmailInformation_DataSet2Result"));
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
