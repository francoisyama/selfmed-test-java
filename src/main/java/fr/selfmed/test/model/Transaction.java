package fr.selfmed.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.selfmed.test.types.TransactionStatus;
import fr.selfmed.test.util.CustomDateSerializer;

import java.util.Date;

public class Transaction {

    private Long id;
    // l'annotation personnalise l'affichage de la date au format JSON
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;
    private String label;
    // l'annotation permet de ne pas inclure ce champ dans la construction du JSON
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Double amount;
    private Double debit;
    private Double credit;
    private TransactionStatus status;

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Transaction() {
    }

    public Transaction(Long id, Date date, String label, Double amount, Double credit, Double debit, TransactionStatus status) {
        this.id = id;
        this.date = date;
        this.label = label;
        this.amount = amount;
        this.credit = credit;
        this.debit = debit;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", label='" + label + '\'' +
                ", amount=" + amount +
                ", credit=" + credit +
                ", debit=" + debit +
                ", status=" + status +
                '}';
    }
}
