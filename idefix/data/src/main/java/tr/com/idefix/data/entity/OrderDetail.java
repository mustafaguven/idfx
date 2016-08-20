package tr.com.idefix.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain = true, fluent = true) public class OrderDetail {

  @SerializedName("OrderCode") @Expose private String orderCode;
  @SerializedName("OrderStatusId") @Expose private int orderStatusId;
  @SerializedName("PaymentStatusId") @Expose private int paymentStatusId;
  @SerializedName("PrintMode") @Expose private boolean printMode;
  @SerializedName("IsPartial") @Expose private boolean isPartial;
  @SerializedName("IsPackedPartial") @Expose private boolean isPackedPartial;
  @SerializedName("CreatedOn") @Expose private String createdOn;
  @SerializedName("WantToEinvoice") @Expose private boolean wantToEinvoice;
  @SerializedName("OrderStatus") @Expose private String orderStatus;
  @SerializedName("IsReOrderAllowed") @Expose private boolean isReOrderAllowed;
  @SerializedName("IsReturnRequestAllowed") @Expose private boolean isReturnRequestAllowed;
  @SerializedName("IsShippable") @Expose private boolean isShippable;
  @SerializedName("PickUpInStore") @Expose private boolean pickUpInStore;
  @SerializedName("ShippingStatus") @Expose private String shippingStatus;
  @SerializedName("ShippingAddress") @Expose private ShippingAddress shippingAddress;
  @SerializedName("ShippingMethod") @Expose private String shippingMethod;
  @SerializedName("Shipments") @Expose private List<Object> shipments = new ArrayList<Object>();
  @SerializedName("BillingAddress") @Expose private BillingAddress billingAddress;
  @SerializedName("VatNumber") @Expose private Object vatNumber;
  @SerializedName("InvoiceReceipt") @Expose private Object invoiceReceipt;
  @SerializedName("PaymentMethod") @Expose private String paymentMethod;
  @SerializedName("PaymentMethodStatus") @Expose private String paymentMethodStatus;
  @SerializedName("CanRePostProcessPayment") @Expose private boolean canRePostProcessPayment;
  @SerializedName("OrderShippingtotal") @Expose private String orderShippingtotal;
  @SerializedName("OrderSubtotal") @Expose private String orderSubtotal;
  @SerializedName("OrderSubTotalDiscount") @Expose private Object orderSubTotalDiscount;
  @SerializedName("OrderShipping") @Expose private Object orderShipping;
  @SerializedName("PaymentMethodAdditionalFee") @Expose private Object paymentMethodAdditionalFee;
  @SerializedName("CheckoutAttributeInfo") @Expose private String checkoutAttributeInfo;
  @SerializedName("InterestTotal") @Expose private String interestTotal;
  @SerializedName("PricesIncludeTax") @Expose private boolean pricesIncludeTax;
  @SerializedName("DisplayTaxShippingInfo") @Expose private boolean displayTaxShippingInfo;
  @SerializedName("Tax") @Expose private Object tax;
  @SerializedName("TaxRates") @Expose private List<Object> taxRates = new ArrayList<Object>();
  @SerializedName("DisplayTax") @Expose private boolean displayTax;
  @SerializedName("DisplayTaxRates") @Expose private boolean displayTaxRates;
  @SerializedName("ShipmentTraceUrl") @Expose private Object shipmentTraceUrl;
  @SerializedName("OrderTotalDiscount") @Expose private Object orderTotalDiscount;
  @SerializedName("RedeemedRewardPoints") @Expose private int redeemedRewardPoints;
  @SerializedName("RedeemedRewardPointsAmount") @Expose private Object redeemedRewardPointsAmount;
  @SerializedName("OrderTotal") @Expose private String orderTotal;
  @SerializedName("OrderTotal2") @Expose private double orderTotal2;
  @SerializedName("ShipmentTotal") @Expose private double shipmentTotal;
  @SerializedName("GiftCards") @Expose private List<Object> giftCards = new ArrayList<Object>();
  @SerializedName("OrderGuid") @Expose private String orderGuid;
  @SerializedName("ShowSku") @Expose private boolean showSku;
  @SerializedName("Items") @Expose private List<ItemEntity> items = new ArrayList<ItemEntity>();
  @SerializedName("IsTransfer") @Expose private Object isTransfer;
  @SerializedName("OrderNotes") @Expose private List<Object> orderNotes = new ArrayList<Object>();
  @SerializedName("StockCodeList") @Expose private List<String> stockCodeList =
      new ArrayList<String>();
  @SerializedName("CustomerId") @Expose private int customerId;
  @SerializedName("Id") @Expose private int id;
}