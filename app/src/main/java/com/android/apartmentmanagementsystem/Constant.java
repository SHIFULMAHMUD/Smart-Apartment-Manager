package com.android.apartmentmanagementsystem;

public class Constant {

    public static final String BASE_URL = "https://apartmentsecurity.cf/android/";
    public static final String IMAGE_URL = "https://apartmentsecurity.cf/admin/image/";
    public static final String KEY_NAME="name";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_CELL="cell";
    public static final String KEY_GUEST_NAME="guest_name";
    public static final String KEY_ACCOUNT="account";
    public static final String KEY_GENDER="gender";
    public static final String KEY_NID="nid";
    public static final String KEY_NID_PHOTO="nid_pic";
    public static final String KEY_FILE= "file";

    public static final String KEY_RENTER_NAME="renter_name";
    public static final String KEY_FLAT_NO="flat_no";
    public static final String KEY_FLOOR_NO="floor_no";
    public static final String KEY_COMPLAIN="complain";
    public static final String KEY_HOST_CELL="host_cell";
    public static final String KEY_QR_CODE="qr_code";
    public static final String KEY_PURPOSE="purpose";
    public static final String KEY_TOTAL_GUEST="total_guest";
    public static final String KEY_GUARD_NAME="guard_name";
    public static final String KEY_TASK="task";
    public static final String KEY_MONTH="rent_of_month";
    public static final String KEY_AMOUNT="rent_amount";
    public static final String KEY_AMOUNT_GAS="gas_bill_amount";
    public static final String KEY_AMOUNT_ELECTRICITY="electricity_bill_amount";
    public static final String KEY_BKASH_TRX_ID="bkash_trx_id";
    public static final String KEY_BKASH_CELL="bkash_cell";
    public static final String KEY_NOTE= "note";
    public static final String KEY_DATE="paying_date";
    public static final String KEY_TIME="paying_time";
    public static final String KEY_STATUS="status";


    //share preference
    //We will use this to store the user cell number into shared preference
    public static final String SHARED_PREF_NAME = "com.android.apartmentmanagementsystem"; //pcakage name+ id

    //This would be used to store the cell of current logged in user
    public static final String CELL_SHARED_PREF = "cell";

    //json array name.We will received data in this array
    public static final String JSON_ARRAY = "result";
}
