package fr.selfmed.test.types;

public enum TransactionStatus {
    ASSIGNED("ASSIGNED", "0"),
    NOTASSIGNED("NOTASSIGNED", "1");

    private String status;
    private String statusNumber;

    TransactionStatus(String status, String statusNumber) {
        this.status = status;
        this.statusNumber = statusNumber;
    }

    public String getStatusNumber() {
        return statusNumber;
    }

    @Override
    public String toString() {
        return status;
    }
}
