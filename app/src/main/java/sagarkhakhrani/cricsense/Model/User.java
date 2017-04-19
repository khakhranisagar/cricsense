package sagarkhakhrani.cricsense.Model;

/**
 * Created by sagar.khakhrani on 27-03-2017.
 */

public class User {
    String mUsername;
    String mEmail;
    String mPhone;
    String mAvatar;
    String mUserPoints;
    String mDeviceId;
    String mReferCode;
    String mAppliedReferCode;

    public User() {
    }

    public User(String mUsername, String mEmail, String mPhone, String mAvatar, String mUserPoints, String mDeviceId, String mReferCode, String mAppliedReferCode) {
        this.mUsername = mUsername;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
        this.mAvatar = mAvatar;
        this.mUserPoints = mUserPoints;
        this.mDeviceId = mDeviceId;
        this.mReferCode = mReferCode;
        this.mAppliedReferCode = mAppliedReferCode;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getmUserPoints() {
        return mUserPoints;
    }

    public void setmUserPoints(String mUserPoints) {
        this.mUserPoints = mUserPoints;
    }

    public String getmDeviceId() {
        return mDeviceId;
    }

    public void setmDeviceId(String mDeviceId) {
        this.mDeviceId = mDeviceId;
    }

    public String getmReferCode() {
        return mReferCode;
    }

    public void setmReferCode(String mReferCode) {
        this.mReferCode = mReferCode;
    }

    public String getmAppliedReferCode() {
        return mAppliedReferCode;
    }

    public void setmAppliedReferCode(String mAppliedReferCode) {
        this.mAppliedReferCode = mAppliedReferCode;
    }
}