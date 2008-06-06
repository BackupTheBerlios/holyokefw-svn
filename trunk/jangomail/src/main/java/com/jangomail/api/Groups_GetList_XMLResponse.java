/**
 * Groups_GetList_XMLResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Groups_GetList_XMLResponse  implements java.io.Serializable {
    private com.jangomail.api.Groups_GetList_XMLResponseGroups_GetList_XMLResult groups_GetList_XMLResult;

    public Groups_GetList_XMLResponse() {
    }

    public Groups_GetList_XMLResponse(
           com.jangomail.api.Groups_GetList_XMLResponseGroups_GetList_XMLResult groups_GetList_XMLResult) {
           this.groups_GetList_XMLResult = groups_GetList_XMLResult;
    }


    /**
     * Gets the groups_GetList_XMLResult value for this Groups_GetList_XMLResponse.
     * 
     * @return groups_GetList_XMLResult
     */
    public com.jangomail.api.Groups_GetList_XMLResponseGroups_GetList_XMLResult getGroups_GetList_XMLResult() {
        return groups_GetList_XMLResult;
    }


    /**
     * Sets the groups_GetList_XMLResult value for this Groups_GetList_XMLResponse.
     * 
     * @param groups_GetList_XMLResult
     */
    public void setGroups_GetList_XMLResult(com.jangomail.api.Groups_GetList_XMLResponseGroups_GetList_XMLResult groups_GetList_XMLResult) {
        this.groups_GetList_XMLResult = groups_GetList_XMLResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Groups_GetList_XMLResponse)) return false;
        Groups_GetList_XMLResponse other = (Groups_GetList_XMLResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.groups_GetList_XMLResult==null && other.getGroups_GetList_XMLResult()==null) || 
             (this.groups_GetList_XMLResult!=null &&
              this.groups_GetList_XMLResult.equals(other.getGroups_GetList_XMLResult())));
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
        if (getGroups_GetList_XMLResult() != null) {
            _hashCode += getGroups_GetList_XMLResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Groups_GetList_XMLResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Groups_GetList_XMLResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groups_GetList_XMLResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Groups_GetList_XMLResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">>Groups_GetList_XMLResponse>Groups_GetList_XMLResult"));
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
