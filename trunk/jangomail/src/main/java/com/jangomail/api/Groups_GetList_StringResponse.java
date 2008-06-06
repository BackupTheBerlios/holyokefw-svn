/**
 * Groups_GetList_StringResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Groups_GetList_StringResponse  implements java.io.Serializable {
    private java.lang.String groups_GetList_StringResult;

    public Groups_GetList_StringResponse() {
    }

    public Groups_GetList_StringResponse(
           java.lang.String groups_GetList_StringResult) {
           this.groups_GetList_StringResult = groups_GetList_StringResult;
    }


    /**
     * Gets the groups_GetList_StringResult value for this Groups_GetList_StringResponse.
     * 
     * @return groups_GetList_StringResult
     */
    public java.lang.String getGroups_GetList_StringResult() {
        return groups_GetList_StringResult;
    }


    /**
     * Sets the groups_GetList_StringResult value for this Groups_GetList_StringResponse.
     * 
     * @param groups_GetList_StringResult
     */
    public void setGroups_GetList_StringResult(java.lang.String groups_GetList_StringResult) {
        this.groups_GetList_StringResult = groups_GetList_StringResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Groups_GetList_StringResponse)) return false;
        Groups_GetList_StringResponse other = (Groups_GetList_StringResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.groups_GetList_StringResult==null && other.getGroups_GetList_StringResult()==null) || 
             (this.groups_GetList_StringResult!=null &&
              this.groups_GetList_StringResult.equals(other.getGroups_GetList_StringResult())));
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
        if (getGroups_GetList_StringResult() != null) {
            _hashCode += getGroups_GetList_StringResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Groups_GetList_StringResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Groups_GetList_StringResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groups_GetList_StringResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Groups_GetList_StringResult"));
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
