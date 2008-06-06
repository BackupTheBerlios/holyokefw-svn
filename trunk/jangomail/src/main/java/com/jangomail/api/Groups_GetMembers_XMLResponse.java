/**
 * Groups_GetMembers_XMLResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Groups_GetMembers_XMLResponse  implements java.io.Serializable {
    private com.jangomail.api.Groups_GetMembers_XMLResponseGroups_GetMembers_XMLResult groups_GetMembers_XMLResult;

    public Groups_GetMembers_XMLResponse() {
    }

    public Groups_GetMembers_XMLResponse(
           com.jangomail.api.Groups_GetMembers_XMLResponseGroups_GetMembers_XMLResult groups_GetMembers_XMLResult) {
           this.groups_GetMembers_XMLResult = groups_GetMembers_XMLResult;
    }


    /**
     * Gets the groups_GetMembers_XMLResult value for this Groups_GetMembers_XMLResponse.
     * 
     * @return groups_GetMembers_XMLResult
     */
    public com.jangomail.api.Groups_GetMembers_XMLResponseGroups_GetMembers_XMLResult getGroups_GetMembers_XMLResult() {
        return groups_GetMembers_XMLResult;
    }


    /**
     * Sets the groups_GetMembers_XMLResult value for this Groups_GetMembers_XMLResponse.
     * 
     * @param groups_GetMembers_XMLResult
     */
    public void setGroups_GetMembers_XMLResult(com.jangomail.api.Groups_GetMembers_XMLResponseGroups_GetMembers_XMLResult groups_GetMembers_XMLResult) {
        this.groups_GetMembers_XMLResult = groups_GetMembers_XMLResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Groups_GetMembers_XMLResponse)) return false;
        Groups_GetMembers_XMLResponse other = (Groups_GetMembers_XMLResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.groups_GetMembers_XMLResult==null && other.getGroups_GetMembers_XMLResult()==null) || 
             (this.groups_GetMembers_XMLResult!=null &&
              this.groups_GetMembers_XMLResult.equals(other.getGroups_GetMembers_XMLResult())));
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
        if (getGroups_GetMembers_XMLResult() != null) {
            _hashCode += getGroups_GetMembers_XMLResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Groups_GetMembers_XMLResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Groups_GetMembers_XMLResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groups_GetMembers_XMLResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Groups_GetMembers_XMLResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Groups_GetMembers_XMLResponse>Groups_GetMembers_XMLResult"));
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
