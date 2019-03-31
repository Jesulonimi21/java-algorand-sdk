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

import java.util.ArrayList;
import java.util.List;

/**
 * APIV1GETWalletsResponse is the response to &#x60;GET /v1/wallets&#x60; friendly:ListWalletsResponse
 */
public class APIV1GETWalletsResponse {

  @SerializedName("error")
  private Boolean error = null;

  @SerializedName("message")
  private String message = null;

  @SerializedName("wallets")
  private List<APIV1Wallet> wallets = null;
  public APIV1GETWalletsResponse error(Boolean error) {
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
  public APIV1GETWalletsResponse message(String message) {
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
  public APIV1GETWalletsResponse wallets(List<APIV1Wallet> wallets) {
    this.wallets = wallets;
    return this;
  }

  public APIV1GETWalletsResponse addWalletsItem(APIV1Wallet walletsItem) {
    if (this.wallets == null) {
      this.wallets = new ArrayList<APIV1Wallet>();
    }
    this.wallets.add(walletsItem);
    return this;
  }

  /**
  * Get wallets
  * @return wallets
  **/
  public List<APIV1Wallet> getWallets() {
    return wallets;
  }
  public void setWallets(List<APIV1Wallet> wallets) {
    this.wallets = wallets;
  }
  @Override
  public boolean equals(java.lang.Object o) {
  if (this == o) {
    return true;
  }
  if (o == null || getClass() != o.getClass()) {
    return false;
  }
    APIV1GETWalletsResponse apIV1GETWalletsResponse = (APIV1GETWalletsResponse) o;
    return ObjectUtils.equals(this.error, apIV1GETWalletsResponse.error) &&
    ObjectUtils.equals(this.message, apIV1GETWalletsResponse.message) &&
    ObjectUtils.equals(this.wallets, apIV1GETWalletsResponse.wallets);
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeMulti(error, message, wallets);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class APIV1GETWalletsResponse {\n");
    
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    wallets: ").append(toIndentedString(wallets)).append("\n");
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
