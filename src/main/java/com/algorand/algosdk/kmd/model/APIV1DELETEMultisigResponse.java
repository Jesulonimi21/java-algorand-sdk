/*
 * for KMD HTTP API
 * API for KMD (Key Management Daemon)
 *
 * OpenAPI spec version: 0.0.1
 * Contact: contact@algorand.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.algorand.algosdk.kmd.model;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.ObjectUtils;

/**
 * APIV1DELETEMultisigResponse is the response to POST /v1/multisig/delete&#x60; friendly:DeleteMultisigResponse
 */
public class APIV1DELETEMultisigResponse {

  @SerializedName("error")
  private Boolean error = null;

  @SerializedName("message")
  private String message = null;
  public APIV1DELETEMultisigResponse error(Boolean error) {
    this.error = error;
    return this;
  }

  

  /**
  * Get error
  * @return error
  **/
  public Boolean isError() {
    return error;
  }
  public void setError(Boolean error) {
    this.error = error;
  }
  public APIV1DELETEMultisigResponse message(String message) {
    this.message = message;
    return this;
  }

  

  /**
  * Get message
  * @return message
  **/
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  @Override
  public boolean equals(java.lang.Object o) {
  if (this == o) {
    return true;
  }
  if (o == null || getClass() != o.getClass()) {
    return false;
  }
    APIV1DELETEMultisigResponse apIV1DELETEMultisigResponse = (APIV1DELETEMultisigResponse) o;
    return ObjectUtils.equals(this.error, apIV1DELETEMultisigResponse.error) &&
    ObjectUtils.equals(this.message, apIV1DELETEMultisigResponse.message);
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeMulti(error, message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class APIV1DELETEMultisigResponse {\n");
    
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
