
/**
 * Created by Federico Torres on 24/5/2016.
 */
public interface ApiResultResponse {

    boolean success = false;
    String resultMessage = "";

    void setResultMessage(String resultMessage);

    String getResultMessage();

    boolean isSuccess();

    void setSuccess(boolean success);


}
