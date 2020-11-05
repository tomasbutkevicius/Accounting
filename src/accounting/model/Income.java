package accounting.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Income implements Serializable {
    private String name;
    private Integer amount;

    public Income(String name, Integer amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
