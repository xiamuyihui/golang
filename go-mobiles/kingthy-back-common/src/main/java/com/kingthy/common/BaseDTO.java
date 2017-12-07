// package com.kingthy.common;
//
// import java.io.Serializable;
//
// import com.fasterxml.jackson.annotation.JsonIgnore;
//
// public class BaseDTO implements Serializable
// {
// /**
// * serialVersionUID:TODO（用一句话描述这个变量表示什么）
// *
// * @since 1.0.0
// */
//
// private static final long serialVersionUID = 1L;
//
// @javax.persistence.Transient
// @JsonIgnore
// private String clientId;
//
// @javax.persistence.Transient
// private String token;
//
// public String getClientId()
// {
// return clientId;
// }
//
// public void setClientId(String clientId)
// {
// this.clientId = clientId;
// }
//
// public String getToken()
// {
// return token;
// }
//
// public void setToken(String token)
// {
// this.token = token;
// }
//
// @Override
// public String toString()
// {
// return "BaseDTO [clientId=" + clientId + ", token=" + token + "]";
// }
//
// }
