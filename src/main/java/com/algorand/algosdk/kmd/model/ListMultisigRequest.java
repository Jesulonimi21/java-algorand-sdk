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
 * APIV1POSTMultisigListRequest is the request for &#x60;POST /v1/multisig/list&#x60;
 */
public class ListMultisigRequest {

  @SerializedName("wallet_handle_token")
  private String walletHandleToken = null;
  public ListMultisigRequest walletHandleToken(String walletHandleToken) {
    this.walletHandleToken = walletHandleToken;
    return this;
  }

  

  /**
  * Get walletHandleToken
  * @return walletHandleToken
  **/
  public String getWalletHandleToken() {
    return walletHandleToken;
  }
  public void setWalletHandleToken(String walletHandleToken) {
    this.walletHandleToken = walletHandleToken;
  }
  @Override
  public boolean equals(java.lang.Object o) {
  if (this == o) {
    return true;
  }
  if (o == null || getClass() != o.getClass()) {
    return false;
  }
    ListMultisigRequest listMultisigRequest = (ListMultisigRequest) o;
    return ObjectUtils.equals(this.walletHandleToken, listMultisigRequest.walletHandleToken);
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeMulti(walletHandleToken);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListMultisigRequest {\n");
    
    sb.append("    walletHandleToken: ").append(toIndentedString(walletHandleToken)).append("\n");
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
