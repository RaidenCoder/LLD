package strategies;

import java.util.List;

import entities.Split;
import exceptions.InvalidSplitException;

public class EqualSplitStrategy implements SplitStrategy {
    @Override
    public void validate(List<Split> splits, double totalAmount) {
        if(splits == null || splits.isEmpty()) {
            throw new InvalidSplitException("Splits list cannot be empty for equal split");
        }
    }

    @Override
    public void calculateSplits(List<Split> splits, double totalAmount) {
        int count  = splits.size();
        double equalShare = Math.round((totalAmount / count * 100.0) / 100.0);

        //Assign equal share to everyone except last
        for(int i = 0; i < count - 1; i++) {
            splits.get(i).setAmount(equalShare);
        }

        //Last person absorbs roudning remainder
        double remainder = totalAmount - (equalShare * (count - 1));
        remainder = Math.round(remainder * 100.0) / 100.0;
        splits.get(count - 1).setAmount(remainder);
    }
}
