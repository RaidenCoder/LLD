package strategies;

import java.util.List;

import entities.Split;
import exceptions.InvalidSplitException;

public class ExactSplitStrategy implements SplitStrategy {
    @Override
    public void validate(List<Split> splits, double totalAmount) {
        double sum = 0;
        for(Split split: splits) {
            sum += split.getAmount();
        }
        // USe epsilon comparison for flaoting point 
        if(Math.abs(sum - totalAmount) > 0.01) {
            throw new InvalidSplitException(
                String.format("Exact split amount (%.2f) don't sum to total (%.2f)", sum, totalAmount)
            );
        }
    }

    @Override 
    public void calculateSplits(List<Split> splits, double totalAmount) {
        //Amounts are already set byt the caller, nothing to calculate
    }
}
