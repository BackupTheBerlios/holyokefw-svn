/**
 * Groups_GetMembers_StringResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Groups_GetMembers_StringResponse  implements java.io.Serializable {
    private java.lang.String groups_GetMembers_StringResult;

    public Groups_GetMembers_StringResponse() {
    }

    public Groups_GetMembers_StringResponse(
           java.lang.String groups_GetMembers_StringResult) {
           this.groups_GetMembers_StringResult = groups_GetMembers_StringResult;
    }


    /**
     * Gets the groups_GetMembers_StringResult value for this Groups_GetMembers_StringResponse.
     * 
     * @return groups_GetMembers_StringResult
     */
    public java.lang.String getGroups_GetMembers_StringResult() {
        return groups_GetMembers_StringResult;
    }


    /**
     * Sets the groups_GetMembers_StringResult value for this Groups_GetMembers_StringResponse.
     * 
     * @param groups_GetMembers_StringResult
     */
    public void setGroups_GetMembers_StringResult(java.lang.String groups_GetMembers_StringResult) {
        this.groups_GetMembers_StringResult = groups_GetMembers_StringResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Groups_GetMembers_StringResponse)) return false;
        Groups_GetMembers_StringResponse other = (Groups_GetMembers_StringResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.groups_GetMembers_StringResult==null && other.getGroups_GetMembers_StringResult()==null) || 
             (this.groups_GetMembers_StringResult!=null &&
              this.groups_GetMembers_StringResult.equals(other.getGroups_GetMembers_StringResult())));
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
        if (getGroups_GetMembers_StringResult() != null) {
            _hashCode += getGroups_GetMembers_StringResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Groups_GetMembers_StringResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Groups_GetMembers_StringResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groups_GetMembers_StringResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Groups_GetMembers_StringResult"));
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
