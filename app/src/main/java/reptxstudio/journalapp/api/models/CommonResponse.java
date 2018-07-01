package reptxstudio.journalapp.api.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CommonResponse implements Parcelable {
    public static final Creator<CommonResponse> CREATOR = new Creator<CommonResponse>() {
        @Override
        public CommonResponse createFromParcel(Parcel source) {
            CommonResponse var = new CommonResponse();
            var.code = source.readString();
            var.message = source.readString();
            return var;
        }

        @Override
        public CommonResponse[] newArray(int size) {
            return new CommonResponse[size];
        }
    };
    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
