/**
 * Reports_GetSentMessageCount.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.jangomail.api;

public class Reports_GetSentMessageCount  implements java.io.Serializable {
    private java.lang.String username;

    private java.lang.String password;

    private java.lang.String beginningDate;

    private java.lang.String endingDate;

    public Reports_GetSentMessageCount() {
    }

    public Reports_GetSentMessageCount(
           java.lang.String username,
           java.lang.String password,
           java.lang.String beginningDate,
           java.lang.String endingDate) {
           this.username = username;
           this.password = password;
           this.beginningDate = beginningDate;
           this.endingDate = endingDate;
    }


    /**
     * Gets the username value for this Reports_GetSentMessageCount.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this Reports_GetSentMessageCount.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the password value for this Reports_GetSentMessageCount.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Reports_GetSentMessageCount.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the beginningDate value for this Reports_GetSentMessageCount.
     * 
     * @return beginningDate
     */
    public java.lang.String getBeginningDate() {
        return beginningDate;
    }


    /**
     * Sets the beginningDate value for this Reports_GetSentMessageCount.
     * 
     * @param beginningDate
     */
    public void setBeginningDate(java.lang.String beginningDate) {
        this.beginningDate = beginningDate;
    }


    /**
     * Gets the endingDate value for this Reports_GetSentMessageCount.
     * 
     * @return endingDate
     */
    public java.lang.String getEndingDate() {
        return endingDate;
    }


    /**
     * Sets the endingDate value for this Reports_GetSentMessageCount.
     * 
     * @param endingDate
     */
    public void setEndingDate(java.lang.String endingDate) {
        this.endingDate = endingDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Reports_GetSentMessageCount)) return false;
        Reports_GetSentMessageCount other = (Reports_GetSentMessageCount) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.beginningDate==null && other.getBeginningDate()==null) || 
             (this.beginningDate!=null &&
              this.beginningDate.equals(other.getBeginningDate()))) &&
            ((this.endingDate==null && other.getEndingDate()==null) || 
             (this.endingDate!=null &&
              this.endingDate.equals(other.getEndingDate())));
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
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getBeginningDate() != null) {
            _hashCode += getBeginningDate().hashCode();
        }
        if (getEndingDate() != null) {
            _hashCode += getEndingDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Reports_GetSentMessageCount.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://api.jangomail.com/", ">Reports_GetSentMessageCount"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginningDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "BeginningDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endingDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://api.jangomail.com/", "EndingDate"));
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
