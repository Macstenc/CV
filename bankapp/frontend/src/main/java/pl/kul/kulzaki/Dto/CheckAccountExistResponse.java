package pl.kul.kulzaki.Dto;

public class CheckAccountExistResponse {
    public boolean exist;
    public String message;

    public CheckAccountExistResponse(boolean exist, String message) {
        this.exist = exist;
        this.message = message;
    }
}


