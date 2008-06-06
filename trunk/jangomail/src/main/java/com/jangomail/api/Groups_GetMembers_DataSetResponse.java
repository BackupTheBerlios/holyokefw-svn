/**
 * Groups_GetMembers_DataSetResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Groups_GetMembers_DataSetResponse  implements java.io.Serializable {
    private com.jangomail.api.Groups_GetMembers_DataSetResponseGroups_GetMembers_DataSetResult groups_GetMembers_DataSetResult;

    public Groups_GetMembers_DataSetResponse() {
    }

    public Groups_GetMembers_DataSetResponse(
           com.jangomail.api.Groups_GetMembers_DataSetResponseGroups_GetMembers_DataSetResult groups_GetMembers_DataSetResult) {
           this.groups_GetMembers_DataSetResult = groups_GetMembers_DataSetResult;
    }


    /**
     * Gets the groups_GetMembers_DataSetResult value for this Groups_GetMembers_DataSetResponse.
     * 
     * @return groups_GetMembers_DataSetResult
     */
    public com.jangomail.api.Groups_GetMembers_DataSetResponseGroups_GetMembers_DataSetResult getGroups_GetMembers_DataSetResult() {
        return groups_GetMembers_DataSetResult;
    }


    /**
     * Sets the groups_GetMembers_DataSetResult value for this Groups_GetMembers_DataSetResponse.
     * 
     * @param groups_GetMembers_DataSetResult
     */
    public void setGroups_GetMembers_DataSetResult(com.jangomail.api.Groups_GetMembers_DataSetResponseGroups_GetMembers_DataSetResult groups_GetMembers_DataSetResult) {
        this.groups_GetMembers_DataSetResult = groups_GetMembers_DataSetResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Groups_GetMembers_DataSetResponse)) return false;
        Groups_GetMembers_DataSetResponse other = (Groups_GetMembers_DataSetResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.groups_GetMembers_DataSetResult==null && other.getGroups_GetMembers_DataSetResult()==null) || 
             (this.groups_GetMembers_DataSetResult!=null &&
              this.groups_GetMembers_DataSetResult.equals(other.getGroups_GetMembers_DataSetResult())));
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
        if (getGroups_GetMembers_DataSetResult() != null) {
            _hashCode += getGroups_GetMembers_DataSetResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Groups_GetMembers_DataSetResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Groups_GetMembers_DataSetResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groups_GetMembers_DataSetResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Groups_GetMembers_DataSetResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Groups_GetMembers_DataSetResponse>Groups_GetMembers_DataSetResult"));
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
