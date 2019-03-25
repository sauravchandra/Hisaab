package saurav.chandra.hisaab;

import java.util.ArrayList;

public class Transaction {
    private String amount,description,type,time,paid_by;
    private ArrayList<String> participants;

    public Transaction() {}

    public Transaction(String tr_amount, String tr_description, String tr_time, String tr_type, String tr_paid_by, ArrayList<String> tr_participants) {
        this.amount = tr_amount;
        this.description = tr_description;
        this.time = tr_time;
        this.type = tr_type;
        this.paid_by = tr_paid_by;
        this.participants = tr_participants;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getType(){
        return  type;
    }

    public String getPaidBy() {
        return paid_by;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }
}
